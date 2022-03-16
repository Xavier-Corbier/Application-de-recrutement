package com.jobvxs.bl;

/**
 * Class to manage the job categories of the software
 */
public class JobCategory {
    private String name;

    /**
     * Constructor for the JobCategory class
     * @param name
     */
    public JobCategory(String name) {
        this.name = name;
    }

    /**
     * Get the name of the category
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the category
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
