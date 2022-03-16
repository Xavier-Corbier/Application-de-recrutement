package com.jobvxs.ui;

import com.jobvxs.bl.FacadeJobSeeker;
import com.jobvxs.bl.FacadeUser;
import com.jobvxs.bl.JobSeeker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.Optional;

import static com.jobvxs.ui.JobVXS.getPage;
import static com.jobvxs.ui.JobVXS.signOut;

/**
 * Class for initializing the dashboard of a jobSeeker
 */

public class MenuJobSeekerController {

    @FXML
    MenuBar menu;

    private final String packageUi = "jobseeker/";

    @FXML
    private BorderPane mainPane;

    /**
     * Initialise the dashboard of the jobSeeker (called automatically)
     */
    @FXML
    protected void initialize()  {
        try{
            JobSeeker jobSeeker = (JobSeeker) FacadeUser.getFacadeUser().getRole();
            FacadeJobSeeker.getFacadeJobSeeker().setJobseeker(jobSeeker);
        }catch (Exception e){
            e.printStackTrace();
        }
        mainPane.setCenter(getPage("company/list-job-offer.fxml"));
    }

    /**
     * Load the home of the user
     * @param e Event of the button
     */
    @FXML
    protected void onHome(Event e) {
        mainPane.setCenter(getPage("company/list-job-offer.fxml"));
    }

    /**
     * Load the profile page of the jobseeker
     * @param e ActionEvent of the button
     */
    @FXML
    protected void onProfileButton(ActionEvent e) {
        mainPane.setCenter(getPage(packageUi+"profile-jobseeker.fxml"));
    }

    /**
     * Disconnect the jobSeeker of the application
     * @param e ActionEvent of the button
     */
    @FXML
    protected void onSignOutButton(ActionEvent e) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                FacadeJobSeeker.getFacadeJobSeeker().signOut(); // disconnect the job seeker role
                signOut(menu.getScene()); // disconnect the user
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            this.mainPane.setCenter(new Text("There is a problem with the database"));
        }

    }

    /**
     * Redirect the jobSeeker to a page where he will be able to update his CV
     * @param e
     */
    @FXML
    protected void onClickButtonUpdateCV(ActionEvent e) {mainPane.setCenter(getPage(packageUi+"modify-cv.fxml"));}

    /**
     * Redirect the jobSeeker to a page where he will be able to see his CV
     * @param ae
     */
    @FXML
    protected void onClickButtonViewCV(ActionEvent ae) {mainPane.setCenter(getPage(packageUi+"cv-view.fxml"));}

    /**
     * Redirect the jobSeeker to a page where he will be able to see the list of the company
     * @param ae
     */
    @FXML
    protected void onListCompany(ActionEvent ae) {
        mainPane.setCenter(getPage("company/list-company.fxml"));
    }

    /**
     * load the page of applications
     * @param e ActionEvent of the button
     */
    @FXML
    protected void onApplications(Event e) {
        mainPane.setCenter(getPage(packageUi+"list-application-jobseeker.fxml"));
    }

    /**
     * Redirect the user to the page notification to see is notifications
     * @param e
     */
    @FXML
    protected void onClickButtonNotification(Event e){
        mainPane.setCenter(getPage("notifications.fxml"));
    }

}
