package com.jobvxs.ui;

import com.jobvxs.bl.FacadeUser;
import com.jobvxs.bl.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * UserController Class is for providing action to elements of the Application
 */

public class UserController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Text errorText;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField passwordInput;


    /**
     * The onButtonClick function provide an action to the button send
     * to permit the user to send his information
     * @param event
     */
    @FXML
    protected void onButtonClick(ActionEvent event) {
        String email = emailInput.getText();
        String password = passwordInput.getText();
        try {
            FacadeUser facadeUser = FacadeUser.getFacadeUser();
            facadeUser.login(email,password);
            switchToDashboard(event);
        } catch (Exception e) {
            e.printStackTrace();
            errorText.setText(e.getMessage());
        }
    }

    /**
     * Function which redirect the user to his dashboard
     * @param event
     * @throws IOException
     */
    protected void switchToDashboard(ActionEvent event) throws Exception {
        FXMLLoader loader = null;
        User user = FacadeUser.getFacadeUser().getUser();
        String title = "JobVXS";
        switch (user.getRole()){
            case ADMINISTRATOR :
                loader = new FXMLLoader(JobVXS.class.getResource("menu-admin.fxml"));
                title = "JobVXS - Administrator Dashboard";
                break;
            case JOBSEEKER :
                loader = new FXMLLoader(JobVXS.class.getResource("menu-job-seeker.fxml"));
                title = "JobVXS - Job Seeker Dashboard";
                break;
            case COMPANY :
                loader = new FXMLLoader(JobVXS.class.getResource("menu-company.fxml"));
                title = "JobVXS - Company Dashboard";
                break;
            default :
                errorText.setText("There is a problem with the role of the user");
                break;
        }
        if(loader != null){ // no problem encounter => display the dashboard
            root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
            ((Stage) scene.getWindow()).setTitle(title);
        }
    }
}