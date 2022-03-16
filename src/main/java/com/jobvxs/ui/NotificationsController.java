package com.jobvxs.ui;

import com.jobvxs.bl.FacadeUser;
import com.jobvxs.bl.Notification;
import com.jobvxs.ui.ListViewCellNotification;
import com.jobvxs.ui.company.JobOfferListViewCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationsController {

    @FXML
    private ListView<Notification> listview;

    @FXML
    private Label labelInfoNotif;

    private ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList();

    /**
     * Set the view of the listView component
     */
    public void setListView(){
        ArrayList<Notification> notifList = null;
        try {
            notifList = FacadeUser.getFacadeUser().getAllNotification();
            if(notifList.size() == 0){
                labelInfoNotif.setText("You don't have notification for the moment ...");
            } else {
                labelInfoNotif.setText("");
            }
            notificationObservableList.addAll(notifList);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * Initialize the page
     */
    @FXML
    protected void initialize(){
        setListView();
        listview.setItems(notificationObservableList);
        listview.setCellFactory(jobSeekerListView -> new ListViewCellNotification(this));
    }

    /**
     * Action for the deleteButton to delete the notification
     * @param idNotif
     */
    protected void onDeleteButton(int idNotif) {
        try {
            FacadeUser.getFacadeUser().deleteNotification(idNotif);
            this.notificationObservableList.removeIf(n -> n.getId()==idNotif);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Action for the markedAsReadButton to mark the notification as read
     * @param idNotif
     */
    protected void onMarkedAsReadButton(int idNotif) {
        try {
            FacadeUser.getFacadeUser().setReadNotification(idNotif);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
