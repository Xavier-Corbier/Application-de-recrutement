package com.jobvxs.ui.jobseeker;

import com.jobvxs.bl.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ViewCvController {

    @FXML
    private Label welcomeText;

    @FXML
    private Label education;

    @FXML
    private Label workExperience;

    @FXML
    private Label skills;

    @FXML
    private Label references;

    private JobSeeker jobSeeker;

    /**
     * Initialize the view
     */
    @FXML
    protected void initialize(){
        try{
            FacadeJobSeeker facadeJobSeeker = FacadeJobSeeker.getFacadeJobSeeker();
            if(facadeJobSeeker.getJobseeker() != null){
                CV cv = facadeJobSeeker.getInfoCV();
                if(cv == null){
                   cv =  facadeJobSeeker.createCV();
                }

                this.jobSeeker = facadeJobSeeker.getJobseeker();

                setInfoCV(cv, this.jobSeeker.getName());
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Set the field of the CV
     * @param cv
     * @param name name of the jobSeeker
     */
    private void setInfoCV(CV cv, String name){
        welcomeText.setText(name +" - CV ");
        education.setText("Education : " + cv.getEducation());
        workExperience.setText("Work experience : " + cv.getWorkExperience());
        skills.setText("Skills and interests : " + cv.getSkillsAndInterests());
        references.setText("References : " + cv.getReferences());
    }

    /**
     *  Function call by the application system in order to display the CV of a specific jobSeeker
     * @param cv
     * @param nomJobSeeker
     */
    protected void setCV(CV cv,String nomJobSeeker){
        setInfoCV(cv,nomJobSeeker);
    }
}
