package com.jobvxs.ui;

import com.jobvxs.bl.*;
import com.jobvxs.ui.NotificationsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Class to manage the cell view of a Notification
 */
public class ListViewCellNotification extends ListCell<Notification> {

    @FXML
    private Label title;
    @FXML
    private Label desc;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonMarkedAsRead;

    private FXMLLoader mLLoader;
    private NotificationsController parent;


    /**
     * Constructor of the ListViewCellNotification class
     * @param notifControl
     */
    public ListViewCellNotification(NotificationsController notifControl){
        parent = notifControl;
    }

    /**
     * Update the notification on the list view
     * @param notification
     * @param empty
     */
    @Override
    protected void updateItem(Notification notification, boolean empty) {
        super.updateItem(notification, empty);
        if(empty || notification == null) {
            setText(null);
            setGraphic(null);
        } else {
            loadFxml();
            title.setText("Title : " + notification.getTitle());
            desc.setText("Description : " + notification.getDescription());
            setActionButton(notification);
            if(notification.isRead() == true){
                this.setStyle("-fx-background-color:green");
            } else {
                this.setStyle("-fx-background-color:red");
            }
            setText(null);
            setGraphic(gridPane);
        }
    }

    /**
     * Load the fxmlFile
     */
    private void loadFxml() {
        if (mLLoader == null) {
            mLLoader = new FXMLLoader(getClass().getResource("ListCellNotification.fxml"));
            mLLoader.setController(this);
            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set the action of the button of the cell
     * @param notification
     */
    private void setActionButton(Notification notification) {
        buttonDelete.setOnAction(e -> {
            parent.onDeleteButton(notification.getId());
        });

        buttonMarkedAsRead.setOnAction(e -> {
            parent.onMarkedAsReadButton(notification.getId());
            this.setStyle("-fx-background-color:green");
        });

    }
}