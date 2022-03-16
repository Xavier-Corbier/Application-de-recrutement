package com.jobvxs.pl.postgre;

import com.jobvxs.bl.Company;
import com.jobvxs.bl.JobOffer;
import com.jobvxs.bl.User;
import com.jobvxs.pl.JobOfferDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Implement the jobOfferDAO in PostGreSQL
 */
public class JobOfferDAOPostGre implements JobOfferDAO {
    Connection conn ;

    /**
     * Construct JobOfferDAO in PostGreSQL.
     * It is possible to handle an error if the Database is not connected
     * @throws SQLException
     */
    public JobOfferDAOPostGre(Connection conn) throws SQLException {
        this.conn = conn;
    }

    /**
     * Get a job offer by its id
     * @param idJobOffer
     * @return
     */
    @Override
    public JobOffer getJobOfferById(int idJobOffer) {
        return null;
    }

    /**
     * Delete a job offer with its id
     * @param idJobOffer
     */
    @Override
    public void deleteJobOffer(int idJobOffer) throws SQLException {
        // execute the request
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM public.\"JobOffer\" WHERE idjoboffer = ?");
        pstmt.setInt(1,idJobOffer);
        pstmt.executeUpdate();
    }

    /**
     * Create a job offer
     * @param jobOffer
     */
    @Override
    public void createJobOffer(JobOffer jobOffer, Company company) throws SQLException {
        User user = company.getUser();
        String requete = "INSERT INTO public.\"JobOffer\" VALUES (DEFAULT,?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(requete);
        pstmt.setString(1,jobOffer.getName());
        pstmt.setString(2,jobOffer.getType());
        pstmt.setDouble(3,jobOffer.getSalary());
        pstmt.setString(4,jobOffer.getDescription());
        pstmt.setString(5,jobOffer.getRequirements());
        pstmt.setString(6,jobOffer.getAdvantages());
        pstmt.setString(7,jobOffer.getWorkSchedule());
        pstmt.setString(8,user.getEmail());
        // execute the request
        pstmt.executeUpdate();
    }

    /**
     * Update a job offer
     * @param jobOfferModif
     */
    @Override
    public void updateJobOffer(Map<String, String> jobOfferModif) throws Exception {
        StringBuilder requete = new StringBuilder("UPDATE public.\"JobOffer\" ");
        PreparedStatement pstmt;
        int nbChange = 0;
        // create the syntax of the request
        nbChange = getNbChange(jobOfferModif, requete, nbChange);
        if(nbChange>0){
            pstmt = conn.prepareStatement(String.valueOf(requete));
            // set the request
            setNewValuesRequest(jobOfferModif, pstmt, nbChange);
            pstmt.executeUpdate();
        }
    }

    /**
     * Set the new values in the request
     * @param jobOfferModif
     * @param pstmt
     * @param nbChange
     * @throws SQLException
     */
    private void setNewValuesRequest(Map<String, String> jobOfferModif, PreparedStatement pstmt, int nbChange) throws SQLException {
        int i = 1;
        // Set the value of the ?
        for(Map.Entry<String,String> entry : jobOfferModif.entrySet()){
            if(entry.getKey().equals("idjoboffer")){ // is the where condition
                pstmt.setInt(nbChange +1 ,Integer.parseInt(entry.getValue()));
            }
            else{
                if (entry.getKey().equals("salary")){ // is int
                    pstmt.setDouble(i, Double.parseDouble(entry.getValue()));
                }
                else{
                    pstmt.setString(i, entry.getValue());
                }
                i++;
            }
        }
    }

    /**
     * Get the number of changement in the request
     * @param companyModif
     * @param requete
     * @param nbChange
     * @return
     */
    private int getNbChange(Map<String, String> companyModif, StringBuilder requete, int nbChange) {
        String whereCondition = "";
        for(Map.Entry<String,String> entry : companyModif.entrySet()){
            if(entry.getKey().equals("idjoboffer")){ // is the where condition
                whereCondition = " WHERE idjoboffer = ?";
            }
            else{
                if(nbChange == 0){
                    requete.append("SET ");
                }
                nbChange++;
                requete.append(entry.getKey()).append(" = ?,");
            }
        }
        requete.deleteCharAt(requete.length()-1);
        requete.append(whereCondition);
        return nbChange;
    }

    /**
     * Get all job offers
     * @return
     */
    @Override
    public ArrayList<JobOffer> getAllJobOffer() throws Exception {
        ArrayList<JobOffer> jobOffers = new ArrayList<>();
        Statement stmt = conn.createStatement();
        // execute the request
        ResultSet res = stmt.executeQuery("SELECT * FROM public.\"JobOffer\" ;");
        // if it is empty
        if(!res.next()){
            throw new Exception("Error: no Offer Found");
        } else {
            do {
                jobOffers.add(new JobOffer(res.getInt("idjoboffer"),
                        res.getString("name"),
                        res.getString("type"),
                        res.getDouble("salary"),
                        res.getString("description"),
                        res.getString("requirements"),
                        res.getString("advantages"),
                        res.getString("workschedule"),
                        res.getString("emailcompany")));
            } while(res.next());
            // while we have a row
        }
        return jobOffers;
    }

    /**
     * Get all job offers by email of the company
     * @return
     */
    @Override
    public ArrayList<JobOffer> getAllJobOfferByEmail(String email) throws Exception {
        ArrayList<JobOffer> jobOffers = new ArrayList<>();
        Statement stmt = conn.createStatement();
        // execute the request
        ResultSet res = stmt.executeQuery("SELECT * FROM public.\"JobOffer\" WHERE emailcompany='"+email+"'");
        // if it is empty
        if(!res.next()){
            throw new Exception("Error: no Offer Found");
        } else {
            do {
                jobOffers.add(new JobOffer(res.getInt("idjoboffer"),
                        res.getString("name"),
                        res.getString("type"),
                        res.getDouble("salary"),
                        res.getString("description"),
                        res.getString("requirements"),
                        res.getString("advantages"),
                        res.getString("workschedule"),
                        res.getString("emailcompany")));
            } while(res.next());
            // while we have a row
        }
        return jobOffers;
    }
}
