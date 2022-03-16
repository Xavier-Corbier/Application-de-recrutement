package com.jobvxs.bl;

import com.jobvxs.pl.AbstractFactoryDAO;
import com.jobvxs.pl.JobCategoryDAO;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class to give different method to manage the administrator side of the application
 */
public class FacadeAdministrator {
    private static FacadeAdministrator facadeAdministrator = null;
    private AbstractFactoryDAO factory;

    /**
     * Private Constructor for singleton pattern
     */
    private FacadeAdministrator() throws SQLException {
        this.factory =  new FactoryDAOPostGre();
    }

    /**
     * SINGLETON
     * @return the one instance of FacadeCompany
     */
    public static FacadeAdministrator getFacadeAdministrator() throws SQLException {
        if(facadeAdministrator == null){
            facadeAdministrator = new FacadeAdministrator();
        }
        return facadeAdministrator;
    }

    /**
     * Create job category in the DAO
     * @param name
     * @throws Exception
     */
    public void createJobCategory(String name) throws Exception {
        JobCategoryDAO jobCategoryDAO = factory.getJobCategoryDAO();
        JobCategory jobCategory = new JobCategory(name);
        jobCategoryDAO.createJobCategory(jobCategory);
    }

    /**
     * Delete job category in the DAO
     * @param name
     * @throws Exception
     */
    public void deleteJobCategory(String name) throws Exception {
        JobCategoryDAO jobCategoryDAO = factory.getJobCategoryDAO();
        jobCategoryDAO.deleteJobCategory(name);
    }

    /**
     * Update job category in the DAO
     * @param oldName
     * @param newName
     * @throws Exception
     */
    public void updateJobCategory(String oldName, String newName) throws Exception {
        JobCategoryDAO jobCategoryDAO = factory.getJobCategoryDAO();
        JobCategory oldJobCategory = new JobCategory(oldName);
        JobCategory newJobCategory = new JobCategory(newName);
        jobCategoryDAO.updateJobCategory(oldJobCategory,newJobCategory);
    }

    /**
     * Get all job categories in the DAO
     * @return
     * @throws Exception
     */
    public ArrayList<JobCategory> getAllJobCategories() throws Exception {
        JobCategoryDAO jobCategoryDAO = factory.getJobCategoryDAO();
        return jobCategoryDAO.getAllJobCategory();
    }
}
