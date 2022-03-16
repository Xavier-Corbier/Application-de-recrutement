package com.jobvxs.pl.postgre;

import com.jobvxs.bl.Application;
import com.jobvxs.pl.ApplicationDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationDAOPostGre implements ApplicationDAO {
    Connection conn;

    /**
     * Construct RateCompanyDAOPostGre in PostGreSQL.
     * It is possible to handle an error if the Database is not connected
     * @throws SQLException sql connection
     */
    public ApplicationDAOPostGre(Connection conn) throws SQLException {
        this.conn = conn;
    }

    /**
     * Function that get all the application according to the email of the company (or job seeker)
     * @param email // email use by the query
     * @param isForCompany // true : getApplicationsByCompany | false : getApplicationsByjobSeeker
     * @return List of all the application
     * @throws SQLException error in the connection of the db
     */
    private ArrayList<Application> getApplications(String email, boolean isForCompany) throws SQLException{
        ArrayList<Application> applications = new ArrayList<>();

        String whereCondition = isForCompany? "WHERE \"emailcompany\" = ?" : "WHERE \"emailJobSeeker\" = ? ";

        // execute the request
        PreparedStatement pstmt = conn.prepareStatement("" +
                "SELECT \"idApp\", \"state\", \"idJobOffer\", \"emailJobSeeker\",\"coveringLetter\", js.\"name\" AS nomseeker, jo.\"name\" AS nomjob " +
                "FROM public.\"Application\" " +
                "JOIN public.\"JobSeeker\" js ON \"emailJobSeeker\" = \"email\" " +
                "JOIN public.\"JobOffer\" jo ON \"idJobOffer\" = \"idjoboffer\" "+
                 whereCondition +
                "ORDER BY jo.idjoboffer");

        pstmt.setString(1,email);

        ResultSet res = pstmt.executeQuery();

        // if it is empty
        if(!res.next()){
            throw new SQLException("Error: no Applications found");
        } else {
            do {
                applications.add(new Application(
                        res.getInt("idApp"),
                        res.getString("state"),
                        res.getInt("idJobOffer"),
                        res.getString("emailJobSeeker"),
                        res.getString("coveringLetter"),
                        res.getString("nomjob"),
                        res.getString("nomseeker")
                ));
            } while(res.next());
            // while we have a row
        }
        return applications;
    }

    /**
     * Get the list of application by the email of the company
     * @param emailCompany email of the company
     * @return the list of all application of the job offer's company
     * @throws SQLException error in the DB
     */
    public ArrayList<Application> getApplicationByCompany(String emailCompany) throws SQLException{
        return  getApplications(emailCompany,true);
    }

    /**
     * Get the list of application by the email of the jobSeeker
     * @param emailJobSeeker email of the job seeker
     * @return the list of all application of the job seeker
     * @throws SQLException error in the DB
     */
    public ArrayList<Application> getApplicationByJobSeeker(String emailJobSeeker) throws SQLException{
        return  getApplications(emailJobSeeker,false);
    }

    /**
     * Delete an application with its id
     * @param idApplication id of the application
     * @throws SQLException error in the DB
     */
    public void deleteApplication(int idApplication) throws SQLException{
        // execute the request
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM public.\"Application\" WHERE \"idApp\" = ?");

        pstmt.setInt(1,idApplication);
        pstmt.executeUpdate();
    }


    /**
     * Create an application
     * @param emailJobSeeker email of the job seeker
     * @param idJobOffer id of the job offer
     * @param coverLetter content of the cover letter
     * @return the id created by the DB for the application
     * @throws Exception error in the DB
     */
    public int createApplication(String emailJobSeeker, int idJobOffer, String coverLetter) throws Exception{

        String requete = "INSERT INTO public.\"Application\" " +
                "(\"state\",\"idJobOffer\",\"emailJobSeeker\",\"coveringLetter\") " +
                "VALUES ('Waiting',?,?,?) RETURNING \"idApp\"; ";

        PreparedStatement pstmt = conn.prepareStatement(requete);
        pstmt.setInt(1,idJobOffer);
        pstmt.setString(2,emailJobSeeker);
        pstmt.setString(3,coverLetter);

        // execute the request
        ResultSet res = pstmt.executeQuery();
        int idResult = -1;

        if(!res.next()){
            throw new Exception("Error: no application id found");
        } else {
            do {
                idResult = res.getInt(1);
            } while(res.next());
            // while we have a row
        }
        return idResult;
    }

    /**
     * Update the state of the application
     * @param idApplication id of the application
     * @param state the new state of the application
     * @throws Exception error in the DB
     */
    public void updateApplication(int idApplication, String state) throws Exception{
        String requete =  "UPDATE public.\"Application\" SET \"state\" = ";
        if(state.equals("Accepted")){
            requete+="'Accepted' ";
        }
        else if(state.equals("Rejected")){
            requete+="'Rejected' ";
        }
        else{
            throw new Exception("This is not a state for the application");
        }

        PreparedStatement pstmt = conn.prepareStatement(requete +" WHERE \"idApp\" = ?");

        pstmt.setInt(1,idApplication);

        pstmt.executeUpdate();
    }

    /**
     * Get the email of the jobSeeker concerned by the Application
     * @param idApplication
     * @return
     * @throws Exception
     */
    public String getEmailJobSeeker(int idApplication) throws Exception{
        String email = "";
        PreparedStatement pstmt = conn.prepareStatement("SELECT \"emailJobSeeker\" FROM public.\"Application\" WHERE \"idApp\" = ? ;");
        pstmt.setInt(1, idApplication);
        ResultSet res = pstmt.executeQuery();

        if(!res.next()){
            throw new Exception("Error: no email found ");
        } else {
            do{
                email = res.getString("emailJobSeeker");
            } while(res.next());
        }

        return email;
    }
}
