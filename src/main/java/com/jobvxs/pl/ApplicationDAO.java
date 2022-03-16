package com.jobvxs.pl;

import com.jobvxs.bl.Application;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Allows to maintain the informations of a job offer
 */
public interface ApplicationDAO {

    /**
     * Get the list of application by the email of the company
     * @param emailCompany email of the company
     * @return the list of all application of the job offer's company
     * @throws SQLException error in the DB
     */
    public ArrayList<Application> getApplicationByCompany(String emailCompany) throws SQLException;

    /**
     * Get the list of application by the email of the jobSeeker
     * @param emailJobSeeker email of the job seeker
     * @return the list of all application of the job seeker
     * @throws SQLException error in the DB
     */
    public ArrayList<Application> getApplicationByJobSeeker(String emailJobSeeker) throws SQLException;

    /**
     * Delete an application with its id
     * @param idApplication id of the application
     * @throws SQLException error in the DB
     */
    public void deleteApplication(int idApplication) throws SQLException;

    /**
     * Create an application
     * @param emailJobSeeker email of the job seeker
     * @param idJobOffer id of the job offer
     * @param coverLetter content of the cover letter
     * @return the id created by the DB for the application
     * @throws Exception error in the DB
     */
    public int createApplication(String emailJobSeeker, int idJobOffer,String coverLetter) throws Exception;

    /**
     * Update the state of the application
     * @param idApplication id of the application
     * @param state the new state of the application
     * @throws Exception error in the DB
     */
    public void updateApplication(int idApplication, String state) throws Exception;

    /**
     * Return the email of the jobSeeker concern by the application
     * @param idApplication
     * @return the email of the jobSeeker
     * @throws Exception
     */
    public String getEmailJobSeeker(int idApplication) throws Exception;

}
