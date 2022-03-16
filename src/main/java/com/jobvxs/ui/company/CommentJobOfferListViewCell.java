package com.jobvxs.ui.company;

import com.jobvxs.bl.*;
import com.jobvxs.ui.NotificationsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

public class CommentJobOfferListViewCell extends ListCell<CommentJobOffer> {

    @FXML
    private GridPane gridPane;

    @FXML
    private TextArea textAreaComment;

    @FXML
    private Label labelDescription;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonDelete;

    private FXMLLoader mLLoader;

    private SeeJobOfferController parent;

    /**
     * Constructor for the CommentJobOfferListViewCell class
     * @param seeJobOfferController
     */
    public CommentJobOfferListViewCell(SeeJobOfferController seeJobOfferController){
        parent = seeJobOfferController;
    }

    /**
     * Update a commentJobOffer of the list View
     * @param commentJobOffer
     * @param empty
     */
    @Override
    protected void updateItem(CommentJobOffer commentJobOffer, boolean empty){
        super.updateItem(commentJobOffer, empty);
        if(commentJobOffer == null || empty){
            setText(null);
            setGraphic(null);
        } else {
            loadFxml();
            String mailUser = null;
            try {
                mailUser = FacadeCompany.getFacadeCompany().getOriginatorCommentJobOffer(commentJobOffer.getIdComment());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            setVisibilityForButton(commentJobOffer.getIdComment());
            String description = "Comment of : " + mailUser;
            labelDescription.setText(description);
            textAreaComment.setText(commentJobOffer.getComment());
            setActionButton(commentJobOffer);
            setText(null);
            setGraphic(gridPane);
        }
    }

    /**
     * Load the fxmlFile
     */
    private void loadFxml() {
        if (mLLoader == null) {
            mLLoader = new FXMLLoader(getClass().getResource("list-cell-comment-job-offer.fxml"));
            mLLoader.setController(this);
            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set the action for the button deleteComment and updateComment
     * @param commentJobOffer
     */
    private void setActionButton(CommentJobOffer commentJobOffer) {
        buttonDelete.setOnAction(e -> {
            parent.onDeleteButtonComment(commentJobOffer.getIdComment());
        });

        buttonUpdate.setOnAction(e -> {
            String comment = textAreaComment.getText();
            parent.onUpdateButtonComment(comment ,commentJobOffer.getIdComment());
        });

    }

    private void setVisibilityForButton(int idComment){
        try {
            if(FacadeUser.getFacadeUser().getUser() != null
                    && FacadeUser.getFacadeUser().getUser().getRole() == User.UserType.JOBSEEKER
                    && FacadeUser.getFacadeUser().getUser().getEmail().equals(FacadeCompany.getFacadeCompany().getOriginatorCommentJobOffer(idComment))){
                buttonUpdate.setVisible(true);
                buttonDelete.setVisible(true);
            } else {
                buttonUpdate.setVisible(false);
                buttonDelete.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
