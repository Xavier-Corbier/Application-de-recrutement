package com.jobvxs.pl;

import com.jobvxs.bl.CV;

import java.sql.SQLException;

public interface CvDAO {

    /**
     * Return the CV of the user associated with the email
     * @param email
     * @return the CV of the user concerned
     * @throws Exception
     */
    public CV getCVById(String email) throws Exception;

    /**
     * Update a CV by giving a new CV
     * @param cv
     * @return
     */
    public boolean updateCV(CV cv) throws SQLException;

    /**
     *  Create a CV by default
     * @return
     * @throws Exception
     */
    public CV createCV() throws Exception;
}
