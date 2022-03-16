package com.jobvxs.ui.company;

import com.jobvxs.bl.*;
import com.jobvxs.ui.JobVXS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to show the list of the job offers
 */
public class ListJobOfferController {

    @FXML
    private ListView<JobOffer> listView;
    private ObservableList<JobOffer> jobOffersObservableList;
    private FacadeCompany facadeCompany;
    @FXML
    private AnchorPane panelRight;
    @FXML
    private BorderPane borderPane;

    @FXML
    private Text errorMsg;
    private Button buttonClose;

    /**
     * Constructor for the ListJobOfferController
     */
    public ListJobOfferController(){
        updateList();
    }

    /**
     * Update list of job offer
     */
    private void updateList() {
        jobOffersObservableList = FXCollections.observableArrayList();
        ArrayList<JobOffer> arrayList = getListJobOffer();
        jobOffersObservableList.addAll(arrayList);
    }

    /**
     * Get list of the job offer thanks to the type of user
     * @return
     */
    private ArrayList<JobOffer> getListJobOffer() {
        ArrayList<JobOffer> arrayList ;
        try {
            FacadeUser facadeUser = FacadeUser.getFacadeUser();
            User user = facadeUser.getUser();
            facadeCompany = FacadeCompany.getFacadeCompany();
            if (user == null){
                arrayList = facadeCompany.getAllJobOffer();
            } else {
                UserRole userRole = facadeUser.getRole();
                if(userRole instanceof Company){
                    arrayList = facadeCompany.getOwnOffer();
                } else {
                    arrayList = facadeCompany.getAllJobOffer();
                }
            }
        }
        catch (Exception e){
            arrayList = new ArrayList<JobOffer>();
        }
        return arrayList;
    }

    /**
     * Initialise the list page of the jobOffers (called automatically)
     */
    @FXML
    protected void initialize(){
        listView.setItems(jobOffersObservableList);
        listView.setCellFactory(jobSeekerListView -> new JobOfferListViewCell(this));
    }

    /**
     * Display a panel with the information of the job offer
     * @param id
     * @param buttonclose
     */
    protected void onReadButton(int id, Button buttonclose) {
        if(this.buttonClose != null){
            this.buttonClose.setVisible(false);
        }
        this.buttonClose = buttonclose;
        FXMLLoader loader = new FXMLLoader(JobVXS.class.getResource("company/see-job-offer.fxml"));
        try {
            loadJobOffer(id, loader);
        } catch (IOException e) {
            e.printStackTrace();
            this.errorMsg.setText("There is a problem with javafx ");
        }
        listView.setItems(jobOffersObservableList);
    }

    /**
     * Load the job offer in the database
     * @param id
     * @param loader
     * @throws IOException
     */
    private void loadJobOffer(int id, FXMLLoader loader) throws IOException {
        Pane panel = loader.load();
        SeeJobOfferController seeJobOfferController = loader.getController();
        updateList();
        JobOffer jobOffer = getJobOfferFromList(id);
        facadeCompany.setJobOffer(jobOffer);
        seeJobOfferController.setJobOffer(jobOffer);
        this.panelRight.getChildren().setAll(panel);
    }

    /**
     * Get a job offer in the list by this id
     * @param id
     * @return
     */
    private JobOffer getJobOfferFromList(int id){
        for (JobOffer j : this.jobOffersObservableList) {
            if(j.getIdJobOffer()==id){
                return j;
            }
        }
        return null;
    }

    /**
     * Launch the deletion process of the job offer
     * @param id
     */
    protected void onDeleteButton(int id) {
        try {
            this.facadeCompany.deleteJobOffer(id);
            onClosePanelRight();
            this.jobOffersObservableList.removeIf(j -> j.getIdJobOffer()==(id));
        } catch (Exception e) {
            this.errorMsg.setText("The company could not be deleted");
            e.printStackTrace();
        }
    }

    /**
     * Close the panel with the information of a job seeker
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