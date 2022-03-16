package com.jobvxs.ui.jobseeker;

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


public class ListJobSeekerController {

    @FXML
    private ListView<JobSeeker> listView;

    private ObservableList<JobSeeker> jobSeekersObservableList;

    private FacadeJobSeeker facadeJobSeeker;

    @FXML
    private AnchorPane panelRight;

    @FXML
    private BorderPane borderPane;


    @FXML
    private Text errorMsg;

    private Button buttonClose;

    /**
     * Constuctor of the listJobSeekerController
     * get all the list of the job seekers
     */
    public ListJobSeekerController(){
        jobSeekersObservableList = FXCollections.observableArrayList();
        try {
            facadeJobSeeker = FacadeJobSeeker.getFacadeJobSeeker();
            jobSeekersObservableList.addAll(facadeJobSeeker.getList());
        }
        catch (Exception e){
            this.borderPane.setCenter(new Text("There is no job seeker yet"));
            e.printStackTrace();
        }
    }

    /**
     * Initialise the list page of the jobSeekers (called automatically)
     */
    @FXML
    protected void initialize(){
        listView.setItems(jobSeekersObservableList);
        listView.setCellFactory(jobSeekerListView -> new JobSeekerListViewCell(this));

    }

    /**
     * Display a panel with the information of the job seeker
     * @param email email of the job seeker
     * @param buttonclose  reference to the button close of the cell that launch this function
     */
    protected void onReadButton(String email, Button buttonclose) {
        if(this.buttonClose != null){
            this.buttonClose.setVisible(false);
        }
        this.buttonClose = buttonclose;
        FXMLLoader loader = new FXMLLoader(JobVXS.class.getResource("jobseeker/profile-jobseeker.fxml"));
        Pane panel = null;
        try {
            panel = loader.load();
            ProfileJobSeekerController profileJobSeekerController = loader.getController();
            profileJobSeekerController.setJobSeeker(getJobSeekerFromList(email));
            this.panelRight.getChildren().setAll(panel);
        } catch (IOException e) {
            e.printStackTrace();
            this.errorMsg.setText("There is a problem with javafx ");
        }

    }

    /**
     * get a jobSeeker in the list by this email
     * @param email email of the job seeker
     * @return the job seeker of the list that have the same email
     */
    private JobSeeker getJobSeekerFromList(String email){
        for (JobSeeker j : this.jobSeekersObservableList) {
            if(j.getEmail().equals(email)){
                return j;
            }
        }
        return null;
    }

    /**
     * Launch the deletion process of the job seeker
     * @param email of the job seeker
     */
    protected void onDeleteButton(String email) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete the job seeker profile");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                this.facadeJobSeeker.delete(email);
                onClosePanelRight();
                this.jobSeekersObservableList.removeIf(j -> j.getEmail().equals(email));
            }
        } catch (Exception e) {
            this.errorMsg.setText("The jobSeeker could not be deleted");
            e.printStackTrace();
        }
    }


    /**
     * Close the panel with the information of a job seeker
     */
    protected void onClosePanelRight(){
        if(this.buttonClose != null){
            this.buttonClose.setVisible(false);
            this.buttonClose = null;
            panelRight.getChildren().clear();
            borderPane.setRight(null);
        }
    }


}
