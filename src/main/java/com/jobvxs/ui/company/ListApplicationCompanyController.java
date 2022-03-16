package com.jobvxs.ui.company;

import com.jobvxs.bl.Application;
import com.jobvxs.bl.FacadeCompany;
import com.jobvxs.bl.FacadeUser;
import com.jobvxs.bl.Notification;
import com.jobvxs.ui.JobVXS;
import com.jobvxs.ui.jobseeker.SeeApplicationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Optional;

public class ListApplicationCompanyController {

    @FXML
    private ListView<Application> listView;

    private ObservableList<Application> applicationObservableList;

    private FacadeCompany facadeCompany;

    @FXML
    private AnchorPane panelRight;

    @FXML
    private BorderPane borderPane;

    private Button buttonClose;

    @FXML
    private Text errorMsg;

    /**
     * Constructor of the ListApplicationCompanyController
     * get all the application of the company
     */
    public ListApplicationCompanyController(){
        applicationObservableList = FXCollections.observableArrayList();
        try {
            facadeCompany = FacadeCompany.getFacadeCompany();
            applicationObservableList.addAll(facadeCompany.getListApp());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Initialize the list page of the jobSeekers (called automatically)
     */
    @FXML
    protected void initialize(){
        listView.setItems(applicationObservableList);
        listView.setCellFactory(jobSeekerListView -> new ApplicationCompanyListViewCell(this));

        if(applicationObservableList.size() == 0){
            errorMsg.setText("You have no application request yet !");
        }

    }

    /**
     * Function launch by the cell of the applications
     * Load the view of the application in the right panel
     * @param idApp id of the application
     * @param buttonclose reference to the button close of the cell
     */
    protected void onReadButton(int idApp, Button buttonclose) {
        if(this.buttonClose != null){
            this.buttonClose.setVisible(false);
        }
        this.buttonClose = buttonclose;
        FXMLLoader loader = new FXMLLoader(JobVXS.class.getResource("jobseeker/see-application.fxml"));
        Pane panel;
        try {
            panel = loader.load();
            SeeApplicationController seeApplicationController = loader.getController();
            seeApplicationController.setApplication(getAppfromList(idApp));
            this.panelRight.getChildren().setAll(panel);
        } catch (IOException e) {
            e.printStackTrace();
            this.errorMsg.setText("There is a problem with javafx ");
        }
    }

    /**
     * Get the number of the application according to a jobOffer
     * (If there are 2 application for the job offer 1, the index for the first application is 0, the second 1, etc...)
     * @param idApp id of the application
     * @return the index of the application group by the job offer
     */
    protected int getIdAppFromTheList(int idApp){
        // precondition : applicationObservableList is ordered by the id of the jobOffer
        int id = -1;
        int idJobOffer = -1;
        for (Application app : applicationObservableList) {
            if(idJobOffer == app.getIdJobOffer()){ // we found the same jobOffer
                id++; // we increment the index
            }
            else{ // we found a new job, there is no more application for the previous
                idJobOffer = app.getIdJobOffer();
                id = 0;
            }
            if(idApp == app.getIdApp()){ // we found it
                return id; // we return its index
            }
        }
        return -1; // not found
    }

    /**
     * Function that launch by the cell
     * Accept the application of a job seeker
     * @param idApp id of the application
     */
    protected void onAcceptButton(int idApp){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Accept the application");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                this.facadeCompany.acceptApplication(idApp);
                this.updateStateAppfromList(idApp, Application.StateApplication.ACCEPTED);
            }
        } catch (Exception e) {
            this.errorMsg.setText("The application could not be accepted");
            e.printStackTrace();
        }
    }

    /**
     * Function that launch by the cell
     * Reject the application of a job seeker
     * @param idApp id of the application
     */
    protected void onRejectButton(int idApp){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reject the applicaiton");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                this.facadeCompany.rejectApplication(idApp);
                this.updateStateAppfromList(idApp, Application.StateApplication.REJECTED);
            }
        } catch (Exception e) {
            this.errorMsg.setText("The application could not be rejected");
            e.printStackTrace();
        }
    }

    /**
     * Close the panel with the information of the application
     */
    protected void onClosePanelRight(){
        if(this.buttonClose != null){
            this.buttonClose.setVisible(false);
            this.buttonClose = null;
            panelRight.getChildren().clear();
            borderPane.setRight(null);
        }
    }

    /**
     * get an Application in the list by this id
     * @param idApp id of the application
     * @return the application of the list giving its id
     */
    private Application getAppfromList(int idApp){
        for (Application app : this.applicationObservableList) {
            if(app.getIdApp() == idApp){
                return app;
            }
        }
        return null;
    }

    /**
     * update the new state of the application
     * @param idApp id of the application
     * @param state new state of the application
     */
    private void updateStateAppfromList(int idApp, Application.StateApplication state){
        for (Application app : this.applicationObservableList) {
            if(app.getIdApp() == idApp){
                app.setState(state); // update the state
            }
        }
    }

}
