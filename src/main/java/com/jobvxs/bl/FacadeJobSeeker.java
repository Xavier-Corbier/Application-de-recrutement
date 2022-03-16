package com.jobvxs.bl;

import com.jobvxs.bl.tools.GenerateCode;
import com.jobvxs.bl.tools.SendMail;
import com.jobvxs.pl.AbstractFactoryDAO;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;
import javafx.scene.control.DatePicker;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.*;
import java.util.Map;

public class FacadeJobSeeker {

    private static FacadeJobSeeker facadeJobSeeker = null;

    private JobSeeker jobseeker = null; // state of the jobseeker
    private ArrayList<Application> listApp = new ArrayList<>(); // application of the jobsseker
    private AbstractFactoryDAO factory;
    private String code;

    /**
     * Private Constructor for singleton pattern
     */
    private FacadeJobSeeker() throws SQLException {
        this.factory =  new FactoryDAOPostGre();

    }

    /**
     * SINGLETON
     * @return the one instance of FacadeJobSeeker
     */
    public static FacadeJobSeeker getFacadeJobSeeker() throws SQLException {
        if(facadeJobSeeker == null){
            facadeJobSeeker = new FacadeJobSeeker();
        }
        return facadeJobSeeker;
    }

    /** ------Getter and Setter----- **/

    /**
     * Setter for the jobseeker
     * @param jobSeeker new job seeker
     */
    public void setJobseeker(JobSeeker jobSeeker) {
        this.jobseeker = jobSeeker;
        // recup all its application;
        try {
            this.listApp = this.getAllApplicationByIdJobSeeker();
        } catch (Exception exception) {
            // there is no applications
        }
    }

    /**
     * getter for the jobseeker
     * @return the job seeker that connected in the facade
     */
    public JobSeeker getJobseeker() {
        return jobseeker;
    }

    /**
     * Getter for the list of the application
     * @return the list of the applications of the job seeker
     */
    public ArrayList<Application> getListApp(){return  listApp;}




    /** Function for the CV **/

    public CV getInfoCV() throws Exception {
        String email;
        if(this.jobseeker == null){
            FacadeUser facadeUser = FacadeUser.getFacadeUser();
            jobseeker = (JobSeeker) facadeUser.getRole();
        }
        email = jobseeker.getEmail();
        return getInfoCV(email);
    }

    /**
     * Function use for get the CV from the application of the jobSeeker
     * @param emailJobSeeker email of the jobSeeker
     * @return CV of the jobSeeker
     * @throws Exception error with the databse
     */
    public CV getInfoCV(String emailJobSeeker) throws Exception{
        CV cv = factory.getCvDAO().getCVById(emailJobSeeker);
        if(cv == null){
            factory.getCvDAO().createCV();
        }
        return cv;
    }

    /**
     * Update the CV by providing a new CV
     * @param cv
     * @return
     * @throws Exception
     */
    public boolean updateCV(CV cv) throws Exception {
        boolean res = false;
        String email = jobseeker.getEmail();
        CV cvActuel = factory.getCvDAO().getCVById(email);

        cvActuel.setEducation(cv.getEducation());
        cvActuel.setWorkExperience(cv.getWorkExperience());
        cvActuel.setSkillsAndInterests(cv.getSkillsAndInterests());
        cvActuel.setReferences(cv.getReferences());

        if(factory.getCvDAO().updateCV(cvActuel)){
            res = true;
        }
        return res;
    }

    /**
     * Create a CV by default
     * @return
     * @throws Exception
     */
    public CV createCV() throws Exception{
        CV newCV = factory.getCvDAO().createCV();
        return newCV;
    }

    /** Function for the JobSeeker **/

    /**
     * register the jobseeker
     * @param name name of the job seeker
     * @param address address of the job seeker
     * @param description description of the job seeker
     * @param birthday  birthday  of the job seeker
     * @param email email of the user
     * @param password password of the user
     * @throws Exception exception in the DB
     */
    public void register(String name, String address, String description, String phoneNumber, Date birthday, String email, String password) throws Exception {
        this.jobseeker = new JobSeeker(new User(email,password,"Job Seeker"),
                                        name,
                                        address,
                                        birthday,
                                        description,
                                        phoneNumber);
        this.factory.getJobSeekerDAO().save(jobseeker);
        User user = jobseeker.getUser();
        FacadeUser.getFacadeUser().login(user.getEmail(),user.getPassword()); // We connect the job seeker automatically
        this.jobseeker.getUser().deletePassword();
    }


    /**
     * update the state of a jobseeker
     * @param name name of the job seeker
     * @param address address of the job seeker
     * @param description description of the job seeker
     * @param phoneNumber phoneNumber of the job seeker
     * @param birthday birthday of the job seeker
     * @param email email of the user
     * @param password password of the user
     * @return error message or "" if there is no error
     * @throws Exception  exception in the DB
     */
    public String update(String name, String address, String description,String phoneNumber, Date birthday, String email, String password) throws Exception {
        if(FacadeUser.getFacadeUser().verifPassword(email,password)){
            JobSeeker newJobseeker = new JobSeeker(this.jobseeker.getUser(),name,address,birthday,description,phoneNumber);
            Map<String,String> modif = jobseeker.searchDifference(newJobseeker);
            if(modif.size()<2){
                return "There is not update to compute";
            }
            this.factory.getJobSeekerDAO().update(modif);
            this.jobseeker = newJobseeker; // change the state of the jobseeker
            return "";
        }
        return "This is the wrong password";

    }

    /**
     * Verify the attributes of a jobseeker
     * @param name name of the job seeker
     * @param address address of the job seeker
     * @param dateOfBirth dateOfBirth of the job seeker
     * @param phone phone number of the job seeker
     * @param email email of the user
     * @param password password of the user
     * @return error message or "" if there is no error
     */
    public String verifField(String name, String address, LocalDate dateOfBirth, String phone, String email, String password){
        if(name.length() < 2){
            return "The name field could not be empty";
        }
        if(address.length() <2){
            return  "The address field could not be empty";
        }
        System.out.println(dateOfBirth);
        if(dateOfBirth == null || dateOfBirth.equals(new DatePicker(LocalDate.now()).getValue())){
            return "The date of birth field could not be empty";
        }
        // the string is a number
        if(!phone.matches("^0\\d{9}$")){
            System.out.println(phone);
            return "The phone number field is in the wrong format";
        }
        if(email.isEmpty() || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,4}$")){
            return "The email field is in the wrong format";
        }
        if(password.length()<3){
            return "The password field could not be under the length of 3";
        }
        return "";
    }

    /**
     * Delete jobseeker
     * @param email email of the job seeker
     * @throws Exception error the DB
     */
    public void delete(String email) throws Exception{
        if(this.factory.getUserDAO().getUserById(email).getRole() == User.UserType.JOBSEEKER){
            this.factory.getJobSeekerDAO().delete(email);
        }else{
            throw new Exception("The user is not a jobSeeker");
        }
    }

    /**
     * Disconnect the jobseeker
     */
    public void signOut() {
        this.jobseeker = null;
        this.listApp = new ArrayList<>();
    }

    /**
     * Get the list of all jobSeeker
     * @return List of JobSeeker
     * @throws Exception error the DB
     */
    public ArrayList<JobSeeker> getList() throws Exception {
        return this.factory.getJobSeekerDAO().getListOfJobSeeker();
    }

    /**
     * Send a code to the email of a job seeker
     * @param email email of the job seeker
     * @throws MessagingException exception created by the email
     * @throws UnsupportedEncodingException exception created by the email
     */
    public void sendCode(String email) throws MessagingException, UnsupportedEncodingException {
        this.code = GenerateCode.generate();
        SendMail.sendCode(this.code,email);
    }

    /**
     * Verify is the code is the same that we send
     * @param code enter by the job seeker
     * @return true if code == code send
     */
    public boolean checkCode(String code){
        return this.code.equals(code);
    }

    /**
     * @param email email of the job seeker
     * @return true if a user already exist with this email
     */
    public boolean preRegister(String email){
        try{
            return this.factory.getUserDAO().getUserById(email) != null;
        }
        catch (Exception e){
            return false;
        }
    }

    // ------- Application  -------- //

    /**
     * Get the list of the application of the jobSeeker
     * @return the list of all applications of the jobSeeker
     * @throws Exception problem with the database
     */
    public ArrayList<Application> getAllApplicationByIdJobSeeker() throws Exception {
        String email = this.jobseeker.getEmail();
        if(email != null && !email.equals("")){
            return this.factory.getApplicationDAO().getApplicationByJobSeeker(email);
        }
        else{
            throw new Exception("There is a problem with the email");
        }
    }


    /**
     * Create a new application for a job
     * @param idJobOffer id of the job offer
     * @param nameJob name of the job offer
     * @param coverletter cover letter of the application
     * @throws Exception problem with the database
     */
    public void createApplication(int idJobOffer, String nameJob, String coverletter) throws Exception{
        String email = this.jobseeker.getEmail();
        if(email != null && !email.equals("")){
            for (Application app : listApp) {
                if(app.getIdJobOffer() == idJobOffer){
                    throw new Exception("Your are already send a request");
                }
            }
            int newId = this.factory.getApplicationDAO().createApplication(email,idJobOffer,coverletter);
            Application newApp = new Application(newId,"Waiting",idJobOffer,email,coverletter,nameJob,this.jobseeker.getName());
            this.listApp.add(newApp); // we add the new application to the jobSeeker application's list
        }
        else{
            throw new Exception("There is a problem with the email");
        }
    }

    /**
     * Delete the application giving its id
     * @param idApplication id of the application
     * @throws Exception problem with the database or with the user rights
     */
    public void deleteApplication(int idApplication) throws Exception{
        if(FacadeUser.getFacadeUser().getUser().getRole() == User.UserType.JOBSEEKER){
            this.factory.getApplicationDAO().deleteApplication(idApplication);
            // We remove it from the list of the jobSeeker
            this.listApp.removeIf(app -> app.getIdApp() == idApplication);
        }
        else{
            throw new Exception("Your are not a job seeker");
        }

    }


}
