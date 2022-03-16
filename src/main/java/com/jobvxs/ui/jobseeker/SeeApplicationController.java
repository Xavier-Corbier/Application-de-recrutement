package com.jobvxs.ui.jobseeker;

import com.jobvxs.bl.*;
import com.jobvxs.ui.JobVXS;
import com.jobvxs.ui.company.SeeJobOfferController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class SeeApplicationController {

    @FXML
    private VBox coveringLetterBox;

    @FXML
    private TextArea coveringLetter;

    @FXML
    private BorderPane borderPane;

    private Application application;


    /**
     * Initialise the view to read
     */
    @FXML
    protected void initialize() {
        coveringLetterBox.managedProperty().bind(coveringLetterBox.visibleProperty());
        coveringLetterBox.setVisible(false);
    }

    /**
     * Set the application on the view
     * @param application application of the job Seeker
     */
    public void setApplication(Application application){
        try{
            this.application = application;
            // set the covering letter
            if(application.getCoveringLetter() != null && !application.getCoveringLetter().equals("")){
                coveringLetterBox.setVisible(true);
                coveringLetter.setText(application.getCoveringLetter());
            }
            // set the CV
            FXMLLoader loader = new FXMLLoader(JobVXS.class.getResource("jobseeker/cv-view.fxml"));
            CV cv =  FacadeJobSeeker.getFacadeJobSeeker().getInfoCV(application.getEmailJobSeeker());
            try {
                loadCV(cv, loader);
            } catch (IOException e) {
                e.printStackTrace();
                this.borderPane.setCenter(new Text("There is a problem with javafx "));
            }

        }
        catch (Exception e){
            this.borderPane.setCenter(new Text(e.getMessage()));
            e.printStackTrace();
        }
    }


    /**
     * Load the job offer in the database
     * @param cv CV of the jobSeeker
     * @param loader of the CV view
     * @throws IOException Exception if there is a problem with the fxml file
     */
    private void loadCV(CV cv, FXMLLoader loader) throws IOException, SQLException {
        Pane panel = loader.load();
        ViewCvController viewCvController = loader.getController();
        if(FacadeUser.getFacadeUser().getUser().getRole() != User.UserType.JOBSEEKER){
            // if the user is not the jobSeeker, the view cannot find his CV, so we inject it
            viewCvController.setCV(cv, application.getNomJobSeeker());
        }
        this.borderPane.setCenter(panel);
    }
}
