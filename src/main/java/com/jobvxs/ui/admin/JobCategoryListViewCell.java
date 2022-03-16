package com.jobvxs.ui.admin;

import com.jobvxs.bl.*;
import com.jobvxs.ui.admin.ListJobCategoryController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Class to manage the cell view of a job category
 */
public class JobCategoryListViewCell extends ListCell<JobCategory> {

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
    private ListJobCategoryController parent;

    public JobCategoryListViewCell(ListJobCategoryController ljsc) {
        parent = ljsc;
    }

    /**
     * Set the jobCategory information in the .fxml file
     * @param jobCategory
     * @param booleanValue
     */
    protected void updateItem(JobCategory jobCategory, boolean booleanValue) {
        super.updateItem(jobCategory, booleanValue);
        if(booleanValue || jobCategory == null) {
            setText(null);
            setGraphic(null);
        } else {
            loadFxml();
            labelNom.setText(String.valueOf(jobCategory.getName()));
            buttonClose.setVisible(false);
            setActionButton(jobCategory);
            buttonDelete.setVisible(true);
            setText(null);
            setGraphic(gridPane);
        }
    }

    /**
     * Set the different actions for the buttons
     * @param jobCategory
     */
    private void setActionButton(JobCategory jobCategory) {
        // button see information
        buttonSee.setOnAction(e -> {
            parent.onReadButton(jobCategory.getName(),buttonClose);
            buttonClose.setVisible(true);
        });
        // button delete profile
        buttonDelete.setOnAction(e -> parent.onDeleteButton(jobCategory.getName()));
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