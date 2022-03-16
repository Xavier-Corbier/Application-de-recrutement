package com.jobvxs.ui.jobseeker;

import com.jobvxs.bl.Application;
import com.jobvxs.bl.FacadeJobSeeker;
import com.jobvxs.bl.JobSeeker;
import com.jobvxs.ui.JobVXS;
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


public class ListApplicationJobSeekerController {

    @FXML
    private ListView<Application> listView;

    private ObservableList<Application> applicationObservableList;

    private FacadeJobSeeker facadeJobSeeker;

    @FXML
    private AnchorPane panelRight;

    @FXML
    private BorderPane borderPane;
    
    private Button buttonClose;

    @FXML
    private Text errorMsg;

    /**
     * Constuctor of the listApplicationJobSeekerController
     * Get all the list of the applications of the job seeker
     */
    public ListApplicationJobSeekerController(){
        applicationObservableList = FXCollections.observableArrayList();
        try {
            facadeJobSeeker = FacadeJobSeeker.getFacadeJobSeeker();
            applicationObservableList.addAll(facadeJobSeeker.getListApp());
        }
        catch (Exception e){
            this.borderPane.setCenter(new Text("You have no application yet !"));
            e.printStackTrace();
        }
    }

    /**
     * Initialise the list page of the jobSeekers (called automatically)
     */
    @FXML
    protected void initialize(){
        listView.setItems(applicationObservableList);
        listView.setCellFactory(jobSeekerListView -> new ApplicationListViewCell(this));

    }

    /**
     * Function launch by the cell of the applications
     * Load the view of the application in the right panel
     * @param idApp id of the application
     * @param buttonclose reference to the button close of the cell
     */
    protected void onReadButton(int idApp, Button buttonclose) {
        if(this.buttonClose != null){this.buttonClose.setVisible(false); }
        this.buttonClose = buttonclose;
        FXMLLoader loader = new FXMLLoader(JobVXS.class.getResource("jobseeker/see-application.fxml"));
        Pane panel;
        try {
            panel = loader.load();
            SeeApplicationController seeApplicationController = loader.getController();
            seeApplicationController.setApplication(getAppfromList(idApp)); // set the application into the controller
            this.panelRight.getChildren().setAll(panel); // display the view
        } catch (IOException e) {
            e.printStackTrace();
            this.errorMsg.setText("There is a problem with javafx ");
        }
    }

    /**
     * Function launch by the cell of the applications
     * Delete an application giving its ID
     * @param idApp id of the application
     */
    protected void onDeleteButton(int idApp){
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete your applicaiton");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                this.facadeJobSeeker.deleteApplication(idApp);
                onClosePanelRight();
                this.applicationObservableList.removeIf(app -> app.getIdApp() == idApp);
            }
        } catch (Exception e) {
            this.errorMsg.setText("The application could not be deleted");
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
     * @param idApp id of the application of the job seeker
     * @return the application that have its id in the list
     */
    private Application getAppfromList(int idApp){
        for (Application app : this.applicationObservableList) {
            if(app.getIdApp() == idApp){
                return app;
            }
        }
        return null;
    }
}
