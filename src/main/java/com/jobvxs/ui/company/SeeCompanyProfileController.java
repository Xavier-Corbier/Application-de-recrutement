package com.jobvxs.ui.company;

import com.jobvxs.bl.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

import java.sql.SQLException;
import java.util.Optional;

import static com.jobvxs.ui.JobVXS.*;

/**
 * Class to manage the view to see the profile of a company
 */
public class SeeCompanyProfileController {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Text companyName;
    @FXML
    private Text companyEmail;
    @FXML
    private Text companyAddress;
    @FXML
    private Text companyPhoneNumber;
    @FXML
    private Text companyDescription;
    @FXML
    private Text companyNbEmployee;
    @FXML
    private Text error;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    private Company company;

    /** rating company **/
    @FXML
    private BorderPane paneRate;
    @FXML
    private Button createRate;
    @FXML
    private Rating rateField;
    @FXML
    private TextField commentField;

    @FXML
    private ListView<RatingCompany> listView;
    @FXML
    private VBox createRateBox;
    private ObservableList<RatingCompany> ratingCompanyObservableList;


    /**
     * Initialise the view to read
     */
    @FXML
    protected void initialize()  {
        bindDisplay();
        try {
            FacadeCompany facadeCompany = FacadeCompany.getFacadeCompany();
            company = facadeCompany.getCompany();
            if(company!=null){
                loadFieldCompany();
                setRating(company); // display its rates
            }
            checkRightToSeeButtons();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check the rights to see the buttons
     * @throws Exception
     */
    private void checkRightToSeeButtons() throws Exception {
        FacadeUser facadeUser = FacadeUser.getFacadeUser();
        createRateBox.setVisible(false);
        if(!(facadeUser.getUser().getRole() == User.UserType.COMPANY)){
            deleteButton.setVisible(false);
            updateButton.setVisible(false);
            if(facadeUser.getUser().getRole() == User.UserType.JOBSEEKER){
                // allow the jobseeker to create a rate for the company
                createRateBox.setVisible(true);
            }
        }
    }

    /**
     * display none in function of the valour of the node (true : display block | false : display none = not rendered)
     */
    private void bindDisplay(){
        createRateBox.managedProperty().bind(createRateBox.visibleProperty());
        deleteButton.managedProperty().bind(deleteButton.visibleProperty());
        updateButton.managedProperty().bind(updateButton.visibleProperty());
    }

    /**
     * Load the field of the company on the view
     */
    public void loadFieldCompany(){
        companyName.setText(companyName.getText()+" "+company.getName());
        companyAddress.setText(companyAddress.getText()+" "+company.getAddress());
        companyEmail.setText(companyEmail.getText()+" "+company.getUser().getEmail());
        companyDescription.setText(companyDescription.getText()+" "+company.getDescription());
        companyNbEmployee.setText(companyNbEmployee.getText()+" "+company.getNumberOfEmployee());
        companyPhoneNumber.setText(companyPhoneNumber.getText()+" "+company.getPhoneNumber());
    }

    /**
     * Redirect to the view update
     * @param e
     */
    @FXML
    protected void onUpdateButton(ActionEvent e) {
        ((BorderPane) ((AnchorPane) mainPane.getScene().getRoot()).getChildren().get(1)).setCenter(getPage("company/update-company.fxml"));
    }

    /**
     * Delete the profile
     * @param e
     */
    @FXML
    protected void onDeleteButton(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Company");
        alert.setContentText("Are you sure ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            deleteCompany();
            signOut(mainPane.getScene());
        }
    }

    /**
     * Delete the company in the database
     */
    private void deleteCompany() {
        try {
            FacadeCompany facadeCompany = FacadeCompany.getFacadeCompany();
            Company company = facadeCompany.getCompany();
            facadeCompany.delete(company.getUser().getEmail());
            facadeCompany.logout();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Set the company of the view (for the list)
     * @param company
     */
    public void setCompany(Company company) {
        this.company = company;
        setRating(this.company);
    }

    /** -------------- Management of rating  ------------------ **/

    /**
     * Create a rate for the company
     * @param e
     */
    @FXML
    protected void onSubmitRatingButton(ActionEvent e){
        int rate = (int) rateField.getRating();
        String comment = commentField.getText();

        try {
            User user = FacadeUser.getFacadeUser().getUser();
            if(user.getRole() == User.UserType.JOBSEEKER){ // There is a job Seeker connected
                String emailJobSeeker = user.getEmail();
                if(rate>=0 && rate<= 5){ // the rate not exceeded
                    RatingCompany ratingCompany = FacadeCompany.getFacadeCompany().createRate(
                            emailJobSeeker,
                            company.getUser().getEmail(),
                            rate,
                            comment
                    );
                    this.ratingCompanyObservableList.add(ratingCompany);
                    this.createRateBox.setVisible(false);

                    String desc = "Receive a new rate from " + user.getEmail() + " of " + ratingCompany.getNbStars() + " stars.";
                    FacadeUser.getFacadeUser().sendNotification(company.getUser().getEmail()
                            ,"New rate" , desc, Notification.EnumNotif.CLASSIC);
                }
            }
        } catch (Exception exception) {
            this.error.setText("The rate cannot be create");
            exception.printStackTrace();
        }
    }


    /** ---------------  Management of rating list  --------------------  **/

    /**
     * Display the list of the rates of the company
     * @param company company information
     */
    public void setRating(Company company){
        ratingCompanyObservableList = FXCollections.observableArrayList();
        try {
            FacadeCompany facadeCompany = FacadeCompany.getFacadeCompany();
            ratingCompanyObservableList.addAll(facadeCompany.getRatesCompany(company.getUser().getEmail()));
        }
        catch (Exception e){
            this.paneRate.setCenter(new Text("There is no rate yet"));
            e.printStackTrace();
        }

        listView.setItems(ratingCompanyObservableList);

        try {
            if((FacadeUser.getFacadeUser().getUser().getRole() == User.UserType.JOBSEEKER)) {
                // If this is the jobSeeker who launch the list company view, we pass his email
                // in order to display his rate from a different point of view
                String emailJobSeeker = FacadeUser.getFacadeUser().getUser().getEmail();
                listView.setCellFactory(jobSeekerListView -> new RatingCompanyListViewCell(this,emailJobSeeker));
            }
            else{
                listView.setCellFactory(jobSeekerListView -> new RatingCompanyListViewCell(this));
            }
        } catch (Exception exception) {
            this.error.setText("There is a problem withe the database (user)");
            exception.printStackTrace();
        }
    }

    /**
     * Function call by the cells (RatingCompanyListViewCell) in order to display or not the box for create a rate
     * @param value true : display the fields, false : hide the fields
     */
    protected void setBoxCreateRateVisible(boolean value){
        createRateBox.setVisible(value);
    }


    /**
     * Remove the rate from the list
     * @param jobSeekerEmail email of the job seeker
     */
    protected void onDeleteRateButton(String jobSeekerEmail) throws SQLException {
        this.ratingCompanyObservableList.removeIf(r -> r.getEmailJobSeeker().equals(jobSeekerEmail));
        if(! (FacadeUser.getFacadeUser().getUser().getRole() == User.UserType.ADMINISTRATOR)){
            createRateBox.setVisible(true); // If the jobSeeker delete his rate
        }
    }

    /**
     * Update a rate from the list
     * @param ratingCompany information of the rate
     */
    protected void onUpdateRateButton(RatingCompany ratingCompany) throws SQLException {
        // remove the old value
        this.ratingCompanyObservableList.removeIf(r -> r.getEmailJobSeeker().equals(ratingCompany.getEmailJobSeeker()));
        // add the new value
        this.ratingCompanyObservableList.add(ratingCompany);

        User user = FacadeUser.getFacadeUser().getUser();
        String desc = "Rate updated from " + user.getEmail() + " : " + ratingCompany.getNbStars() + " stars.";
        FacadeUser.getFacadeUser().sendNotification(company.getUser().getEmail()
                ,"Updated rate" , desc, Notification.EnumNotif.CLASSIC);
    }
}
