package com.jobvxs.bl;

public class RatingCompany {

    private int idRate;
    private int nbStars;
    private String comment;
    private String emailJobSeeker;
    private String emailCompany;
    private String nameJobSeeker;

    /**
     * Constuctor of a rating company
     * @param idRate id of the rate
     * @param nbStars rate of the company (between 1 and 5)
     * @param comment commentary of the rate
     * @param emailJobSeeker email of the job seeker that create the rate
     * @param emailCompany email of the company rated
     * @param nameJobSeeker name of the job seeker that rate the company
     */
    public RatingCompany(int idRate, int nbStars, String comment, String emailJobSeeker, String emailCompany, String nameJobSeeker) {
        this.idRate = idRate;
        this.nbStars = nbStars;
        this.comment = comment;
        this.emailJobSeeker = emailJobSeeker;
        this.emailCompany = emailCompany;
        this.nameJobSeeker = nameJobSeeker;
    }

    /**
     * Constructor  of a rating company from an another ratingCompany
     * @param ra an another rating
     */
    public RatingCompany(RatingCompany ra){
        this.idRate = ra.idRate;
        this.nbStars = ra.nbStars;
        this.comment = ra.comment;
        this.emailJobSeeker = ra.emailJobSeeker;
        this.emailCompany = ra.emailCompany;
        this.nameJobSeeker = ra.nameJobSeeker;
    }

    /**
     * Getter for the name of the jobseeker
     * @return the name of the job seeker
     */
    public String getNameJobSeeker() {
        return nameJobSeeker;
    }

    /**
     * Getter for the id of the RatingCompany
     * @return the id of the rate
     */
    public int getIdRate() {
        return idRate;
    }

    /**
     * Getter for the nb of stars of the RatingCompany
     * @return the rate
     */
    public int getNbStars() {
        return nbStars;
    }

    /**
     * Getter for the comment of the RatingCompany
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Getter for the JobSeeker's email that create the rate
     * @return the email of the job seeker
     */
    public String getEmailJobSeeker() {
        return emailJobSeeker;
    }

    /**
     * Getter for the Company's email that concern the rate
     * @return the email of the company
     */
    public String getEmailCompany() {
        return emailCompany;
    }


    /**
     * Setter for the id
     * @param idRate id of the rate
     */
    public void setIdRate(int idRate) {
        this.idRate = idRate;
    }

    /**
     * Setter for the nb of stars
     * @param nbStars rate of the company
     */
    public void setNbStars(int nbStars) {
        this.nbStars = nbStars;
    }

    /**
     * Setter for the comment
     * @param comment comment of the rate
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}
