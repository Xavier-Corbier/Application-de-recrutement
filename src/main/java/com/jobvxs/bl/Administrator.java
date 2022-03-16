package com.jobvxs.bl;

/**
 * Administrator class is a subclass of the UserRole abstract class
 */

public class Administrator extends UserRole{

    /**
     * Constructor of the Administrator class
     * @param user reference to the user
     */
    public Administrator(User user) {
        super(user);
    }

    /**
     * Getter for the name of an Administrator
     * @return the name of the administrator
     */
    public String getName() {
        return "Administrator";
    }
}
