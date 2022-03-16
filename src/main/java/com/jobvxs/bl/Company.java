package com.jobvxs.bl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Company class is a subclass of the UserRole abstract class
 */

public class Company extends UserRole{
    private String name;
    private String address;
    private int numberOfEmployee ;
    private String description;
    private int phoneNumber;
    /**
     * Constructor of the Company class
     * @param user
     * @param name
     * @param address
     * @param numberOfEmployee
     * @param description
     */
    public Company(User user,String name, String address, int numberOfEmployee, String description, int phoneNumber) {
        super(user);
        this.name = name;
        this.address = address;
        this.numberOfEmployee = numberOfEmployee;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter for the email of a Company
     * @return String email
     */
    public String getEmail(){
        return this.getUser().getEmail();
    }

    /**
     * Getter for the name of a Company
     * @return String name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the address
     * @return String address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter for number of employee
     * @return int numberOfEmployee
     */
    public int getNumberOfEmployee() {
        return numberOfEmployee;
    }

    /**
     * Getter for the description
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the phone number
     * @return int phoneNumber
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Get by introspection all the get's method to search the difference between this and the other company
     * @param other
     * @return a map with the values to be modified
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Map<String, String> searchDifference(Company other) throws InvocationTargetException, IllegalAccessException {
        Map<String, String> diff = new HashMap<>();
        // get different getters by introspection
        Class<? extends Company> Company = this.getClass();
        Method[] methods = Company.getDeclaredMethods();
        for(Method m : methods){
            if(m.getName().startsWith("get")){
                Object result = m.invoke(this);
                Object resultOther = m.invoke(other);
                if(m.getName().equals("getEmail") || !(result.toString()).equals(resultOther.toString())){ // different valuer => added to the map
                    String key = m.getName().toLowerCase().substring(3); // (ex : "getName" => "name")
                    diff.put(key,resultOther.toString());
                }
            }
        }
        return diff;
    }
}
