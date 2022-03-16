package com.jobvxs.ui.admin;

import com.jobvxs.bl.*;
import com.jobvxs.ui.JobVXS;
import com.jobvxs.ui.company.JobOfferListViewCell;
import com.jobvxs.ui.company.SeeJobOfferController;
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
import java.util.ArrayList;
import java.util.Optional;

/**
 * Class to show the list of the job category
 */
public class ListJobCategoryController {

    @FXML
    private ListView<JobCategory> listView;
    private ObservableList<JobCategory> jobCategoriesObservableList;
    @FXML
    private AnchorPane panelRight;
    @FXML
    private BorderPane borderPane;

    @FXML
    private Text errorMsg;
    private Button buttonClose;

    /**
     * Constructor for the ListJobCategoryController
     */
    public ListJobCategoryController(){
        updateList();
    }

    /**
     * Update list of job categories
     */
    private void updateList() {
        jobCategoriesObservableList = FXCollections.observableArrayList();
        ArrayList<JobCategory> arrayList = getListJobCategories();
        jobCategoriesObservableList.addAll(arrayList);
    }

    /**
     * Get list of the job category
     * @return
     */
    private ArrayList<JobCategory> getListJobCategories() {
        ArrayList<JobCategory> arrayList ;
        try {
            FacadeAdministrator facadeAdministrator = FacadeAdministrator.getFacadeAdministrator();
            arrayList = facadeAdministrator.getAllJobCategories();
        }
        catch (Exception e){
            arrayList = new ArrayList<JobCategory>();
        }
        return arrayList;
    }

    /**
     * Initialise the list page of the jobCategories (called automatically)
     */
    @FXML
    protected void initialize(){
        listView.setItems(jobCategoriesObservableList);
        listView.setCellFactory(jobSeekerListView -> new JobCategoryListViewCell(this));
    }

    /**
     * Display a panel for update the job category
     * @param name
     * @param buttonclose
     */
    protected void onReadButton(String name, Button buttonclose) {
        if(this.buttonClose != null){
            this.buttonClose.setVisible(false);
        }
        this.buttonClose = buttonclose;
        FXMLLoader loader = new FXMLLoader(JobVXS.class.getResource("admin/update-job-category.fxml"));
        try {
            loadJobCategory(name, loader);
        } catch (IOException e) {
            e.printStackTrace();
            this.errorMsg.setText("There is a problem with javafx ");
        }
        listView.setItems(jobCategoriesObservableList);
    }

    /**
     * Load the job category in the database
     * @param name
     * @param loader
     * @throws IOException
     */
    private void loadJobCategory(String name, FXMLLoader loader) throws IOException {
        Pane panel = loader.load();
        UpdateJobCategoryController updateJobCategoryController = loader.getController();
        updateList();
        updateJobCategoryController.updateField(name);
        this.panelRight.getChildren().setAll(panel);
    }

    /**
     * Launch the deletion process of the job category
     * @param name
     */
    protected void onDeleteButton(String name) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete the job category");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                FacadeAdministrator facadeAdministrator = FacadeAdministrator.getFacadeAdministrator();
                facadeAdministrator.deleteJobCategory(name);
                onClosePanelRight();
                this.jobCategoriesObservableList.removeIf(j -> j.getName().equals(name));
            }

        } catch (Exception e) {
            this.errorMsg.setText("The job category could not be deleted");
            e.printStackTrace();
        }
    }

    /**
     * Close the panel with the information of a job category
     */
    @FXML
    protected void onClosePanelRight(){
        if(this.buttonClose != null){
            this.buttonClose.setVisible(false);
            this.buttonClose = null;
            panelRight.getChildren().clear();
            borderPane.setRight(null);
        }
    }
}