package com.jobvxs.ui.company;

import com.jobvxs.bl.Company;
import com.jobvxs.bl.FacadeCompany;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.sql.SQLException;
import static com.jobvxs.ui.JobVXS.getPage;

/**
 * Class to manage the view to update a company
 */
public class UpdateCompanyInformationController {

    @FXML
    private BorderPane mainPane;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField numberEmployeeField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private Text error;
    @FXML
    private Button updateButton;

    /**
     * Initialise the view to read
     */
    @FXML
    protected void initialize()  {
        try {
            FacadeCompany facade = FacadeCompany.getFacadeCompany();
            Company company = facade.getCompany();
            loadFields(company);
        } catch (SQLException e) {
            error.setVisible(true);
            error.setText(e.getMessage());
        }
    }

    /**
     * Load in the view the fields of the company
     * @param company
     */
    private void loadFields(Company company) {
        nameField.setText(company.getName());
        addressField.setText(company.getAddress());
        emailField.setText(company.getUser().getEmail());
        emailField.setDisable(true);
        descriptionField.setText(company.getDescription());
        numberEmployeeField.setText(String.valueOf(company.getNumberOfEmployee()));
        phoneNumberField.setText(String.valueOf(company.getPhoneNumber()));
        passwordField.setText(company.getUser().getPassword());
    }

    /**
     * Redirect to the view update
     * @param e
     */
    @FXML
    protected void onUpdateButton(ActionEvent e) {
        try {
            updateCompany();
            ((BorderPane) ((AnchorPane) mainPane.getScene().getRoot()).getChildren().get(1)).setCenter(getPage("company/see-company.fxml"));
        } catch (Exception ex) {
            error.setVisible(true);
            error.setText(ex.getMessage());
        }
    }

    /**
     * Update the company in the database
     * @throws Exception
     */
    private void updateCompany() throws Exception {
        FacadeCompany facade = FacadeCompany.getFacadeCompany();
        facade.update(nameField.getText(),
                addressField.getText(),
                descriptionField.getText(),
                Integer.parseInt(phoneNumberField.getText()),
                Integer.parseInt(numberEmployeeField.getText()),
                emailField.getText(),
                passwordField.getText()
        );
    }
}
