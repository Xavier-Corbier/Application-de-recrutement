package com.jobvxs.bl;

public class CommentJobOffer {

    private int idComment;
    private String comment;

    /**
     * Constructor for the CommentJobOffer class
     * @param idComment
     * @param comment
     */
    public CommentJobOffer(int idComment, String comment){
        this.idComment = idComment;
        this.comment = comment;
    }

    /**
     * Getter for the id of a commentJobOffer
     * @return
     */
    public int getIdComment() {
        return idComment;
    }

    /**
     * Getter for the comment of a commentJobOffer
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter for the comment of a commentJobOffer
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Setter for the id of a comment of a commentJobOffer
     * @param idComment
     */
    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }
}
