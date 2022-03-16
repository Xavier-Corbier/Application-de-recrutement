package com.jobvxs.ui;

import com.jobvxs.bl.Administrator;
import com.jobvxs.bl.FacadeUser;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

import javafx.scene.text.Text;


import java.util.Optional;

import static com.jobvxs.ui.JobVXS.getPage;
import static com.jobvxs.ui.JobVXS.signOut;

/**
 * Class for initializing the dashboard of an admin
 */

public class MenuAdminController {

    @FXML private MenuBar menu;

    @FXML
    private Label welcomeText;

    @FXML
    private Text errorText;

    @FXML
    private BorderPane mainPane;

    private Administrator administrator;

    private String jobSeekerUI = "jobseeker/";
    private String companyUI = "company/";
    private String adminUI = "admin/";

    /**
     * Initialize the dashboard of the Admin (called automatically)
     */
    @FXML
    protected void initialize()  {
        try{
            this.administrator = (Administrator) FacadeUser.getFacadeUser().getRole();
        }catch (Exception e){
            errorText.setText(e.getMessage());
        }
        mainPane.setCenter(getPage("company/list-job-offer.fxml"));
    }

    /**
     * Load the home of the user
     * @param e
     */
    @FXML
    protected void onHome(Event e) {
        mainPane.setCenter(getPage("company/list-job-offer.fxml"));
    }

    /**
     * Load the ui to create a job category
     * @param e
     */
    @FXML
    protected void onCreateJobCategoryButton(ActionEvent e) {
        mainPane.setCenter(getPage(adminUI+"create-job-category.fxml"));
    }

    /**
     * Load the ui of the list of job category
     * @param e
     */
    @FXML
    protected void onListJobCategoryButton(ActionEvent e) {
        mainPane.setCenter(getPage(adminUI+"list-job-category.fxml"));
    }

    /**
     * Load the list of the job Seekers
     * @param e ActionEvent of the button
     */
    @FXML
    protected void onListJobSeekerButton(ActionEvent e) {
        mainPane.setCenter(getPage(jobSeekerUI+"list-jobseeker.fxml"));
    }

    /**
     * Load the list of the companies
     * @param e ActionEvent of the button
     */
    @FXML
    protected void onListCompanyButton(ActionEvent e) {
        mainPane.setCenter(getPage(companyUI+"list-company.fxml"));
    }

    /**
     * Disconnect the administrator of the application
     * @param e ActionEvent of the button
     */
    @FXML
    protected void onSingOutButton(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            signOut(menu.getScene()); // disconnect the user
        }
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
