package com.jobvxs.pl;

import com.jobvxs.bl.CommentJobOffer;

import java.util.ArrayList;

public interface CommentJobOfferDAO {

    /**
     * Get all the comment on a jobOffer by giving it's id
     * @param idJobOffer
     * @return an arrayList of the comment for the concerned jobOffer
     * @throws Exception
     */
    public ArrayList<CommentJobOffer> getAllCommentJobOffer(int idJobOffer) throws Exception;

    /**
     * Update the comment on a jobOffer
     * @param idComment
     * @param commentJobOffer
     * @throws Exception
     */
    public void updateCommentJobOffer(int idComment, String commentJobOffer) throws Exception;

    /**
     * Create a comment on a jobOffer
     * @param idJobOffer
     * @param comment
     * @return
     * @throws Exception
     */
    public int createCommentJobOffer(int idJobOffer, String comment) throws Exception;

    /**
     * Delete a comment on a jobOffer
     * @param idComment
     * @throws Exception
     */
    public void deleteCommentJobOffer(int idComment) throws Exception;

    /**
     * Return the email of the jobSeeker concerned by the comment on the jobOffer
     * @param idComment
     * @return the email of the jobSeeker
     * @throws Exception
     */
    public String getEmailJobSeeker(int idComment) throws Exception;
}
