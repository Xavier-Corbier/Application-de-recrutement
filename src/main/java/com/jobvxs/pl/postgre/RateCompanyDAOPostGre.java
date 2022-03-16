package com.jobvxs.pl.postgre;

import com.jobvxs.bl.RatingCompany;
import com.jobvxs.pl.RateCompanyDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RateCompanyDAOPostGre implements RateCompanyDAO {
    Connection conn;

    /**
     * Construct RateCompanyDAOPostGre in PostGreSQL.
     * It is possible to handle an error if the Database is not connected
     * @throws SQLException Exception in the connection of the db
     */
    public RateCompanyDAOPostGre(Connection conn) throws SQLException {
        this.conn = conn;
    }

    /**
     * Get the list of the rates of a company
     * @param email email of the company
     * @return List of the rates of the company
     * @throws Exception for the DB
     */
    @Override
    public ArrayList<RatingCompany> getRatesByCompany(String email) throws Exception {
        ArrayList<RatingCompany> ratingCompanies = new ArrayList<>();
        // execute the request
        PreparedStatement pstmt = conn.prepareStatement("SELECT \"idRate\", \"nbStars\", \"comment\", \"emailJobSeeker\", \"emailCompany\", \"name\" FROM public.\"RateCompany\" JOIN public.\"JobSeeker\" ON \"emailJobSeeker\" = \"email\" WHERE \"emailCompany\" = ?");

        pstmt.setString(1,email);

        ResultSet res = pstmt.executeQuery();

        // if it is empty
        if(!res.next()){
            throw new Exception("Error: no Rates Found");
        } else {
            do {
                ratingCompanies.add(new RatingCompany(
                        res.getInt("idRate"),
                        res.getInt("nbStars"),
                        res.getString("comment"),
                        res.getString("emailJobSeeker"),
                        res.getString("emailCompany"),
                        res.getString("name")));
            } while(res.next());
            // while we have a row
        }
        return ratingCompanies;
    }

    /**
     * Get a rate according to its id
     * @param id id of the rate
     * @return the rate giving its id
     * @throws Exception for the DB
     */
    @Override
    public RatingCompany getRateCompany(int id) throws Exception {
        // execute the request
        PreparedStatement pstmt = conn.prepareStatement("SELECT \"idRate\", \"nbStars\", \"comment\", \"emailJobSeeker\", \"emailCompany\", \"name\" FROM public.\"RateCompany\" JOIN public.\"JobSeeker\" ON \"emailJobSeeker\" = \"email\" WHERE \"idRate\" = ?");

        pstmt.setInt(1,id);

        ResultSet res = pstmt.executeQuery();

        RatingCompany ratingCompany;

        // if it is empty
        if(!res.next()){
            throw new Exception("Error: no Rate Found");
        } else {
            do {
                ratingCompany = new RatingCompany(
                        res.getInt("idRate"),
                        res.getInt("nbStars"),
                        res.getString("comment"),
                        res.getString("emailJobSeeker"),
                        res.getString("emailCompany"),
                        res.getString("name"));
            } while(res.next());
            // while we have a row
        }
        return ratingCompany;
    }

    /**
     * Delete a rate whit its id
     * @param id id of the rate
     * @throws Exception for the DB
     */
    @Override
    public void deleteRateCompany(int id) throws Exception {
        // execute the request
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM public.\"RateCompany\" WHERE \"idRate\" = ?");

        pstmt.setInt(1,id);
        pstmt.executeUpdate();
    }

    /**
     * Update a rate
     * @param ratingCompany the news state of the rating company
     * @throws Exception for the DB
     */
    @Override
    public void updateRateCompany(RatingCompany ratingCompany) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement("UPDATE public.\"RateCompany\" SET \"nbStars\" = ?, \"comment\" = ? WHERE \"idRate\" = ?");

        pstmt.setInt(1,ratingCompany.getNbStars());
        pstmt.setString(2,ratingCompany.getComment());
        pstmt.setInt(3,ratingCompany.getIdRate());

        pstmt.executeUpdate();
    }

    /**
     * Create a rate
     * @param ratingCompany the state of the rating company
     * @return the id create by the database
     * @throws Exception for the DB
     */
    @Override
    public int createRateCompany(RatingCompany ratingCompany) throws Exception {

        String requete = "INSERT INTO public.\"RateCompany\" (\"nbStars\",\"comment\",\"emailJobSeeker\",\"emailCompany\") VALUES (?,?,?,?) RETURNING \"idRate\"; ";

        PreparedStatement pstmt = conn.prepareStatement(requete);
        pstmt.setInt(1,ratingCompany.getNbStars());
        pstmt.setString(2,ratingCompany.getComment());
        pstmt.setString(3,ratingCompany.getEmailJobSeeker());
        pstmt.setString(4,ratingCompany.getEmailCompany());

        // execute the request
        ResultSet res = pstmt.executeQuery();
        int idResult = -1;

        if(!res.next()){
            throw new Exception("Error: no Rate id Found");
        } else {
            do {
                idResult = res.getInt(1);
            } while(res.next());
            // while we have a row
        }
        return idResult;
    }
}
