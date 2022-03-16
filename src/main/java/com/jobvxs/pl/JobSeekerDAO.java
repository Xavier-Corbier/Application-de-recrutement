package com.jobvxs.pl;

import com.jobvxs.bl.JobSeeker;
import com.jobvxs.bl.User;

import java.util.ArrayList;
import java.util.Map;

/**
 * Allows to maintain the JobSeeker with the type of save
 */
public interface JobSeekerDAO {

    /**
     * Get by user email the Job Seeker associated object save in the database
     * @param user user information of the application
     * @return the job seeker corresponding to the user
     * @throws Exception for the DB
     */
    public JobSeeker getUserById(User user) throws Exception;

    /**
     * Get the list of all jobSeeker
     * @return the list of all the job seeker
     * @throws Exception for the DB
     */
    public ArrayList<JobSeeker> getListOfJobSeeker() throws Exception;

    /**
     * Update a jobseeker in the database
     * @param jobSeekerModif Map that contains all the modification of the job seeker
     * @throws Exception for the DB
     */
    public void update(Map<String, String> jobSeekerModif) throws Exception;

    /**
     * Delete a jobSeeker from the database
     * @param email email of the job seeker
     * @throws Exception for the DB
     */
    public void delete(String email) throws Exception;

    /**
     * Register a jobSeeker in the database
     * @param jobseeker State of the new Job Seeker
     * @throws Exception for the DB
     */
    public void save(JobSeeker jobseeker) throws Exception;
}
