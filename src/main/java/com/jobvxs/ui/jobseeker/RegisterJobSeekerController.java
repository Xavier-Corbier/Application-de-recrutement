package com.jobvxs.ui.jobseeker;

import com.jobvxs.bl.FacadeJobSeeker;
import com.jobvxs.ui.JobVXS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;


public class RegisterJobSeekerController {

    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private TextArea description;

    @FXML
    private Button signUp;

    @FXML
    private TextField code;

    @FXML
    private HBox validateCode;

    @FXML
    private Text errorMsg;

    private FacadeJobSeeker facadeJobSeeker;


    /**
     * Initialise the register page of the jobSeeker (called automatically)
     */
    @FXML
    protected void initialize(){
        try {
            facadeJobSeeker = FacadeJobSeeker.getFacadeJobSeeker();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            this.errorMsg.setText("There is a problem with the database");
        }
    }

    /**
     * Deploy the procces of a registration of a jobseeker
     * @param e ActionEvent of the button
     */
    @FXML
    protected void onRegisterButton(ActionEvent e) {
        if(this.verifField()){ // the fields are correct
            if(!facadeJobSeeker.preRegister(email.getText())){ // there is no user already registered with this email
                try {
                    setDisabled(true);
                    this.errorMsg.setFill(Color.GREEN);
                    this.errorMsg.setText("Please wait ...");
                    facadeJobSeeker.sendCode(email.getText()); // send the code to the user
                } catch (Exception ex) {
                    this.errorMsg.setFill(Color.RED);
                    this.errorMsg.setText("There is a problem with the process of sending an email code");
                    ex.printStackTrace();
                    return;
                }
                this.errorMsg.setText("Please enter the code that you received on your email");
            }
            else{
                this.errorMsg.setText("This email already exist");
            }
        }
    }

    /**
     * Disabled all the field in function of the parameter
     * @param bool true : disabled all input, false : cancel the disabled process
     */
    private void setDisabled(boolean bool){
        validateCode.setVisible(bool);
        signUp.setVisible(!bool);

        email.setDisable(bool);
        name.setDisable(bool);
        address.setDisable(bool);
        dateOfBirth.setDisable(bool);
        phoneNumber.setDisable(bool);
        password.setDisable(bool);
        description.setDisable(bool);
    }

    /**
     * Verif the field that the jobseeker filled before applying to an update
     * @return true if the field are valid, false otherwise
     */
    private boolean verifField(){
        this.errorMsg.setText("");
        String error = facadeJobSeeker.verifField(
                this.name.getText(),
                this.address.getText(),
                this.dateOfBirth.getValue(),
                this.phoneNumber.getText(),
                this.email.getText(),
                this.password.getText()
        );
        if(!error.equals("")){
            this.errorMsg.setText(error); // If there is an error, we display it
            return false;
        }
        return true;
    }

    /**
     * Convert a date into a string of the format YY-MM-DD
     * @param dp a DatePicker
     * @return the value of the date parsing into string YYYY-MM-DD
     */
    private String getStringDate(DatePicker dp){
        return dp.getValue().getYear()+"-"+dp.getValue().getMonth().getValue()+"-"+dp.getValue().getDayOfMonth();
    }

    /**
     * The user entered the code and want to create his account (the function verify if the code is correct)
     * @param e ActionEvent of the button check code
     */
    @FXML
    protected void onCheckButton(ActionEvent e) {
        this.errorMsg.setText("");
        this.errorMsg.setFill(Color.RED);

        if(facadeJobSeeker.checkCode(code.getText())) { // check the code
            try {
                facadeJobSeeker.register(name.getText(),address.getText(),description.getText(),phoneNumber.getText(),Date.valueOf(getStringDate(dateOfBirth)),email.getText(),password.getText());
            } catch (Exception exception) {
                this.errorMsg.setText("There is an error with the database on the saving process");
                exception.printStackTrace();
            }
            loadJobSeekerDashboard(e);
        }
        else{
            this.errorMsg.setText("This is not the same code !");
        }
        setDisabled(false);
    }

    /**
     * Switch to the jobSeeker Dashboard
     * @param e ActionEvent of the button
     */
    private void loadJobSeekerDashboard(ActionEvent e) {
        try {
            JobVXS.loadNewIU(e,"menu-job-seeker.fxml","JobVXS - Job Seeker Dashboard");
        } catch (Exception exception) {
            exception.printStackTrace();
            this.errorMsg.setText("There is a problem with JavaFX");
        }
    }

}
