package com.jobvxs.ui;

import com.jobvxs.bl.Company;
import com.jobvxs.bl.FacadeCompany;
import com.jobvxs.bl.FacadeUser;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.Optional;

import static com.jobvxs.ui.JobVXS.*;

/**
 * Class to manage the menu for a company
 */
public class MenuCompanyController {

    @FXML
    MenuBar menu;
    @FXML
    private BorderPane mainPane;

    /**
     * Initialise the dashboard of the Company (called automatically)
     */
    @FXML
    protected void initialize()  {
        try {
            FacadeUser facadeUser = FacadeUser.getFacadeUser();
            Company company = (Company) facadeUser.getRole();
            FacadeCompany facadeCompany = FacadeCompany.getFacadeCompany();
            facadeCompany.setCompany(company);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
     * Load the profile of the user
     * @param e
     */
    @FXML
    protected void onProfileButton(ActionEvent e) {
        mainPane.setCenter(getPage("company/see-company.fxml"));
    }

    /**
     * Load the view for create job offer
     * @param e
     */
    @FXML
    protected void onCreateJobOfferButton(ActionEvent e) {
        mainPane.setCenter(getPage("company/create-job-offer.fxml"));
    }

    /**
     * Load the list application view
     * @param e
     */
    @FXML
    protected void onListApplication(Event e) {
        mainPane.setCenter(getPage("company/list-application-company.fxml"));
    }

    /**
     * Redirect the user to the page notification to see is notifications
     * @param e
     */
    @FXML
    protected void onClickButtonNotification(Event e){
        mainPane.setCenter(getPage("notifications.fxml"));
    }

    /**
     * Logout the user
     * @param e
     */
    @FXML
    protected void onLogout(ActionEvent e) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                FacadeUser facadeUser = FacadeUser.getFacadeUser();
                FacadeCompany facadeCompany = FacadeCompany.getFacadeCompany();
                facadeCompany.logout();
                facadeUser.logout();
                signOut(menu.getScene());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
