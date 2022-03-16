package com.jobvxs.ui.company;

import com.jobvxs.bl.FacadeCompany;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import static com.jobvxs.ui.JobVXS.loadNewIU;

/**
 * Class to manage the view to register a company
 */
public class RegisterCompanyController {

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
    private Text textCode;
    @FXML
    private TextField codeField;
    @FXML
    private Button sendButon;
    @FXML
    private Button checkButton;

    /**
     * Register the field of the company
     * @param e
     */
    @FXML
    protected void onRegisterButton(ActionEvent e) {
        try {
            registerCompany();
            loadNewIU(e,"menu-company.fxml","JobVXS - Company Dashboard");
        } catch (Exception ex) {
            error.setText("Issue with the database");
        }
    }

    /**
     * Register the company in the database
     * @throws Exception
     */
    private void registerCompany() throws Exception {
        FacadeCompany facade = FacadeCompany.getFacadeCompany();
        facade.register(nameField.getText(),
                addressField.getText(),
                Integer.parseInt(numberEmployeeField.getText()),
                emailField.getText(),
                Integer.parseInt(phoneNumberField.getText()),
                descriptionField.getText(),
                passwordField.getText()
                );
    }

    /**
     * Check if the fields are corrects
     * @param e
     */
    @FXML
    protected void onCheckButton(ActionEvent e) {
        try {
            checkFields();
            try {
                preRegister();
            } catch (SQLException ex) {
                showMsg("Issue with the database");
            } catch (MessagingException | UnsupportedEncodingException messagingException) {
                showMsg("Issue with the mail");
            }
        } catch (Exception ex){
            showMsg("Issue with number fields");
        }
    }

    /**
     * Check the fields in the form
     */
    private void checkFields() {
        int field = Integer.parseInt(numberEmployeeField.getText());
        field = Integer.parseInt(phoneNumberField.getText());
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
     * Pre register the company in the database. We check if there are already an user with this email in the database
     * @throws SQLException
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private void preRegister() throws SQLException, MessagingException, UnsupportedEncodingException {
        FacadeCompany facade = FacadeCompany.getFacadeCompany();
        if(facade.preRegister(emailField.getText())){
            facade.sendCode(emailField.getText());
            setDisable();
        }
        else{
            showMsg("This email is already used");
        }
    }

    /**
     * Disable the field of the view
     */
    private void setDisable(){
        error.setVisible(false);
        textCode.setVisible(true);
        codeField.setVisible(true);
        sendButon.setVisible(true);
        checkButton.setVisible(false);
        nameField.setDisable(true);
        addressField.setDisable(true);
        numberEmployeeField.setDisable(true);
        phoneNumberField.setDisable(true);
        descriptionField.setDisable(true);
        passwordField.setDisable(true);
        emailField.setDisable(true);
    }
}
