package com.jobvxs.bl;

import com.jobvxs.bl.tools.GenerateCode;
import com.jobvxs.bl.tools.SendMail;
import com.jobvxs.pl.AbstractFactoryDAO;
import com.jobvxs.pl.CompanyDAO;
import com.jobvxs.pl.JobOfferDAO;
import com.jobvxs.pl.UserDAO;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class to give different method to manage the company side of the application
 */
public class FacadeCompany {
    private static FacadeCompany facadeCompany = null;
    private Company company = null; // state of the company
    private JobOffer jobOffer = null; // state of the company
    private AbstractFactoryDAO factory;
    private String code;
    private ArrayList<Application> listApp = new ArrayList<>();
    /**
     * Private Constructor for singleton pattern
     */
    private FacadeCompany() throws SQLException {
        this.factory =  new FactoryDAOPostGre();
    }

    /**
     * SINGLETON
     * @return the one instance of FacadeCompany
     */
    public static FacadeCompany getFacadeCompany() throws SQLException {
        if(facadeCompany == null){
            facadeCompany = new FacadeCompany();
        }
        return facadeCompany;
    }


    /**
     * Getter for the company
     * @return Company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Getter for the list of app of the company
     * @return the list of the applications of the job offer's company
     */
    public ArrayList<Application> getListApp() {
        return listApp;
    }

    /**
     * Set the company int the facade
     * @param company
     */
    public void setCompany(Company company){
        this.company = company;

        // recup all its application;
        try {
            this.listApp = this.getAllApplicationsByIdCompany();
        } catch (Exception exception) {
            // there is no app
            System.out.println(exception.getMessage());
            System.out.println("there is no app");
        }
    }

    /**
     * Check with the email. We choose to check if the user has already an account.
     * @param email
     * @return boolean True if it is correct
     */
    public boolean preRegister(String email){
        User user = null;
        try {
            UserDAO userDAO = factory.getUserDAO();
            user = userDAO.getUserById(email);

        } catch (Exception e) {
            System.out.println("No user");
        }
        return user==null;
    }

    /**
     * Send check code to the email of the user
     * @param email
     * @return String code to check account
     */
    public String sendCode(String email) throws MessagingException, UnsupportedEncodingException {
        code= GenerateCode.generate();
        SendMail.sendCode(code, email);
        return code;
    }

    /**
     * Register the Company in the DAO
     * @param name
     * @param address
     * @param nbEmployee
     * @param email
     * @param phoneNumber
     * @param description
     * @param password
     */
    public void register(String name, String address, int nbEmployee, String email, int phoneNumber, String description, String password) throws Exception {
        Company company = new Company(
                new User(email,
                        password,
                        "Company"),
                name,
                address,
                nbEmployee,
                description,
                phoneNumber
        );
        CompanyDAO companyDAO = factory.getCompanyDAO();
        companyDAO.save(company);
        this.company = company;
        FacadeUser facadeUser = FacadeUser.getFacadeUser();
        User user = company.getUser();
        facadeUser.login(user.getEmail(),user.getPassword());
        company.getUser().deletePassword();
    }


    /**
     * Delete the company int the DAO
     * @param email
     * @throws Exception
     */
    public void delete (String email) throws Exception {
        if(this.factory.getUserDAO().getUserById(email).getRole() == User.UserType.COMPANY){
            this.factory.getCompanyDAO().delete(email);
        }else{
            throw new Exception("The user is not a company");
        }
    }

    /**
     * Logout the company
     */
    public void  logout() {
        this.company=null;
        this.listApp = new ArrayList<>();
    }

    /**
     * Update the company in the DAO
     * @param name
     * @param address
     * @param description
     * @param phoneNumber
     * @param numberOfEmployee
     * @param email
     * @param password
     * @throws Exception
     */
    public void update(String name, String address, String description,int phoneNumber, int numberOfEmployee, String email, String password) throws Exception {
        if(FacadeUser.getFacadeUser().verifPassword(email,password)){
            Company newCompany = new Company(this.company.getUser(),name,address,numberOfEmployee,description,phoneNumber);
            Map<String,String> modif = company.searchDifference(newCompany);
            this.factory.getCompanyDAO().update(modif);
            this.company = newCompany; // change the state of the company
        } else {
            throw new Exception("This is the wrong password");
        }
    }

    /**
     * Get the list of all companies
     * @return List of Companies
     * @throws Exception
     */
    public ArrayList<Company> getList() throws Exception {
        return this.factory.getCompanyDAO().getListOfCompanies();
    }


    /**
     * Create a job offer
     * @param name
     * @param type
     * @param salary
     * @param description
     * @param requirements
     * @param advantages
     * @param workSchedule
     * @throws Exception
     */
    public void createJobOffer(String name,
                               String type,
                               double salary,
                               String description,
                               String requirements,
                               String advantages,
                               String workSchedule) throws Exception {
        User user = FacadeUser.getFacadeUser().getUser();
        JobOffer  jobOffer = new JobOffer(0, name, type, salary,description, requirements, advantages, workSchedule,user.getEmail());
        JobOfferDAO jobOfferDAO = factory.getJobOfferDAO();
        Company company = FacadeCompany.getFacadeCompany().getCompany();
        jobOfferDAO.createJobOffer(jobOffer,company);
    }

    /**
     * Get all job offers of the current company
     * @return ArrayList<JobOffer>
     * @throws Exception
     */
    public ArrayList<JobOffer> getOwnOffer() throws Exception {
        User user = FacadeUser.getFacadeUser().getUser();
        JobOfferDAO jobOfferDAO = factory.getJobOfferDAO();
        return jobOfferDAO.getAllJobOfferByEmail(user.getEmail());
    }

    /**
     * Delete job offer in the database with its id
     * @param id
     * @throws Exception
     */
    public void deleteJobOffer(int id) throws Exception {
        JobOfferDAO jobOfferDAO = factory.getJobOfferDAO();
        jobOfferDAO.deleteJobOffer(id);
    }

    /**
     * Get all job offers
     * @return ArrayList<JobOffer>
     * @throws Exception
     */
    public ArrayList<JobOffer> getAllJobOffer() throws Exception {
        User user = FacadeUser.getFacadeUser().getUser();
        JobOfferDAO jobOfferDAO = factory.getJobOfferDAO();
        return jobOfferDAO.getAllJobOffer();
    }

    /**
     * Get the current job offer in the facade
     * @return
     */
    public JobOffer getJobOffer() {
        return jobOffer;
    }

    /**
     * Set the current job offer in the facade
     * @param jobOffer
     */
    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }

    public void updateJobOffer(int id,
                               String name,
                               String type,
                               double salary,
                               String description,
                               String requirements,
                               String advantages,
                               String workSchedule,
                               String email) throws Exception {
        JobOffer newJobOffer = new JobOffer(id, name, type, salary, description, requirements, advantages, workSchedule, email);
        Map<String, String> modif = jobOffer.searchDifference(newJobOffer);
        this.factory.getJobOfferDAO().updateJobOffer(modif);
        this.jobOffer = newJobOffer; // change the state of the job offer
    }

    // ------------- Rating Company ----------------- //

    /**
     * Get the list of all rates of the company
     * @param email email of the company
     * @return the list of all rates of the company
     * @throws Exception error with the DB
     */
    public List<RatingCompany> getRatesCompany(String email) throws Exception {
        return this.factory.getRateCompanyDAO().getRatesByCompany(email);
    }

    /**
     * Update a rate
     * @param rate new rate of the company
     * @throws Exception error with the DB
     */
    public void updateRate(RatingCompany rate) throws Exception {
        this.factory.getRateCompanyDAO().updateRateCompany(rate);
    }

    /**
     * Create a rate
     * @param emailJobSeeker email of the job seeker that create the rate
     * @param emailCompany email of the company that be rated
     * @param nbStars rate between 1 and 5
     * @param comment comment associated with the rate
     * @return the new rating created by the database with its id
     * @throws Exception  error with the DB
     */
    public RatingCompany createRate(String emailJobSeeker, String emailCompany, int nbStars, String comment) throws Exception {
        RatingCompany ratingCompany = new RatingCompany(
                0,
                nbStars,
                comment,
                emailJobSeeker,
                emailCompany,
                "jack :)"
        );
        ratingCompany.setIdRate(this.factory.getRateCompanyDAO().createRateCompany(ratingCompany));
        return ratingCompany;
    }

    /**
     * Delete a rate
     * @param ratingCompany the rate to delete
     * @throws Exception  error with the DB
     */
    public void deleteRate(RatingCompany ratingCompany) throws Exception {
        boolean isDeleteMyRate = FacadeJobSeeker.getFacadeJobSeeker().getJobseeker() != null && FacadeJobSeeker.getFacadeJobSeeker().getJobseeker().getEmail().equals(ratingCompany.getEmailJobSeeker());
        boolean isAdmin = FacadeUser.getFacadeUser().getUser().getRole() == User.UserType.ADMINISTRATOR;

        if(isAdmin || isDeleteMyRate){ // we can delete
            this.factory.getRateCompanyDAO().deleteRateCompany(ratingCompany.getIdRate());
        }
    }

    /**
     * get a rate from its id
     * @param id id of the rate
     * @return the rate according to the id
     * @throws Exception  error with the DB
     */
    public RatingCompany getRate(int id) throws Exception {
        return this.factory.getRateCompanyDAO().getRateCompany(id);

    }

    // ------- Application  -------- //

    /**
     * Get the list of the application of the Company
     * @return List of all application
     * @throws Exception problem with the database
     */
    public ArrayList<Application> getAllApplicationsByIdCompany() throws Exception {
        String email = this.company.getEmail();
        if(email != null && !email.equals("")){
            return this.factory.getApplicationDAO().getApplicationByCompany(email);
        }
        else{
            throw new Exception("There is a problem with the email");
        }
    }

    /**
     * Accept an application by changing it state into "Accepted"
     * @param idApplication id of the application
     * @throws Exception problem with the database
     */
    public void acceptApplication(int idApplication) throws Exception{
        if(FacadeUser.getFacadeUser().getUser().getRole() == User.UserType.COMPANY) {
            this.factory.getApplicationDAO().updateApplication(idApplication, "Accepted");
            this.listApp.forEach(app -> {
                if(app.getIdApp() == idApplication){
                    app.setState(Application.StateApplication.ACCEPTED);
                }
            });

            //Send notification to the JobSeeker
            String desc = "Your application to " + facadeCompany.getJobOffer().getName() + "was accepted.";
            String emailJobSeeker = this.factory.getApplicationDAO().getEmailJobSeeker(idApplication);
            FacadeUser.getFacadeUser().sendNotification(emailJobSeeker
                    ,"Application accepted" , desc, Notification.EnumNotif.JOB);
        }
        else{
            throw new Exception("Your are not a company");
        }
    }

    /**
     * Reject an application by changing it state into "Rejected"
     * @param idApplication id of the application
     * @throws Exception problem with the database
     */
    public void rejectApplication(int idApplication) throws Exception{
        if(FacadeUser.getFacadeUser().getUser().getRole() == User.UserType.COMPANY){
            this.factory.getApplicationDAO().updateApplication(idApplication, "Rejected");
            this.listApp.forEach(app -> {
                if(app.getIdApp() == idApplication){
                    app.setState(Application.StateApplication.REJECTED);
                }
            });

            //Send notification to the JobSeeker
            String desc = "Your application to " + facadeCompany.getJobOffer().getName() + "was rejected.";
            String emailJobSeeker = this.factory.getApplicationDAO().getEmailJobSeeker(idApplication);
            FacadeUser.getFacadeUser().sendNotification(emailJobSeeker
                    ,"Application rejected" , desc, Notification.EnumNotif.JOB);
        }
        else{
            throw new Exception("Your are not a company");
        }

    }

    /**
     * Get the list of all job categories
     * @return
     * @throws Exception
     */
    public ArrayList<JobCategory> getAllJobCategorie() throws Exception {
        FacadeAdministrator facadeAdministrator = FacadeAdministrator.getFacadeAdministrator();
        return facadeAdministrator.getAllJobCategories();
    }

    /**
     * Create a comment for a job offer
     * @param comment
     * @param idJobOffer
     * @return
     */
    public CommentJobOffer createCommentJobOffer(String comment, int idJobOffer){
        int idComment = 0;
        try {
            idComment = this.factory.getCommentJobOfferDAO().createCommentJobOffer(idJobOffer, comment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CommentJobOffer commentJobOffer = new CommentJobOffer(idComment, comment);

        return commentJobOffer;
    }

    /**
     * Update a comment on a job offer
     * @param comment
     * @param idComment
     * @return
     */
    public CommentJobOffer updateCommentJobOffer(String comment, int idComment){
        CommentJobOffer commentJobOffer = new CommentJobOffer(idComment, comment);
        try {
            this.factory.getCommentJobOfferDAO().updateCommentJobOffer(idComment, comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commentJobOffer;
    }

    /**
     * Method to obtain all comments on a specific job offer
     * @param idJobOffer
     * @return
     */
    public ArrayList<CommentJobOffer> getAllCommentJobOffer(int idJobOffer){
        ArrayList<CommentJobOffer> arrayList = new ArrayList<CommentJobOffer>();
        try {
            arrayList = this.factory.getCommentJobOfferDAO().getAllCommentJobOffer(idJobOffer);
        } catch (Exception e) {
            // there is not comment
        }
        return arrayList;
    }

    /**
     * Delete a comment on a jobOffer
     * @param idComment
     */
    public void deleteCommentJobOffer(int idComment){
        try {
            this.factory.getCommentJobOfferDAO().deleteCommentJobOffer(idComment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getOriginatorCommentJobOffer(int idComment){
        String email = "";
        try{
            email = this.factory.getCommentJobOfferDAO().getEmailJobSeeker(idComment);
        } catch (Exception e){
            e.printStackTrace();
        }
        return email;
    }

}
