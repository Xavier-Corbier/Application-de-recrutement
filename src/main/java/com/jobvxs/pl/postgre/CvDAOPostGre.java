package com.jobvxs.pl.postgre;

import com.jobvxs.bl.CV;
import com.jobvxs.bl.FacadeJobSeeker;
import com.jobvxs.bl.JobSeeker;
import com.jobvxs.pl.CvDAO;

import java.sql.*;

public class CvDAOPostGre implements CvDAO {
    Connection conn;

    /**
     * Constructor for the CvDAOPostGre class
     * @param conn
     * @throws SQLException
     */
    public CvDAOPostGre(Connection conn) throws SQLException{
        this.conn = conn;
    }

    /**
     * Get the CV by giving the email of the user concerned
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public CV getCVById(String email) throws Exception {
        CV cv = null;
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM public.\"CV\" WHERE email='"+email+"'");
        if(!res.next()){
            cv = null;
        } else {
            cv = new CV(
                    res.getString("education"),
                    res.getString("workExperience"),
                    res.getString("skillAndInterests"),
                    res.getString("references"));
        }
        return cv;
    }

    /**
     * Update the CV
     * @param cv
     * @return
     */
    @Override
    public boolean updateCV(CV cv) throws SQLException {
        FacadeJobSeeker facadeJobSeeker = FacadeJobSeeker.getFacadeJobSeeker();
        String email = facadeJobSeeker.getJobseeker().getEmail();
        String requete = "UPDATE public.\"CV\" SET education='"+cv.getEducation()+"' WHERE email='"+email+"' ";
        String requete2 = "UPDATE public.\"CV\" SET \"workExperience\"='"+cv.getWorkExperience()+"' WHERE email='"+email+"' ";
        String requete3 = "UPDATE public.\"CV\" SET \"skillAndInterests\"='"+cv.getSkillsAndInterests()+"' WHERE email='"+email+"' ";
        String requete4 = "UPDATE public.\"CV\" SET \"references\"='"+cv.getReferences()+"' WHERE email='"+email+"' ";

        Statement stmt = conn.createStatement();
        Statement stmt2 = conn.createStatement();
        Statement stmt3 = conn.createStatement();
        Statement stmt4 = conn.createStatement();

        stmt.executeUpdate(requete);
        stmt2.executeUpdate(requete2);
        stmt3.executeUpdate(requete3);
        stmt4.executeUpdate(requete4);

        return true;
    }

    /**
     * Create a CV by default
     * @return
     * @throws Exception
     */
    @Override
    public CV createCV() throws Exception {
        FacadeJobSeeker facadeJobSeeker = FacadeJobSeeker.getFacadeJobSeeker();
        String email = facadeJobSeeker.getJobseeker().getEmail();
        CV cv = new CV("", "", "", "");
        String requete = "INSERT INTO public.\"CV\" VALUES (?, '', '', '', '');";

        PreparedStatement pstmt = conn.prepareStatement(requete);
        pstmt.setString(1, email);

        pstmt.executeUpdate();
        return cv;
    }
}
