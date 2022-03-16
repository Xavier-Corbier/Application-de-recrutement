package com.jobvxs.bl;

import java.util.Enumeration;

/**
 * Class User for global information about User
 */

public class User {

    /**
     * Enum for the possible type of User (JobSeeker, Company, Administrator)
     */
    public enum UserType {  //

        JOBSEEKER, COMPANY, ADMINISTRATOR
    }
    private String email;
    private String password;
    private UserType role;


    /**
     * Constructor of the User class
     * @param email
     * @param password
     * @param type
     */
    public User(String email, String password, String type) {
        this.email = email;
        this.password = password;
        switch (type) {
            case "Job Seeker" -> this.role = UserType.JOBSEEKER;
            case "Company" -> this.role = UserType.COMPANY;
            case "Administrator" -> this.role = UserType.ADMINISTRATOR;
            default -> this.role = null;
        }
    }

    /**
     * Getter for the password of a User
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for the email of a User
     * @return
     */
    public String getEmail() { return email;}

    /**
     * Getter for the role of a User
     * @return
     */
    public UserType getRole() {return role;}

    /**
     * Delete password of the user, to hide the password for the other layer
     * @return
     */
    public void deletePassword(){
        password="";
    }
}
