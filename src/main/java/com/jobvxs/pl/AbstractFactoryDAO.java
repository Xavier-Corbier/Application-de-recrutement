package com.jobvxs.pl;

import com.jobvxs.bl.CommentJobOffer;
import com.jobvxs.pl.postgre.CvDAOPostGre;

import java.sql.SQLException;

/**
 *  Allow to get all type in DAO
 */
public interface AbstractFactoryDAO {

    public AbstractFactoryDAO getFactory() throws SQLException;
    /**
     * Get the UserDAO
     * @return UserDAO
     * @throws Exception error the the DB
     */
    public UserDAO getUserDAO() throws Exception;

    /**
     * Get the CompanyDAO
     * @return CompanyDAO
     * @throws Exception error the the DB
     */
    public CompanyDAO getCompanyDAO() throws Exception;

    /**
     * Get the JobSeekerDAO
     * @return the JobSeekerDAO
     * @throws Exception error the the DB
     */
    public JobSeekerDAO getJobSeekerDAO() throws Exception;

    /**
     * Get the CvDAO
     * @return CvDAO
     * @throws Exception error the the DB
     */
    public CvDAO getCvDAO() throws Exception;

    /**
     * Get the JobOfferDAO
     * @return JobOfferDAO
     * @throws Exception error the the DB
     */
    public JobOfferDAO getJobOfferDAO() throws Exception;

    /**
     * Get the RateCompanyDAO
     * @return the RateCompanyDAO
     * @throws Exception error the the DB
     */
    public RateCompanyDAO getRateCompanyDAO() throws Exception;

    /**
     * Get the ApplicaitonDAO
     * @return the application DAO
     * @throws Exception error the the DB
     */
    public ApplicationDAO getApplicationDAO() throws Exception;

    /**
     * Get the JobCategoryDAO
     * @return the jobCategoryDAO
     * @throws Exception
     */
    public JobCategoryDAO getJobCategoryDAO() throws Exception;

    /**
     * Get the NotificationDAO
     * @return the notification DAO
     * @throws Exception
     */
    public NotificationDAO getNotificationDAO() throws Exception;

    /**
     * Get the CommentJobOfferDAO
     * @return the commentJobOfferDAO
     * @throws Exception
     */
    public CommentJobOfferDAO getCommentJobOfferDAO() throws Exception;
}
