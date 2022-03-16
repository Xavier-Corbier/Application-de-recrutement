package com.jobvxs.pl;

import com.jobvxs.bl.Company;
import com.jobvxs.bl.JobOffer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Allows to maintain the informations of a job offer
 */
public interface JobOfferDAO {
    /**
     * Get a job offer by its id
     * @param idJobOffer
     * @return
     */
    public JobOffer getJobOfferById(int idJobOffer);

    /**
     * Delete a job offer with its id
     * @param idJobOffer
     */
    public void deleteJobOffer(int idJobOffer) throws SQLException;

    /**
     * Create a job offer
     * @param jobOffer
     */
    public void createJobOffer(JobOffer jobOffer, Company company) throws SQLException;

    /**
     * Update a job offer
     * @param jobOfferModif
     */
    public void updateJobOffer(Map<String, String> jobOfferModif) throws Exception;

    /**
     * Get all job offers
     * @return
     */
    public ArrayList<JobOffer> getAllJobOffer() throws Exception;

    /**
     * Get all job offers by email of the company
     * @return
     */
    public ArrayList<JobOffer> getAllJobOfferByEmail(String email) throws Exception;
}
