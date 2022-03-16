package com.jobvxs.bl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class JobOffer {
    private int idJobOffer;
    private String name;
    private String type;
    private double salary;
    private String description;
    private String requirements;
    private String advantages;
    private String workSchedule;
    private String emailCompany;

    /**
     * Constructor for the jobOffer class
     * @param idJobOffer
     * @param name
     * @param type
     * @param salary
     * @param description
     * @param requirements
     * @param advantages
     * @param workSchedule
     * @param emailCompany
     */
    public JobOffer(int idJobOffer, String name, String type, double salary, String description, String requirements, String advantages, String workSchedule, String emailCompany) {
        this.idJobOffer = idJobOffer;
        this.name = name;
        this.type = type;
        this.salary = salary;
        this.description = description;
        this.requirements = requirements;
        this.advantages = advantages;
        this.workSchedule = workSchedule;
        this.emailCompany = emailCompany;
    }

    /**
     * Getter for the id of a jobOffer
     * @return
     */
    public int getIdJobOffer() {
        return idJobOffer;
    }

    /**
     * Setter for the id of a jobOffer
     * @param idJobOffer
     */
    public void setIdJobOffer(int idJobOffer) {
        this.idJobOffer = idJobOffer;
    }

    /**
     * Getter for the name of a jobOffer
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of a joboffer
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the type of a jobOffer
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for the type of a JobOffer
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for the salary of a jobOffer
     * @return
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Setter for the salary of a jobOffer
     * @param salary
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Getter for the description of a jobOffer
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description of a jobOffer
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the requirements of a jobOffer
     * @return
     */
    public String getRequirements() {
        return requirements;
    }

    /**
     * Setter for the requirements of a jobOffer
     * @param requirements
     */
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    /**
     * Getter for the advantage of a jobOffer
     * @return
     */
    public String getAdvantages() {
        return advantages;
    }

    /**
     * Setter for the advantage of a jobOffer
     * @param advantages
     */
    public void setAdvantages(String advantages) {
        this.advantages = advantages;
    }

    /**
     * Getter for the workSchedule of a jobOffer
     * @return
     */
    public String getWorkSchedule() {
        return workSchedule;
    }

    /**
     * Setter for the workSchedule of a jobOffer
     * @param workSchedule
     */
    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }

    /**
     * Getter for the email of a company of a jobOffer
     * @return
     */
    public String getEmailCompany() {
        return emailCompany;
    }

    /**
     * Setter for the email of a company of a jobOffer
     * @param emailCompany
     */
    public void setEmailCompany(String emailCompany) {
        this.emailCompany = emailCompany;
    }

    /**
     * Get by introspection all the get's method to search the difference between this and the other job offer
     * @param other
     * @return a map with the values to be modified
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Map<String, String> searchDifference(JobOffer other) throws InvocationTargetException, IllegalAccessException {
        Map<String, String> diff = new HashMap<>();
        // get different getters by introspection
        Class<? extends JobOffer> JobOffer = this.getClass();
        Method[] methods = JobOffer.getDeclaredMethods();
        for(Method m : methods){
            if(m.getName().startsWith("get")){
                Object result = m.invoke(this);
                Object resultOther = m.invoke(other);
                if(m.getName().equals("getIdJobOffer") || !(result.toString()).equals(resultOther.toString())){ // different valuer => added to the map
                    String key = m.getName().toLowerCase().substring(3); // (ex : "getName" => "name")
                    diff.put(key,resultOther.toString());
                }
            }
        }
        return diff;
    }
}
