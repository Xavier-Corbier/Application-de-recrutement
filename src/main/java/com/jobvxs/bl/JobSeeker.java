package com.jobvxs.bl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * JobSeeker class is a subclass of the UserRole abstract class
 */

public class JobSeeker extends UserRole{
    private String name;
    private String address;
    private Date birthday;
    private String description;
    private String phone;

    /**
     * Constructor of the JobSeeker class
     * @param user reference ot the user
     * @param name name of the job seeker
     * @param address address of the job seeker
     * @param birthday birthday of the job seeker
     * @param description description of the job seeker
     * @param phone phone number of the job seeker
     */
    public JobSeeker(User user,String name, String address, Date birthday, String description, String phone) {
        super(user);
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        this.description = description;
        this.phone = phone;
    }

    /**
     * Getter for the name of a JobSeeker
     * @return the name of the job seeker
     */
    public String getName() {
        return this.name;
    }

    /***
     * Getter for the address of a JobSeeker
     * @return the address of the job seeker
     */
    public String getAddress() {
        return address;
    }

    /***
     * Getter for the email of a JobSeeker
     * @return the email of the job seeker
     */
    public String getEmail(){
        return super.getUser().getEmail();
    }

    /***
     * Getter for the birthday of a JobSeeker
     * @return the birthday of the JobSeeker
     */
    public Date getBirthday() {
        return birthday;
    }

    /***
     * Getter for the description of a JobSeeker
     * @return the description of the job seeker
     */
    public String getDescription() {
        return description;
    }

    /***
     * Getter for the phone of a JobSeeker
     * @return the phone number of the job seeker
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Get by introspection all the get's method to search the difference between this and the other jobseeker
     * @param other the new values of the job seeker
     * @return a map with the values to be modified
     * @throws InvocationTargetException exception
     * @throws IllegalAccessException exception
     */
    public Map<String, String> searchDifference(JobSeeker other) throws InvocationTargetException, IllegalAccessException {
        Map<String, String> diff = new HashMap<>();
        // get different getters by introspection
        Class<? extends JobSeeker> Job_Seeker = this.getClass();
        Method[] methods = Job_Seeker.getDeclaredMethods();
        for(Method m : methods){
            if(m.getName().startsWith("get")){ // getter find
                Object result = m.invoke(this); // (== result = this.m())
                Object resultOther = m.invoke(other);
                if(m.getName().equals("getEmail") || !(result.toString()).equals(resultOther.toString())){ // different valuer => added to the map
                    String key = m.getName().toLowerCase().substring(3); // (ex : "getName" => "name")
                    System.out.println("result : "+result+" | resultOther : "+resultOther);
                    diff.put(key,resultOther.toString());
                }
            }
        }
        return diff;
    }
}
