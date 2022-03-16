package com.jobvxs.ui.company;

import com.jobvxs.bl.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.jobvxs.ui.JobVXS.getPage;

/**
 * Class to manage the view to see the job offer
 */
public class SeeJobOfferController {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Text offerName;
    @FXML
    private Text offerType;
    @FXML
    private Text offerSalary;
    @FXML
    private Text offerDescription;
    @FXML
    private Text offerRequirement;
    @FXML
    private Text offerAdvantages;
    @FXML
    private Text offerWorkSchedule;
    @FXML
    private Text offerEmail;
    @FXML
    private Text error;
    @FXML
    private Button updateButton;
    @FXML
    private Button createCommentButton;
    @FXML
    private TextField textFieldComment;
    @FXML
    private Label labelForComment;
    @FXML
    private ListView<CommentJobOffer> listviewComment;

    private ObservableList<CommentJobOffer> commentJobOfferObservableList = FXCollections.observableArrayList();

    private JobOffer jobOffer;

    @FXML // Application
    private Button applyButton;
    @FXML
    private HBox boxCoveringLetter;
    @FXML
    private TextArea coveringLetter;

    /**
     * Initialise the view to read
     */
    @FXML
    protected void initialize()  {
        try {

            setVisibilityForComment();

            checkRightToSeeButtons();
            // display none if the visibility is false
            updateButton.managedProperty().bind(updateButton.visibleProperty());
            applyButton.managedProperty().bind(applyButton.visibleProperty());
            boxCoveringLetter.managedProperty().bind(boxCoveringLetter.visibleProperty());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the job offer of the view
     * @param jobOffer
     */
    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
        loadFieldJobOffer();

        refreshListView();

        try{
            if(FacadeUser.getFacadeUser().getUser() != null && FacadeUser.getFacadeUser().getUser().getRole() == User.UserType.JOBSEEKER){
                // Look if the jobSeeker already send this application
                FacadeJobSeeker.getFacadeJobSeeker().getListApp().forEach( app -> {
                    if(app.getIdJobOffer() == jobOffer.getIdJobOffer()){
                        applyButton.setText("Application sent! You can watch it by clicking on this button");
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
            error.setText(e.getMessage());
        }



    }

    /**
     * Check the rights to see the buttons
     * @throws Exception
     */
    private void checkRightToSeeButtons() throws Exception {
        FacadeUser facadeUser = FacadeUser.getFacadeUser();
        User user = facadeUser.getUser();
        if(user == null){
            updateButton.setVisible(false);
            applyButton.setVisible(false);
        } else {
            if(!(user.getRole() == User.UserType.COMPANY)){
                // If the user if not a company, we hidden the button
                updateButton.setVisible(false);
            }
            if(user.getRole() == User.UserType.JOBSEEKER){
                // If the user if a jobSeeker , we display the button
                applyButton.setVisible(true);
            }
        }
    }

    /**
     * Load the field of the job offer on the view
     */
    private void loadFieldJobOffer(){
        offerName.setText(offerName.getText()+" "+jobOffer.getName());
        offerType.setText(offerType.getText()+" "+jobOffer.getType());
        offerSalary.setText(offerSalary.getText()+" "+jobOffer.getSalary());
        offerDescription.setText(offerDescription.getText()+" "+jobOffer.getDescription());
        offerRequirement.setText(offerRequirement.getText()+" "+jobOffer.getRequirements());
        offerAdvantages.setText(offerAdvantages.getText()+" "+jobOffer.getAdvantages());
        offerWorkSchedule.setText(offerWorkSchedule.getText()+" "+jobOffer.getWorkSchedule());
        offerEmail.setText(offerEmail.getText()+" "+jobOffer.getEmailCompany());
    }

    /**
     * Redirect to the view update
     * @param e
     */
    @FXML
    protected void onUpdateButton(ActionEvent e) {
        mainPane.setTop(new Pane());
        mainPane.setCenter(getPage("company/update-job-offer.fxml"));
        mainPane.setBottom(new Pane());
    }

    /**
     * Button that permit to apply to the job offer
     * @param e actionEvent of the button
     */
    @FXML
    protected void onApplyButton(ActionEvent e){
        if(applyButton.getText().equals("Apply for this offer")){
            boxCoveringLetter.setVisible(true); // display the form for the application request
            applyButton.setText("Apply");
        }
        else if(applyButton.getText().equals("Apply")){
            try { // starts the process of sending the application
                FacadeJobSeeker.getFacadeJobSeeker().createApplication(jobOffer.getIdJobOffer(), jobOffer.getName(), coveringLetter.getText());
                applyButton.setText("Application sent! You can watch it by clicking on this button");
                boxCoveringLetter.setVisible(false);
            } catch (Exception exception) {
                exception.printStackTrace();
                error.setText(exception.getMessage());
            }
        }
        else{ // the job offer have already sent the application
            ((BorderPane)((AnchorPane)mainPane.getScene().getRoot()).getChildren().get(0)).setCenter(getPage("jobseeker/list-application-jobseeker.fxml"));
        }
    }

    /**
     * Action to execute when the user click on the CreateComment Button
     * @param e
     */
    @FXML
    protected void onCreateCommentButton(ActionEvent e){
        String comment = textFieldComment.getText();
        int idJobOffer = jobOffer.getIdJobOffer();
        try {
            FacadeCompany.getFacadeCompany().createCommentJobOffer(comment, idJobOffer);
            textFieldComment.setText("");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        refreshListView();
    }

    /**
     * Set the list view for the comment
     */
    private void setListViewComment(){
        ArrayList<CommentJobOffer> commentList = null;
        try {
            commentList = FacadeCompany.getFacadeCompany().getAllCommentJobOffer(jobOffer.getIdJobOffer());
            commentJobOfferObservableList.addAll(commentList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permit to refresh the list view of the comments
     */
    private void refreshListView(){
        listviewComment.getItems().removeAll(commentJobOfferObservableList);
        setListViewComment();
        listviewComment.setItems(commentJobOfferObservableList);
        listviewComment.setCellFactory(commentJobOfferListView -> new CommentJobOfferListViewCell(this));
    }

    /**
     * Action to execute when the user click on the delete button for a comment
     * @param idComment
     */
    protected void onDeleteButtonComment(int idComment){
        try {
            FacadeCompany.getFacadeCompany().deleteCommentJobOffer(idComment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshListView();
    }

    /**
     * Action to execute when the user click on the update comment button
     * @param comment
     * @param idComment
     */
    protected void onUpdateButtonComment(String comment, int idComment){
        try {
            FacadeCompany.getFacadeCompany().updateCommentJobOffer(comment, idComment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshListView();
    }

    /**
     * Set the visibility of the buttons for creating a comment and updating a comment
     */
    private void setVisibilityForComment(){
        try{
            if(FacadeUser.getFacadeUser().getUser() != null
             && FacadeUser.getFacadeUser().getUser().getRole() == User.UserType.JOBSEEKER) {
                createCommentButton.setVisible(true);
                textFieldComment.setVisible(true);
                labelForComment.setText("Type a comment :");
            } else {
                createCommentButton.setVisible(false);
                textFieldComment.setVisible(false);
                labelForComment.setText("Comment for this job offer : ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
