package com.jobvxs.pl.postgre;

import com.jobvxs.bl.JobCategory;
import com.jobvxs.bl.JobOffer;
import com.jobvxs.bl.User;
import com.jobvxs.pl.JobCategoryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Implements JobCategoryDAO in PostGreSQL
 */
public class JobCategoryDAOPostGre implements JobCategoryDAO {
    Connection conn ;

    /**
     * Construct JobCategoryDAO in PostGreSQL.
     * It is possible to handle an error if the Database is not connected
     * @throws SQLException
     */
    public JobCategoryDAOPostGre(Connection conn) throws SQLException {
        this.conn = conn;
    }

    /**
     * Create a job category
     * @param jobCategory
     * @throws SQLException
     */
    @Override
    public void createJobCategory(JobCategory jobCategory) throws SQLException {
        String requete = "INSERT INTO public.\"JobCategory\" VALUES (?);";
        PreparedStatement pstmt = conn.prepareStatement(requete);
        pstmt.setString(1,jobCategory.getName());
        // execute the request
        pstmt.executeUpdate();
    }

    /**
     * Delete job category with its name
     * @param name
     * @throws SQLException
     */
    @Override
    public void deleteJobCategory(String name) throws SQLException {
        // execute the request
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM public.\"JobCategory\" WHERE name = ?");
        pstmt.setString(1,name);
        pstmt.executeUpdate();
    }

    /**
     * Update a job category
     * @param oldJobCategory
     * @param newJobCategory
     * @throws SQLException
     */
    @Override
    public void updateJobCategory(JobCategory oldJobCategory, JobCategory newJobCategory) throws SQLException {
        this.deleteJobCategory(oldJobCategory.getName());
        this.createJobCategory(newJobCategory);
    }

    /**
     * Get all job categories
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<JobCategory> getAllJobCategory() throws Exception {
        ArrayList<JobCategory> jobCategories = new ArrayList<>();
        Statement stmt = conn.createStatement();
        // execute the request
        ResultSet res = stmt.executeQuery("SELECT * FROM public.\"JobCategory\" WHERE name != 'not type defined';");
        // if it is empty
        if(!res.next()){
            throw new Exception("Error: no job category Found");
        } else {
            do {
                jobCategories.add(new JobCategory(res.getString("name")));
            } while(res.next());
            // while we have a row
        }
        return jobCategories;
    }
}
