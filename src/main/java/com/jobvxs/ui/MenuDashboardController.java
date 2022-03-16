package com.jobvxs.ui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import static com.jobvxs.ui.JobVXS.getPage;

/**
 * Class to manage the menu for a classic user
 */
public class MenuDashboardController {

    @FXML
    private BorderPane mainPane;

    /**
     * Initialise the register page of the jobSeeker (called automatically)
     */
    @FXML
    protected void initialize(){

        mainPane.setCenter(getPage("company/list-job-offer.fxml"));
    }

    /**
     * Load the login view
     * @param e
     */
    @FXML
    protected void onLoginButton(Event e) {
        mainPane.setCenter(getPage("login-ui.fxml"));
    }

    /**
     * Load the signup view for the job seeker
     * @param e
     */
    @FXML
    protected void onSignUpButtonJobSeeker(ActionEvent e) {
        mainPane.setCenter(getPage("jobseeker/register-jobseeker.fxml"));
    }

    /**
     * Load the signup view for the company
     * @param e
     */
    @FXML
    protected void onSignUpButtonCompany(ActionEvent e) {
        mainPane.setCenter(getPage("company/register-company.fxml"));
    }

    /**
     * Load the home of the user
     * @param e
     */
    @FXML
    protected void onHome(Event e) {
        mainPane.setCenter(getPage("company/list-job-offer.fxml"));
    }

}
