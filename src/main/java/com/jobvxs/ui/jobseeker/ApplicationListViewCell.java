package com.jobvxs.ui.jobseeker;

import com.jobvxs.bl.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ApplicationListViewCell extends ListCell<Application> {
    private ListApplicationJobSeekerController parent;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label nomJob;

    @FXML
    private Label state;

    @FXML
    private Button seeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button buttonClose;

    private FXMLLoader mLLoader;

    /**
     * Constructor for the ApplicationListViewCell class
     * @param lajc
     */
    public ApplicationListViewCell(ListApplicationJobSeekerController lajc){
        parent = lajc;
    }

    /**
     * Set the appication information in the .fxml file
     * @param application application that represent the cell
     * @param b boolean for the parent (if we display or not the cell)
     */
    @Override
    protected void updateItem(Application application, boolean b) {
        super.updateItem(application, b);

        if (b || application == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("list-application-jobseeker-cell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            // Label for the name of the job offer's application
            nomJob.setText(application.getNomJobOffer());
            // Label for the state of the application
            state.setText(application.getState().toString());
            switch (application.getState()) {
                case ACCEPTED -> state.setTextFill(Color.BLUE);
                case REJECTED -> state.setTextFill(Color.RED);
                case WAITING -> state.setTextFill(Color.GREEN);
            }

            buttonClose.setVisible(false);

            // button see information
            seeButton.setOnAction(e -> {
                parent.onReadButton(application.getIdApp(),buttonClose);
                buttonClose.setVisible(true);
            });

            // button delete profile
            deleteButton.setOnAction(e -> {
                parent.onDeleteButton(application.getIdApp());
            });

            // button close profile window
            buttonClose.setOnAction(e -> {
                parent.onClosePanelRight();
            });

            setText(null);
            setGraphic(gridPane);

        }
    }

}
