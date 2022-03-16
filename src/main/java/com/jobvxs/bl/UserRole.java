package com.jobvxs.bl;

/**
 *
 *  The UserRole can be a company, an Administrator or a jobSeeker
 *
 */
public abstract class UserRole {

    private User user; // user of the role

    /**
     * Constructor of the UserRole
     * @param user
     */
    public UserRole(User user) {
        this.user = user;
    }

    /**
     * Getter for the user
     * @return
     */
    public User getUser() {
        return user;
    }
}
