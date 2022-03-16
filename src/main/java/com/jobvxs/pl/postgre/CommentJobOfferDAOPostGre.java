package com.jobvxs.pl.postgre;

import com.jobvxs.bl.CommentJobOffer;
import com.jobvxs.bl.FacadeUser;
import com.jobvxs.bl.Notification;
import com.jobvxs.pl.CommentJobOfferDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentJobOfferDAOPostGre implements CommentJobOfferDAO {

    Connection conn;

    /**
     * Constructor of the CommentJobOfferDAOPostGre class
     * @param conn
     * @throws SQLException
     */
    public CommentJobOfferDAOPostGre(Connection conn) throws SQLException {
        this.conn = conn;
    }

    /**
     * Get all comment of a JobOffer
     * @param idJobOffer
     * @return
     * @throws Exception
     */
    public ArrayList<CommentJobOffer> getAllCommentJobOffer(int idJobOffer) throws Exception{
        ArrayList<CommentJobOffer> arrayList = new ArrayList<CommentJobOffer>();
        PreparedStatement pstmt = conn.prepareStatement("SELECT \"idComment\", \"comment\" FROM public.\"CommentJobOffer\" WHERE \"idJobOffer\" = ?");
        pstmt.setInt(1, idJobOffer);

        ResultSet res = pstmt.executeQuery();

        CommentJobOffer commentJobOffer;

        if(!res.next()){
            throw new Exception("Error: no commentJobOffer Found");
        } else {
            do {
                commentJobOffer = new CommentJobOffer(
                       res.getInt("idComment"),
                        res.getString("comment")
                );
                arrayList.add(commentJobOffer);
            } while(res.next());
            // while we have a row
        }

        return arrayList;
    }

    /**
     * Update the comment of a JobOffer
     * @param idComment
     * @param commentJobOffer
     * @throws Exception
     */
    public void updateCommentJobOffer(int idComment, String commentJobOffer) throws Exception{
        PreparedStatement pstmt = conn.prepareStatement("UPDATE public.\"CommentJobOffer\" SET \"comment\" = ? WHERE \"idComment\" = ? ");
        pstmt.setString(1, commentJobOffer);
        pstmt.setInt(2, idComment);

        pstmt.executeUpdate();
    }

    /**
     * Create a comment for a JobOffer
     * @param idJobOffer
     * @param comment
     * @return
     * @throws Exception
     */
    public int createCommentJobOffer(int idJobOffer, String comment) throws Exception{
        int idComment = 0;

        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO public.\"CommentJobOffer\" VALUES (DEFAULT,?,?,?) RETURNING \"idComment\";");
        pstmt.setString(1, comment);

        String emailJobSeeker = FacadeUser.getFacadeUser().getUser().getEmail();
        pstmt.setString(2, emailJobSeeker);
        pstmt.setInt(3, idJobOffer);

        ResultSet res = pstmt.executeQuery();

        if(!res.next()){
            throw new Exception("Error: no id was return for commentJobOffer");
        } else {
            do {
                idComment = res.getInt("idComment");
            } while (res.next());
        }

        return idComment;
    }

    /**
     * Delete a comment on a jobOffer
     * @param idComment
     * @throws Exception
     */
    public void deleteCommentJobOffer(int idComment) throws Exception{
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM public.\"CommentJobOffer\" WHERE \"idComment\" = ? ");
        pstmt.setInt(1, idComment);

        pstmt.executeUpdate();
    }

    /**
     * Get the email of the jobSeeker concerned by the comment on the jobOffer
     * @param idComment
     * @return
     * @throws Exception
     */
    public String getEmailJobSeeker(int idComment) throws Exception{
        String email;
        PreparedStatement pstmt = conn.prepareStatement("SELECT \"emailJobSeeker\" FROM public.\"CommentJobOffer\" WHERE \"idComment\" = ? ;");
        pstmt.setInt(1, idComment);

        ResultSet res = pstmt.executeQuery();
        if(!res.next()){
            throw new Exception("Error: no email was found");
        } else {
            do {
                email = res.getString("emailJobSeeker");
            } while (res.next());
        }

        return email;
    }
}
