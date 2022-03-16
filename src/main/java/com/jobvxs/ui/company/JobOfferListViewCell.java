package com.jobvxs.ui.company;

import com.jobvxs.bl.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Class to manage the cell view of a Company
 */
public class JobOfferListViewCell extends ListCell<JobOffer> {

    @FXML
    private Label id;
    @FXML
    private Label labelNom;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button buttonSee;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonClose;
    private FXMLLoader mLLoader;
    private ListJobOfferController parent;

    /**
     * Constructor for the JobOfferListViewCell class
     * @param ljsc
     */
    public JobOfferListViewCell(ListJobOfferController ljsc) {
        parent = ljsc;
    }

    /**
     * Set the jobOffer information in the .fxml file
     * @param jobOffer
     * @param booleanValue
     */
    protected void updateItem(JobOffer jobOffer, boolean booleanValue) {
        super.updateItem(jobOffer, booleanValue);
        if(booleanValue || jobOffer == null) {
            setText(null);
            setGraphic(null);
        } else {
            loadFxml();
            labelNom.setText(String.valueOf(jobOffer.getName()));
            buttonClose.setVisible(false);
            setActionButton(jobOffer);
            checkRightForDelete(jobOffer);
            setText(null);
            setGraphic(gridPane);
        }
    }

    /**
     * Check if the user has the rigth for the delete action
     */
    private void checkRightForDelete(JobOffer jobOffer) {
        try {
            FacadeUser facadeUser = FacadeUser.getFacadeUser();
            User user = facadeUser.getUser();
            if(user == null){
                buttonDelete.setVisible(false);
            } else {
                UserRole userRole = facadeUser.getRole();
                if (!(userRole instanceof Administrator)&&!user.getEmail().equals(jobOffer.getEmailCompany())){
                    buttonDelete.setVisible(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the different actions for the buttons
     * @param jobOffer
     */
    private void setActionButton(JobOffer jobOffer) {
        // button see information
        buttonSee.setOnAction(e -> {
            parent.onReadButton(jobOffer.getIdJobOffer(),buttonClose);
            buttonClose.setVisible(true);
        });
        // button delete profile
        buttonDelete.setOnAction(e -> parent.onDeleteButton(jobOffer.getIdJobOffer()));
        // button close profile window
        buttonClose.setOnAction(e -> parent.onClosePanelRight());
    }

    /**
     * Load the fxmlFile
     */
    private void loadFxml() {
        if (mLLoader == null) {
            mLLoader = new FXMLLoader(getClass().getResource("list-cell.fxml"));
            mLLoader.setController(this);
            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}