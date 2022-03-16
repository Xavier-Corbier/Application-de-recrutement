package com.jobvxs.pl;

import com.jobvxs.bl.JobCategory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Allows to maintain the information of the JobCategory
 */
public interface JobCategoryDAO {
    /**
     * Create job category in the DAO
     * @param jobCategory
     */
    public void createJobCategory(JobCategory jobCategory) throws SQLException;

    /**
     * Delete the job category by its name
     * @param name
     */
    public void deleteJobCategory(String name) throws SQLException;

    /**
     * Update the job category in the DAO
     * @param oldJobCategory
     * @param newJobCategory
     * @throws SQLException
     */
    public void updateJobCategory(JobCategory oldJobCategory, JobCategory newJobCategory) throws SQLException;

    /**
     * Get all job categories in the DAO.
     * @return
     */
    public ArrayList<JobCategory> getAllJobCategory() throws Exception;
}
