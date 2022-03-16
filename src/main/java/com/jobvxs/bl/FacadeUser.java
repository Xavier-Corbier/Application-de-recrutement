package com.jobvxs.bl;

import com.jobvxs.pl.AbstractFactoryDAO;
import com.jobvxs.pl.CompanyDAO;
import com.jobvxs.pl.JobSeekerDAO;
import com.jobvxs.pl.UserDAO;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * FacadeUser implements the FacadeUserInterface
 * It uses the Singleton Pattern
 */

public class FacadeUser {


    private static FacadeUser facadeUser = null;
    private User user = null; // state of the user
    private AbstractFactoryDAO factory;

    /**
     * Private Constructor for singleton pattern
     */
    private FacadeUser() throws SQLException {
        this.factory =  new FactoryDAOPostGre();
    }

    /**
     * SINGLETON
     * @return the one instance of FacadeUser
     */
    public static FacadeUser getFacadeUser() throws SQLException {
        if(facadeUser == null){
            facadeUser = new FacadeUser();
        }
        return facadeUser;
    }

    /**
     * Function login permit the user to log in the system
     * @param email email of the user
     * @param password password of the user
     * @throws Exception exception of the DB
     */
    public void login(String email, String password) throws Exception {
        UserDAO userDAO = factory.getUserDAO();
        this.user = userDAO.getUserById(email);
        if(!password.equals(user.getPassword())){
            throw new Exception("Error : Password ");
        }
        this.user.deletePassword();
    }

    /**
     * Verify the password of a user
     * @param email email of the user
     * @param password password of the user
     * @return true if this is the same password
     * @throws Exception exception of the DB
     */
    public boolean verifPassword(String email, String password) throws Exception {
        this.user = factory.getUserDAO().getUserById(email);
        boolean isCorrect = false;
        if(password.equals(user.getPassword())){
            isCorrect = true;
        }
        this.user.deletePassword();
        return isCorrect;
    }

    /**
     * Return the role information associated with the user logged in the application
     * @return the role of the user
     * @throws Exception exception of the DB
     */
    public UserRole getRole() throws Exception {
        switch (user.getRole()){
            case COMPANY :
                CompanyDAO companyDAO = factory.getCompanyDAO();
                return companyDAO.getUserById(user);
            case JOBSEEKER:
                JobSeekerDAO jobSeekerDAO = factory.getJobSeekerDAO();
                return jobSeekerDAO.getUserById(user);
            case ADMINISTRATOR:
                return new Administrator(user);
            default:
                throw new Exception("No role");
        }

    }

    /**
     * Send a notification to a user
     * @param email
     * @param title
     * @param desc
     * @param type
     */
    public void sendNotification(String email, String title, String desc, Notification.EnumNotif type){
        try {
            factory.getNotificationDAO().createNotification(email, title, desc, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all the notification of a user
     * @return
     */
    public ArrayList<Notification> getAllNotification(){
        ArrayList<Notification> listNotif = new ArrayList<>();
        try {
            String email = FacadeUser.getFacadeUser().getUser().getEmail();
            listNotif = factory.getNotificationDAO().getAllNotifications(email);
        } catch (Exception e) {
            //there is no notif
        }
        return listNotif;
    }

    /**
     * Delete a notification of a user by giving the id of the notification
     * @param idNotif
     */
    public void deleteNotification(int idNotif){
        try {
            String email = FacadeUser.getFacadeUser().getUser().getEmail();
            factory.getNotificationDAO().deleteNotification(email, idNotif);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set a notification as Read
     * @param idNotif
     */
    public void setReadNotification(int idNotif){
        try {
            factory.getNotificationDAO().setReadNotification(idNotif);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the current User of the facade
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * Logout the user
     */
    public void logout(){
        this.user=null;
    }
    
}
