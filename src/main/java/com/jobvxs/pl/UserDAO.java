package com.jobvxs.pl;

import com.jobvxs.bl.User;
/**
 * Allows to maintain the User with the type of save
 */
public interface UserDAO {

    /**
     * Get the User object in the type of save
     * @param email
     * @return
     * @throws Exception
     */
    public User getUserById(String email) throws Exception;

}
