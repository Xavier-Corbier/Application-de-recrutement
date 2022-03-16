package com.jobvxs.bl;

public class Application {

    /**
     * Enum for the possible state of the application
     */
    public enum StateApplication {
        WAITING, REJECTED, ACCEPTED
    }

    private int idApp;
    private StateApplication state;
    private int idJobOffer;
    private String emailJobSeeker;
    private String coveringLetter;
    private String nomJobOffer;
    private String nomJobSeeker;


    /**
     * Constructor of the Application class
     * @param idApp id of the app
     * @param state state of the app
     * @param idJobOffer id of the job offer of the application
     * @param emailJobSeeker email of the jobSeeker that create the application
     * @param nomJobOffer name of the job offer of the application
     * @param nomJobSeeker name of the jobSeeker that create the application
     */
    public Application(int idApp, String state, int idJobOffer, String emailJobSeeker,String coveringLetter, String nomJobOffer, String nomJobSeeker) {
        this.idApp = idApp;
        switch (state) {
            case "Waiting" -> this.state = StateApplication.WAITING;
            case "Rejected" -> this.state = StateApplication.REJECTED;
            case "Accepted" -> this.state = StateApplication.ACCEPTED;
            default -> this.state = null;
        }
        this.coveringLetter = coveringLetter;
        this.idJobOffer = idJobOffer;
        this.emailJobSeeker = emailJobSeeker;
        this.nomJobOffer = nomJobOffer;
        this.nomJobSeeker = nomJobSeeker;
    }

    /**
     * Getter for the id of the application
     * @return the id of the application
     */
    public int getIdApp() {
        return idApp;
    }

    /**
     * Getter for the state of the application
     * @return the state of the application
     */
    public StateApplication getState() {
        return state;
    }

    /**
     * Getter for the id of the jobOffer
     * @return the id of the job offer of the application
     */
    public int getIdJobOffer() {
        return idJobOffer;
    }

    /**
     * Getter of the email of the jobSeeker that create this application
     * @return the email of the job seeker that sent the application
     */
    public String getEmailJobSeeker() {
        return emailJobSeeker;
    }

    /**
     * Getter of the name of the jobSeeker that create this application
     * @return the name of the job offer
     */
    public String getNomJobOffer() {
        return nomJobOffer;
    }

    /**
     * Getter of the name of the jobSeeker that create this application
     * @return the name of the job seeker
     */
    public String getNomJobSeeker() {
        return nomJobSeeker;
    }

    /**
     * Getter of the coveringLetter of the application
     * @return the content of the covering letter
     */
    public String getCoveringLetter() {
        return coveringLetter;
    }

    /**
     * Setter for the id of the application
     * @param idApp the new id of the application
     */
    public void setIdApp(int idApp) {
        this.idApp = idApp;
    }

    /**
     * Setter for the state of the application
     * @param state the new state of the application
     */
    public void setState(StateApplication state) {
        this.state = state;
    }
}
