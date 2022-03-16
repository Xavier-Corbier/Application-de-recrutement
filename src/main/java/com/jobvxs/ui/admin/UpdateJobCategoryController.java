package com.jobvxs.ui.admin;

import com.jobvxs.bl.FacadeAdministrator;
import com.jobvxs.bl.JobCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import static com.jobvxs.ui.JobVXS.loadNewIU;

/**
 * Class to manage the view to update a job category
 */
public class UpdateJobCategoryController {

    @FXML
    private TextField nameField;
    @FXML
    private Text error;
    @FXML
    private Button sendButon;
    private String oldJobCategory;
    /**
     * Action on click on save button. Save the form in the database
     * @param e
     */
    @FXML
    protected void onSaveButton(ActionEvent e){
        try {
            String nameCategory = nameField.getText();
            FacadeAdministrator facadeAdministrator = FacadeAdministrator.getFacadeAdministrator();
            facadeAdministrator.updateJobCategory(oldJobCategory,nameCategory);
            showMsg("Updated !");
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

    /**
     * Update the field of the form
     * @param jobCategory
     */
    public void updateField(String jobCategory){
        nameField.setText(jobCategory);
        oldJobCategory=jobCategory;
    }
}
