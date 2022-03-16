package com.jobvxs.ui.company;

import com.jobvxs.bl.Administrator;
import com.jobvxs.bl.Company;
import com.jobvxs.bl.FacadeUser;
import com.jobvxs.bl.UserRole;
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
public class CompanyListViewCell extends ListCell<Company> {

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
    private ListCompanyController parent;

    /**
     * Constructor for the CompanyListViewCell class
     * @param ljsc
     */
    public CompanyListViewCell(ListCompanyController ljsc) {
        parent = ljsc;
    }

    /**
     * Set the company information in the .fxml file
     * @param company
     * @param booleanValue
     */
    protected void updateItem(Company company, boolean booleanValue) {
        super.updateItem(company, booleanValue);
        if(booleanValue || company == null) {
            setText(null);
            setGraphic(null);
        } else {
            loadFxml();
            labelNom.setText(String.valueOf(company.getUser().getEmail()));
            buttonClose.setVisible(false);
            setActionButton(company);
            checkRightForDelete();
            setText(null);
            setGraphic(gridPane);
        }
    }

    /**
     * Check if the user has the rigth for the delete action
     */
    private void checkRightForDelete() {
        try {
            FacadeUser facadeUser = FacadeUser.getFacadeUser();
            UserRole userRole = facadeUser.getRole();
            if (!(userRole instanceof Administrator)){
                buttonDelete.setVisible(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the different actions for the buttons
     * @param company
     */
    private void setActionButton(Company company) {
        // button see information
        buttonSee.setOnAction(e -> {
            parent.onReadButton(company.getUser().getEmail(),buttonClose);
            buttonClose.setVisible(true);
        });
        // button delete profile
        buttonDelete.setOnAction(e -> parent.onDeleteButton(company.getUser().getEmail()));
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