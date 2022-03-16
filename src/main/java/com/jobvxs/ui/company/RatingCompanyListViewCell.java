package com.jobvxs.ui.company;

import com.jobvxs.bl.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;
import java.io.IOException;
import java.util.Optional;


public class RatingCompanyListViewCell extends ListCell<RatingCompany> {

    @FXML
    private Label labelNom;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button buttonModify;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonCancel;
    @FXML
    private Rating rating;
    @FXML
    private TextArea comment;
    @FXML
    private Text errorText;

    private FXMLLoader mLLoader;
    private SeeCompanyProfileController parent;
    private RatingCompany ratingCompany;
    private String jobSeekerWhoRate = null;

    /**
     * Constructor for the RatingCompanyListViewCell class
     * @param scpc
     */
    public RatingCompanyListViewCell(SeeCompanyProfileController scpc) {
        parent = scpc;
    }

    /**
     * Second constructor call when the job seeker is connected to the application
     * We pass his email to display its rate in an another way
     * @param scpc controller that own the cell
     * @param emailJobSeeker email of the job seeker connected
     */
    public RatingCompanyListViewCell(SeeCompanyProfileController scpc, String emailJobSeeker) {
        parent = scpc;
        jobSeekerWhoRate = emailJobSeeker;
    }

    /**
     * Set the company information in the .fxml file
     * @param ratingC information of the rating
     * @param booleanValue boolean for the parent (if we display or not the cell)
     */
    protected void updateItem(RatingCompany ratingC, boolean booleanValue) {
        this.ratingCompany = ratingC;

        super.updateItem(ratingCompany, booleanValue);
        if(booleanValue || ratingCompany == null) {
            setText(null);
            setGraphic(null);
        } else {
            loadFxml();
            setDisabledRate(true);
            if(jobSeekerWhoRate != null && jobSeekerWhoRate.equals(ratingCompany.getEmailJobSeeker())){
                // if this rate is from the jobSeeker connected
                labelNom.setText("(you)");
                parent.setBoxCreateRateVisible(false); // we remove from the view the create form for the rate
                buttonModify.setVisible(true);
            }
            else{
                labelNom.setText("("+ ratingCompany.getNameJobSeeker() +")");
                buttonModify.setVisible(false);
                buttonModify.managedProperty().bind(buttonModify.visibleProperty());
            }

            rating.setRating(ratingCompany.getNbStars());
            if(ratingCompany.getComment() == null || ratingCompany.getComment().equals("")){
                comment.setVisible(false);
            }
            else{
                comment.setText(ratingCompany.getComment());
            }
            setActionButton();
            checkRightForDelete();
            setText(null);
            setGraphic(gridPane);
        }
    }

    /**
     * Disabled the fields according to the value of the boolean
     * @param bool true : disabled all the field, false: cancel the process
     */
    private void setDisabledRate(boolean bool){
        rating.setDisable(bool);
        comment.setDisable(bool);
        buttonCancel.setVisible(!bool);
    }

    /**
     * Check if the user has the rigth for the delete action
     */
    private void checkRightForDelete() {
        try {
            FacadeUser facadeUser = FacadeUser.getFacadeUser();
            buttonDelete.managedProperty().bind(buttonDelete.visibleProperty());
            if (!(facadeUser.getUser().getRole() == User.UserType.ADMINISTRATOR || (jobSeekerWhoRate != null && jobSeekerWhoRate.equals(ratingCompany.getEmailJobSeeker())))){
                buttonDelete.setVisible(false);
            }
            else{
                buttonDelete.setVisible(true);
            }
        } catch (Exception e) {
            this.errorText.setText("There is an error with the database");
            e.printStackTrace();
        }
    }


    /**
     * Set the different actions for the buttons
     */
    private void setActionButton() {
        /** Action for update a rate made by a jobSeeker **/
        buttonModify.setOnAction(e -> {
            if(buttonModify.getText().equals("modify")){
                buttonModify.setText("Confirm");
                setDisabledRate(false);
                comment.setVisible(true);
            }
            else{
                buttonModify.setText("modify");
                setDisabledRate(true);
                try {
                    int rate = (int) rating.getRating();
                    String commentText =  this.comment.getText();
                    if(commentText.length()>0){
                        comment.setVisible(true); // we display the comment if he add text on it
                    }
                    if(jobSeekerWhoRate != null && jobSeekerWhoRate.equals(ratingCompany.getEmailJobSeeker())){ // jobSeeker connected
                        if(rate>=0 && rate<=5){
                            RatingCompany updateRatingC = new RatingCompany(ratingCompany);
                            updateRatingC.setNbStars(rate);
                            updateRatingC.setComment(commentText);
                            FacadeCompany.getFacadeCompany().updateRate(updateRatingC);
                            parent.onUpdateRateButton(updateRatingC); // create the change on the observable list
                        }
                    }
                } catch (Exception exception) {
                    this.errorText.setText("There is an error with the update, the rate cannot be change");
                    exception.printStackTrace();
                }
            }
        });

        /** Action for cancel an update **/
        buttonCancel.setOnAction(e -> {
            buttonModify.setText("modify");
            setDisabledRate(true);
            if(comment.getText().length() == 0){
                comment.setVisible(false);
            }
        });

        /** Action for delete a rate **/
        buttonDelete.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete your rate");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                try {
                    FacadeCompany.getFacadeCompany().deleteRate(ratingCompany);
                    parent.onDeleteRateButton(ratingCompany.getEmailJobSeeker());
                    comment.setText("");// we clear the textArea
                } catch (Exception exception) {
                    this.errorText.setText("There is an error with the database, the rate cannot be delete");
                    exception.printStackTrace();
                }
            }
        });
    }

    /**
     * Load the fxmlFile
     */
    private void loadFxml() {
        if (mLLoader == null) {
            mLLoader = new FXMLLoader(getClass().getResource("list-cell-rating.fxml"));
            mLLoader.setController(this);
            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
