package com.jobvxs.ui.company;

import com.jobvxs.bl.Company;
import com.jobvxs.bl.FacadeCompany;
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


/**
 * Class to show the list of the companies
 */
public class ListCompanyController {

    @FXML
    private ListView<Company> listView;
    private ObservableList<Company> companiesObservableList;
    private FacadeCompany facadeCompany;
    @FXML
    private AnchorPane panelRight;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Text errorMsg;
    private Button buttonClose;

    /**
     * Constructor for the ListCompanyController class
     */
    public ListCompanyController(){
        companiesObservableList = FXCollections.observableArrayList();
        try {
            facadeCompany = FacadeCompany.getFacadeCompany();
            companiesObservableList.addAll(facadeCompany.getList());
        }
        catch (Exception e){
            this.borderPane.setCenter(new Text("There is no company yet"));
            e.printStackTrace();
        }
    }

    /**
     * Initialise the list page of the companies (called automatically)
     */
    @FXML
    protected void initialize(){
        listView.setItems(companiesObservableList);
        listView.setCellFactory(jobSeekerListView -> new CompanyListViewCell(this));
    }

    /**
     * Display a panel with the information of the company
     * @param email
     * @param buttonclose
     */
    protected void onReadButton(String email, Button buttonclose) {
        if(this.buttonClose != null){
            this.buttonClose.setVisible(false);
        }
        this.buttonClose = buttonclose;
        FXMLLoader loader = new FXMLLoader(JobVXS.class.getResource("company/see-company.fxml"));
        try {
            loadCompany(email, loader);
        } catch (IOException e) {
            e.printStackTrace();
            this.errorMsg.setText("There is a problem with javafx ");
        }
    }

    /**
     * Load the company in the database
     * @param email
     * @param loader
     * @throws IOException
     */
    private void loadCompany(String email, FXMLLoader loader) throws IOException {
        Pane panel = loader.load();
        SeeCompanyProfileController seeCompanyProfileController = loader.getController();
        seeCompanyProfileController.setCompany(getCompanyFromList(email));
        seeCompanyProfileController.loadFieldCompany();
        this.panelRight.getChildren().setAll(panel);
    }

    /**
     * Get a company in the list by this email
     * @param email
     * @return
     */
    private Company getCompanyFromList(String email){
        for (Company c : this.companiesObservableList) {
            if(c.getUser().getEmail().equals(email)){
                return c;
            }
        }
        return null;
    }

    /**
     * Launch the deletion process of the company
     * @param email
     */
    protected void onDeleteButton(String email) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete the company");
            alert.setContentText("Are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                this.facadeCompany.delete(email);
                onClosePanelRight();
                this.companiesObservableList.removeIf(j -> j.getUser().getEmail().equals(email));
            }

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