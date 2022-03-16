package com.jobvxs.ui.company;

import com.jobvxs.bl.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ApplicationCompanyListViewCell extends ListCell<Application> {
    private ListApplicationCompanyController parent;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label nomSeeker;

    @FXML
    private Label jobOffer;

    @FXML
    private Label applicationNumber;

    @FXML
    private Label state;

    @FXML
    private Button seeButton;

    @FXML
    private Button rejectButton;

    @FXML
    private Button acceptButton;

    @FXML
    private Button closeButton;

    private FXMLLoader mLLoader;

    /**
     * Constructor for the ApplicationCompanyListViewCell class
     * @param lacjc
     */
    public ApplicationCompanyListViewCell(ListApplicationCompanyController lacjc){
        parent = lacjc;
    }

    /**
     * Set the appication information in the .fxml file
     * @param application application information
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
                mLLoader = new FXMLLoader(getClass().getResource("list-application-company-cell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            acceptButton.managedProperty().bind(acceptButton.visibleProperty());
            rejectButton.managedProperty().bind(rejectButton.visibleProperty());
            state.managedProperty().bind(state.visibleProperty());

            int index = parent.getIdAppFromTheList(application.getIdApp());

            if(index == 0){ // We first on this jobOffer, so we display the name of the job offer
                jobOffer.setText(application.getNomJobOffer());
            }
            else{ // we hide the first row with the name of the jobOffer
                gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 1);
            }

            // Label for the number of the application according to a job offer
            applicationNumber.setText("Application "+(index+1)+" : ");
            // Label for the name of the job seeker's application
            nomSeeker.setText(application.getNomJobSeeker());

            // button see information
            seeButton.setOnAction(e -> {
                parent.onReadButton(application.getIdApp(),closeButton);
                closeButton.setVisible(true);
            });

            // button close for the application view
            closeButton.setOnAction(e -> {
                parent.onClosePanelRight();
            });

            switch (application.getState()){
                case REJECTED :
                    state.setText("Rejected");
                    state.setTextFill(Color.RED);
                    hideButton();
                    break;
                case ACCEPTED :
                    state.setText("Accepted");
                    state.setTextFill(Color.GREEN);
                    hideButton();
                    break;
                 case WAITING :
                    // button reject application
                    rejectButton.setOnAction(e -> {
                        parent.onRejectButton(application.getIdApp());
                    });
                    // button accept application
                    acceptButton.setOnAction(e -> {
                        parent.onAcceptButton(application.getIdApp());
                    });
                    break;
            }

            setText(null);
            setGraphic(gridPane);
        }
    }

    /**
     * Hide the button if the application has already been rejected or accepted
     */
    private void hideButton(){
        acceptButton.setVisible(false);
        rejectButton.setVisible(false);
        state.setVisible(true);
    }

}
