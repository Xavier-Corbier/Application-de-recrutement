package com.jobvxs.pl.postgre;

import com.jobvxs.bl.CommentJobOffer;
import com.jobvxs.bl.Company;
import com.jobvxs.bl.RatingCompany;
import com.jobvxs.pl.*;
import com.jobvxs.pl.connect.PostGreSqlConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implement AbstractFactory for PostGreSQL
 */
public class FactoryDAOPostGre implements AbstractFactoryDAO {
    private static FactoryDAOPostGre factory = null;
    private UserDAO userDAO;
    private CompanyDAO companyDAO;
    private JobSeekerDAO jobSeekerDAO;
    private CvDAO cvDAO;
    private JobOfferDAO jobOfferDAO;
    private RateCompanyDAO rateCompanyDAO;
    private ApplicationDAO applicationDAO;
    private NotificationDAO notificationDAO;
    private CommentJobOfferDAO commentJobOfferDAO;
    private JobCategoryDAO jobCategoryDAO;
    private Connection conn = PostGreSqlConnection.getInstance();

    /**
     * Constructor of the FactoryDAOPostGre class
     * @throws SQLException
     */
    public FactoryDAOPostGre() throws SQLException {
    }

    /**
     * getter for the Factory
     * @return
     * @throws SQLException
     */
    @Override
    public AbstractFactoryDAO getFactory() throws SQLException {
        if (factory == null) {
            factory = new FactoryDAOPostGre();
        }
        return factory;
    }

    /**
     * Get the UserDAO in PostGreSQL.
     * It is possible to have an Error if we are not connect to the database.
     *
     * @return UserDAO
     * @throws Exception error the the DB
     */
    @Override
    public UserDAO getUserDAO() throws Exception {
        try {
            if (userDAO == null) {
                userDAO = new UserDAOPostGre(conn);
            }
            return userDAO;
        } catch (SQLException e) {
            throw new Exception("Error: Connection database");
        }
    }

    /**
     * Get CompanyDAO in PostGreSQL
     * It is possible to have an Error if we are not connect to the database.
     * @return CompanyDAO
     * @throws Exception error the the DB
     */
    @Override
    public CompanyDAO getCompanyDAO() throws Exception {
        try {
            if (companyDAO == null) {
                companyDAO = new CompanyDAOPostGre(conn);
            }
            return companyDAO;
        } catch (SQLException e) {
            throw new Exception("Error: Connection database");
        }
    }

    /**
     * Get JobSeeker in PostGreSQL
     * It is possible to have an Error if we are not connect to the database.
     * @return JobSeekerDAO
     * @throws Exception error the the DB
     */
    @Override
    public JobSeekerDAO getJobSeekerDAO() throws Exception {
        try {
            if (jobSeekerDAO == null) {
                jobSeekerDAO = new JobSeekerDAOPostGre(conn);
            }
            return jobSeekerDAO;
        } catch (SQLException throwables) {
            throw new Exception("Error: Connection database");
        }

    }

    /**
     * @return CvDAO
     * @throws Exception error the the DB
     */
    @Override
    public CvDAO getCvDAO() throws Exception {
        try {
            if (cvDAO == null) {
                cvDAO = new CvDAOPostGre(conn);
            }
            return cvDAO;
        } catch (SQLException throwables) {
            throw new Exception("Error: Connection database");
        }
    }

    /**
     * Get the JobOfferDAO
     * @return JobOfferDAO
     * @throws Exception error the the DB
     */
    @Override
    public JobOfferDAO getJobOfferDAO() throws Exception {
        try{
            if(jobOfferDAO==null) {
                jobOfferDAO = new JobOfferDAOPostGre(conn);
            }
            return jobOfferDAO;
        } catch (SQLException throwables) {
            throw new Exception("Error: Connection database");
        }
    }
  
    /**
     * Get the RateCompanyDAO
     * @return RateCompanyDAO
     * @throws Exception error the the DB
     */
    public RateCompanyDAO getRateCompanyDAO() throws Exception {
        try {
            if (rateCompanyDAO == null) {
                rateCompanyDAO = new RateCompanyDAOPostGre(conn);
            }
            return rateCompanyDAO;
        } catch (SQLException throwables) {
            throw new Exception("Error: Connection database");
        }
    }

    /**
     * Get the ApplicationDAO
     * @return ApplicaitonDAO
     * @throws Exception error the the DB
     */
    public ApplicationDAO getApplicationDAO() throws Exception {
        try {
            if (applicationDAO == null) {
                applicationDAO = new ApplicationDAOPostGre(conn);
            }
            return applicationDAO;
        } catch (SQLException throwables) {
                     throw new Exception("Error: Connection database");
        }
    }

    /**
     * Get the NotificationDAO
     * @return
     * @throws Exception
     */
    public NotificationDAO getNotificationDAO() throws Exception{
        try{
            if(notificationDAO == null){
                notificationDAO = new NotificationDAOPostGre(conn);
            }
            return notificationDAO;
        } catch (SQLException throwables){
            throw new Exception("Error: Connection database");
        }
    }

    /**
     * Get the CommentJobOfferDAO
     * @return
     * @throws Exception
     */
    public CommentJobOfferDAO getCommentJobOfferDAO() throws Exception{
        try{
            if(commentJobOfferDAO == null){
                commentJobOfferDAO = new CommentJobOfferDAOPostGre(conn);
            }
            return commentJobOfferDAO;
        } catch (SQLException throwables){
            throw new Exception("Error : Connection database");
        }
    }

    /**
     * Get the JobCategoryDAO
     * @return
     * @throws Exception
     */
    @Override
    public JobCategoryDAO getJobCategoryDAO() throws Exception {
        try {
            if (jobCategoryDAO == null) {
                jobCategoryDAO = new JobCategoryDAOPostGre(conn);
            }
            return jobCategoryDAO;
        } catch (SQLException throwables) {
            throw new Exception("Error: Connection database");
        }
    }
}
