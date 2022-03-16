package com.jobvxs.pl;
import com.jobvxs.bl.RatingCompany;

import java.util.ArrayList;
import java.util.List;


/**
 * Allows to maintain the Company with the type of save
 */
public interface RateCompanyDAO {

    /**
     * Get the list of the rates of a company
     * @param email email of the company
     * @return List of the rates of the company
     * @throws Exception for the DB
     */
    public ArrayList<RatingCompany> getRatesByCompany(String email) throws Exception;

    /**
     * Get a rate according to its id
     * @param id id of the rate
     * @return the rate giving its id
     * @throws Exception for the DB
     */
    public RatingCompany getRateCompany(int id) throws Exception;

    /**
     * Delete a rate whit its id
     * @param id id of the rate
     * @throws Exception for the DB
     */
    public void deleteRateCompany(int id) throws Exception;

    /**
     * Update a rate
     * @param ratingCompany the news state of the rating company
     * @throws Exception for the DB
     */
    public void updateRateCompany(RatingCompany ratingCompany) throws Exception;

    /**
     * Create a rate
     * @param ratingCompany the state of the rating company
     * @return the id create by the database
     * @throws Exception for the DB
     */
    public int createRateCompany(RatingCompany ratingCompany) throws Exception;
}
