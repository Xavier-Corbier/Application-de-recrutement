package com.jobvxs.bl;

public class CV {

    private String education;
    private String workExperience;
    private String skillsAndInterests;
    private String references;

    /**
     * Constructor for the CV class
     * @param education
     * @param workExperience
     * @param skillsAndInterests
     * @param references
     */
    public CV(String education, String workExperience, String skillsAndInterests, String references){
        this.education = education;
        this.workExperience = workExperience;
        this.skillsAndInterests = skillsAndInterests;
        this.references = references;
    }

    /**
     * Getter for the education
     * @return
     */
    public String getEducation() {
        return education;
    }

    /**
     * Getter for the workExperience
     * @return
     */
    public String getWorkExperience() {
        return workExperience;
    }

    /**
     * Getter for the skillsAndInterests
     * @return
     */
    public String getSkillsAndInterests() {
        return skillsAndInterests;
    }

    /**
     * Getter for the references
     * @return
     */
    public String getReferences() {
        return references;
    }

    /**
     * Setter for the education
     * @param education
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * Setter for the workExperience
     * @param workExperience
     */
    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    /**
     * Setter for the skillsAndInterests
     * @param skillsAndInterests
     */
    public void setSkillsAndInterests(String skillsAndInterests) {
        this.skillsAndInterests = skillsAndInterests;
    }

    /**
     * Setter for the references
     * @param references
     */
    public void setReferences(String references) {
        this.references = references;
    }
}
