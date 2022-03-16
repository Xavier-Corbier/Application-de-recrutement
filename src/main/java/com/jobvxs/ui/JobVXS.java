package com.jobvxs.ui;

import com.jobvxs.bl.FacadeUser;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * JobVXS is the main Class of the User interface
 */

public class JobVXS extends Application {

    private Stage stage;

    /**
     * The start function permit loading the login fxml file and give details about the
     * Application
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(getPage("menu-dashboard.fxml"));
        this.stage = stage;
        this.stage.setTitle("JobVXS");
        this.stage.setScene(scene);
        this.stage.setMaximized(true);
        this.stage.getIcons().add(new Image("file:icon.png"));
        this.stage.show();
    }

    /**
     * static function that get the page fxml associated with the filename
     * @param fileName
     * @return Pane with the fxml page
     */
    public static Pane getPage(String fileName){
        Pane view = null;
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(JobVXS.class.getResource(fileName));
            view = fxmlLoader.load();
        }catch(Exception e){
            e.printStackTrace();
        }
        return view;
    }

    /**
     * Make the transition with the new UI
     * @param e
     */
    public static void loadNewIU(ActionEvent e,String fileName, String title) throws Exception {
        FXMLLoader loader = new FXMLLoader(JobVXS.class.getResource(fileName));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new Exception("Error with JavaFx");
        }
        Scene scene = ((Node) e.getSource()).getScene();
        scene.setRoot(root);
        ((Stage) scene.getWindow()).setTitle(title);
    }

    /**
     * Disconnect the user and load the dashboard of the guest
     * @param sc
     */
    public static void signOut(Scene sc){
        try {
            FacadeUser.getFacadeUser().logout();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        FXMLLoader loader = new FXMLLoader(JobVXS.class.getResource("menu-dashboard.fxml")); // dashboard 

        String title = "JobVXS";

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return;
        }
        // no problem encounter => display the dashboard
        sc.setRoot(root);
        ((Stage) sc.getWindow()).setTitle(title);
    }

    public static void main(String[] args) {
        launch();
    }
}