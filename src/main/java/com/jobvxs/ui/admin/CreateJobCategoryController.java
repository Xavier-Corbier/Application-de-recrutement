package com.jobvxs.ui.admin;

import com.jobvxs.bl.FacadeAdministrator;
import com.jobvxs.bl.FacadeCompany;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import static com.jobvxs.ui.JobVXS.getPage;
import static com.jobvxs.ui.JobVXS.loadNewIU;

/**
 * Class to manage the view to create a job category
 */
public class CreateJobCategoryController {

    @FXML
    private TextField nameField;
    @FXML
    private Text error;
    @FXML
    private Button sendButon;
    @FXML
    private BorderPane mainPane;

    /**
     * Action on click on save button. Save the form in the database
     * @param e
     */
    @FXML
    protected void onSaveButton(ActionEvent e){
        try {
            String nameCategory = nameField.getText();
            FacadeAdministrator facadeAdministrator = FacadeAdministrator.getFacadeAdministrator();
            facadeAdministrator.createJobCategory(nameCategory);
            // load the UI of the list of category to display the category added
            ((BorderPane)((AnchorPane)mainPane.getScene().getRoot()).getChildren().get(0)).setCenter(getPage("admin/list-job-category.fxml"));
        } catch (Exception ex) {
            showMsg("Issue with fields");
        }
    }

    /**
     * Show the message in the view
     * @param s
     */
    private void showMsg(String s) {
        error.setVisible(true);
        error.setText(s);
    }
}
