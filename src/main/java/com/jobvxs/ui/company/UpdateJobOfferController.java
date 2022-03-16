package com.jobvxs.ui.company;

import com.jobvxs.bl.FacadeCompany;
import com.jobvxs.bl.JobCategory;
import com.jobvxs.bl.JobOffer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class to manage the view to register a company
 */
public class UpdateJobOfferController {

    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox typeField;
    @FXML
    private TextField salaryField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextArea requirementsField;
    @FXML
    private TextArea advantagesField;
    @FXML
    private TextArea workScheduleField;
    @FXML
    private Text error;
    @FXML
    private Button sendButon;
    private JobOffer jobOffer;

    /**
     * Initialise the view to read
     */
    @FXML
    protected void initialize()  {
        try {
            FacadeCompany facade = FacadeCompany.getFacadeCompany();
            this.jobOffer = facade.getJobOffer();
            loadFields();
        } catch (SQLException e) {
            error.setVisible(true);
            error.setText(e.getMessage());
        }
    }

    /**
     * Load in the view the fields of the job offer
     *
     */
    private void loadFields() {
        nameField.setText(this.jobOffer.getName());
        FacadeCompany facade = null;
        try {
            facade = FacadeCompany.getFacadeCompany();
            ArrayList<JobCategory> list = facade.getAllJobCategorie();
            ArrayList<String> newList = new ArrayList<>();
            list.forEach((value) -> newList.add(value.getName()));
            typeField.setItems(FXCollections.observableArrayList(newList));
            typeField.setValue(this.jobOffer.getType());
        } catch (Exception e) {
            error.setVisible(true);
            error.setText(e.getMessage());
        }
        salaryField.setText(String.valueOf(jobOffer.getSalary()));
        descriptionField.setText(jobOffer.getDescription());
        requirementsField.setText(jobOffer.getRequirements());
        advantagesField.setText(jobOffer.getAdvantages());
        workScheduleField.setText(jobOffer.getWorkSchedule());
    }

    /**
     * Action on click on save button. Save the form in the database
     * @param e
     */
    @FXML
    protected void onSaveButton(ActionEvent e){
        try {
            double salary = checkFields(nameField.getText(),
                    (String) typeField.getValue(),
                    salaryField.getText(),
                    descriptionField.getText(),
                    requirementsField.getText(),
                    advantagesField.getText(),
                    workScheduleField.getText());
            saveJobOffer(salary, e);
        } catch (Exception ex) {
            showMsg("Issue with fields");
        }
    }

    /**
     * Save the job offer in the database
     * @param salary
     */
    private void saveJobOffer(double salary, ActionEvent e) {
        try {
            FacadeCompany facadeCompany = FacadeCompany.getFacadeCompany();
            facadeCompany.updateJobOffer(jobOffer.getIdJobOffer(),
                    nameField.getText(),
                    (String) typeField.getValue(),
                    salary,
                    descriptionField.getText(),
                    requirementsField.getText(),
                    advantagesField.getText(),
                    workScheduleField.getText(),
                    jobOffer.getEmailCompany());
            showMsg("Updated !");
        } catch (Exception ex){
            ex.printStackTrace();
            showMsg("Issue with the database");
        }
    }

    /**
     * Check fields of the form and return the double value for the salary
     * @param name
     * @param type
     * @param salaryString
     * @param description
     * @param requirements
     * @param advantages
     * @param workSchedule
     * @return
     * @throws Exception
     */
    private double checkFields(String name, String type, String salaryString, String description, String requirements, String advantages, String workSchedule) throws Exception {
        double salary = Double.parseDouble(salaryString);
        if (name ==""|| type ==""|| description ==""|| requirements ==""|| advantages ==""|| workSchedule ==""){
            throw new Exception("Input empty");
        }
        return salary;
    }

    /**
     * Show message in the view
     * @param s
     */
    private void showMsg(String s) {
        error.setVisible(true);
        error.setText(s);
    }
}
