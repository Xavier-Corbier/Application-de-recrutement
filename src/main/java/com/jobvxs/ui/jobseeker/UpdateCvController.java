package com.jobvxs.ui.jobseeker;

import com.jobvxs.bl.CV;
import com.jobvxs.bl.FacadeJobSeeker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class UpdateCvController {

    @FXML
    private Label labelUpdateStatus;

    @FXML
    private TextField textFieldEducation;

    @FXML
    private TextField textFieldWorkExp;

    @FXML
    private TextField textFieldSkillAndInterest;

    @FXML
    private TextField textFieldReference;

    /**
     * Action to execute when clicking on the button delete
     * @param e
     */
    @FXML
    protected void onDeleteButtonReferences(ActionEvent e){
        try {
            FacadeJobSeeker facadeJobSeeker = FacadeJobSeeker.getFacadeJobSeeker();
            CV cv = facadeJobSeeker.getInfoCV();
            cv.setReferences("");
            textFieldReference.setText(cv.getReferences());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Action to execute when clicking on the button validate Changes to validate the changes for a CV
     * @param e
     */
    @FXML
    protected void onClickButtonValidateChanges(ActionEvent e){
        try{
            FacadeJobSeeker facadeJobSeeker = FacadeJobSeeker.getFacadeJobSeeker();
            String edu = textFieldEducation.getText();
            String workExp = textFieldWorkExp.getText();
            String skills = textFieldSkillAndInterest.getText();
            String ref = textFieldReference.getText();
            CV cv = new CV(edu, workExp, skills, ref);
            if(facadeJobSeeker.updateCV(cv)){
                labelUpdateStatus.setText("Updated with success");
            } else {
                labelUpdateStatus.setText("Error while updating");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Initialize the view
     */
    @FXML
    protected void initialize(){
        try{
            labelUpdateStatus.setText("");
            FacadeJobSeeker facadeJobSeeker = FacadeJobSeeker.getFacadeJobSeeker();
            CV cv = facadeJobSeeker.getInfoCV();
            String eduCV = cv.getEducation();
            String workExpCV = cv.getWorkExperience();
            String skillCV = cv.getSkillsAndInterests();
            String refCV = cv.getReferences();

            textFieldEducation.setText(eduCV);
            textFieldWorkExp.setText(workExpCV);
            textFieldSkillAndInterest.setText(skillCV);
            textFieldReference.setText(refCV);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
