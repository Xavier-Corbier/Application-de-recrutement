package com.jobvxs.ui.jobseeker;

import com.jobvxs.bl.JobSeeker;
import com.jobvxs.ui.JobVXS;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class JobSeekerListViewCell extends ListCell<JobSeeker> {

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

    private ListJobSeekerController parent;

    /**
     * Constructor for the JobSeekerListViewCell class
     * @param ljsc
     */
    public JobSeekerListViewCell(ListJobSeekerController ljsc) {
        parent = ljsc;
    }

    /**
     * Set the jobSeeker information in the .fxml file
     * @param jobSeeker job seeker that represent the cell
     * @param b boolean for the parent (if we display or not the cell)
     */
    @Override
    protected void updateItem(JobSeeker jobSeeker, boolean b) {
        super.updateItem(jobSeeker, b);

        if(b || jobSeeker == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("list-cell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            // label for the email (name) of the jobseeker
            labelNom.setText(String.valueOf(jobSeeker.getEmail()));

            buttonClose.setVisible(false);

            // button see information
            buttonSee.setOnAction(e -> {
                parent.onReadButton(jobSeeker.getEmail(),buttonClose);
                buttonClose.setVisible(true);
            });

            // button delete profile
            buttonDelete.setOnAction(e -> parent.onDeleteButton(jobSeeker.getEmail()));

            // button close profile window
            buttonClose.setOnAction(e -> parent.onClosePanelRight());

            setText(null);
            setGraphic(gridPane);
        }


    }
}
