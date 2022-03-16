package com.jobvxs.ui.company;

import com.jobvxs.bl.FacadeCompany;
import com.jobvxs.bl.JobCategory;
import com.jobvxs.ui.JobVXS;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.jobvxs.ui.JobVXS.loadNewIU;

/**
 * Class to manage the view to register a company
 */
public class CreateJobOfferController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField typeField;
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
    private ChoiceBox categoryInput;
    @FXML
    private Text error;
    @FXML
    private Button sendButon;

    /**
     * Initialise the view to read
     */
    @FXML
    protected void initialize()  {
        try {
            FacadeCompany facade = FacadeCompany.getFacadeCompany();
            ArrayList<JobCategory> list = facade.getAllJobCategorie();
            ArrayList<String> newList = new ArrayList<>();
            list.forEach((value) -> newList.add(value.getName()));
            categoryInput.setItems(FXCollections.observableArrayList(newList));
        } catch (Exception e) {
            error.setVisible(true);
            error.setText(e.getMessage());
        }
    }

    /**
     * Action on click on save button. Save the form in the database
     * @param e
     */
    @FXML
    protected void onSaveButton(ActionEvent e){
        try {
            double salary = checkFields(nameField.getText(),
                    salaryField.getText(),
                    descriptionField.getText(),
                    requirementsField.getText(),
                    advantagesField.getText(),
                    workScheduleField.getText(),
                    (String)categoryInput.getValue());
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
            facadeCompany.createJobOffer(nameField.getText(),
                    (String)categoryInput.getValue(),
                    salary,
                    descriptionField.getText(),
                    requirementsField.getText(),
                    advantagesField.getText(),
                    workScheduleField.getText());
            loadNewIU(e,"menu-company.fxml","JobVXS - Dashboard company");
        } catch (Exception ex){
            showMsg("Issue with the database");
        }
    }

    /**
     * Check fields of the form and return the double value for the salary
     * @param name
     * @param salaryString
     * @param description
     * @param requirements
     * @param advantages
     * @param workSchedule
     * @return
     * @throws Exception
     */
    private double checkFields(String name, String salaryString, String description, String requirements, String advantages, String workSchedule, String category) throws Exception {
        double salary = Double.parseDouble(salaryString);
        if (name ==""|| description ==""|| requirements ==""|| advantages ==""|| workSchedule =="" || category ==null){
            throw new Exception("Input empty");
        }
        return salary;
    }

    /**
     * Show the error in the view
     * @param s
     */
    private void showMsg(String s) {
        error.setVisible(true);
        error.setText(s);
    }
}
