package com.jobvxs.ui.jobseeker;

import com.jobvxs.bl.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

import static com.jobvxs.ui.JobVXS.signOut;


public class ProfileJobSeekerController {

    @FXML
    private Text title;

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
    private Button modify;
    @FXML
    private Button cancel;
    @FXML
    private Button delete;

    @FXML
    private Text errorMsg;

    private JobSeeker jobSeeker;

    private boolean isLaunchByAdmin = false;


    /**
     * Initialise the profile page of the jobSeeker (called automatically)
     */
    @FXML
    protected void initialize(){
        email.setDisable(true);
        setDisabled(true);
        try{
            switch (FacadeUser.getFacadeUser().getUser().getRole()){
                case ADMINISTRATOR:
                    // We came from the dashboardAdministrator
                    this.jobSeeker = null;
                    this.isLaunchByAdmin = true;
                    this.title.setText("Profile of the job seeker");
                    this.modify.setVisible(false);
                    this.delete.setVisible(false);
                    break;
                case JOBSEEKER:
                    // We came from the dashboardJobSeeker
                    try{
                        this.jobSeeker = (JobSeeker) FacadeUser.getFacadeUser().getRole();
                        if(this.jobSeeker != null) {setInput();}
                    }
                    catch (Exception e){
                            e.printStackTrace();
                            errorMsg.setText("There is a problem with the database");
                    }
                    break;
                default:
                    this.errorMsg.setText("There is a problem with the user");
                    break;
            }
        }catch (Exception e){
           this.errorMsg.setText("There is no user connected");
        }
    }


    /**
     * set the jobSeeker and filled this inputField (Function called by an administrator)
     * @param jobSeeker state of the job seeker
     */
    protected void setJobSeeker(JobSeeker jobSeeker){
        this.jobSeeker = jobSeeker;
        this.setInput();
    }

    /**
     * Set the inputField thanks by the jobSeeker information
     */
    private void setInput(){
        if(jobSeeker != null){
            this.name.setText(jobSeeker.getName());
            this.address.setText(jobSeeker.getAddress());
            this.dateOfBirth.setValue(jobSeeker.getBirthday().toLocalDate());
            this.email.setText(jobSeeker.getEmail());
            this.description.setText(jobSeeker.getDescription());
            this.phoneNumber.setText(String.valueOf(jobSeeker.getPhone()));
        }
        else{
            this.errorMsg.setText("There is no job seeker");
        }
    }

    /**
     * Launch the process of an update for the jobSeeker
     * @param e ActionEvent of the update button
     */
    @FXML
    protected void onUpdateButton(ActionEvent e) {
        this.errorMsg.setText("");
        this.errorMsg.setFill(Color.RED);

        if(modify.getText().equals("Validate")){ // we already click one time on this button
            setDisabled(true);
            cancel.setVisible(false);
            modify.setText("Modify");
            String returnStr;
            if(verifField()){
                try {
                    returnStr = FacadeJobSeeker.getFacadeJobSeeker().update(
                            this.name.getText(),
                            this.address.getText(),
                            this.description.getText(),
                            this.phoneNumber.getText(),
                            Date.valueOf(getStringDate(dateOfBirth)),
                            this.email.getText(),
                            this.password.getText()
                            );
                } catch (Exception exception) {
                    exception.printStackTrace();
                    this.errorMsg.setText("There is a problem with the database | Your profile could not be update");
                    return;
                }
                if(returnStr.equals("")){ // there is no error
                    this.errorMsg.setFill(Color.GREEN);
                    this.errorMsg.setText("Your profile has been updated !");
                }
                else{
                    this.errorMsg.setText(returnStr);
                }

            }
        }
        else{ // we click for the first time
            setDisabled(false);
            modify.setText("Validate");
            cancel.setVisible(true);
        }
    }

    /**
     * Convert a date into a string of the format YY-MM-DD
     * @param dp Date picker
     * @return the value of the date parsing in string YYYY-MM-DD
     */
    private String getStringDate(DatePicker dp){
        return dp.getValue().getYear()+"-"+dp.getValue().getMonth().getValue()+"-"+dp.getValue().getDayOfMonth();
    }

    /**
     * Verif the field that the jobseeker filled before applying to an update
     * @return true if the field are valid, or false otherwise
     */
    private boolean verifField(){
        this.errorMsg.setText("");
        String error = null;
        try {
            error = FacadeJobSeeker.getFacadeJobSeeker().verifField(
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            this.errorMsg.setText("There is a problem with the database");
            return false;
        }

    }

    /**
     * Disable all fields so that the job seeker cannot change them
     * @param e ActionEvent of the cancel button
     */
    @FXML
    protected void onCancelButton(ActionEvent e){
        setDisabled(true);
        cancel.setVisible(false);
        modify.setText("Modify");
    }


    /**
     * Disabled all the field in function of the parameter
     * @param bool true : disabled all the field, false : cancel the process
     */
    private void setDisabled(boolean bool){
        name.setDisable(bool);
        address.setDisable(bool);
        dateOfBirth.setDisable(bool);
        phoneNumber.setDisable(bool);
        password.setDisable(bool);
        description.setDisable(bool);
    }

    /**
     * Delete the profile
     * @param e ActionEvent of the delete button
     */
    @FXML
    protected void onDeleteButton(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Your profile");
        alert.setContentText("Are you sure ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            try {
                FacadeJobSeeker.getFacadeJobSeeker().delete(jobSeeker.getEmail());
                FacadeJobSeeker.getFacadeJobSeeker().signOut();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            signOut(((Button)e.getSource()).getScene());
        }
    }


}
