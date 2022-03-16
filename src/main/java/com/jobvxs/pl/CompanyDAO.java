package com.jobvxs.pl;

import com.jobvxs.bl.Company;
import com.jobvxs.bl.User;

import java.util.ArrayList;
import java.util.Map;

/**
 * Allows to maintain the Company with the type of save
 */
public interface CompanyDAO {

    /**
     * Get the Company object in the type of save
     * @param user
     * @return
     * @throws Exception
     */
    public Company getUserById(User user) throws Exception;

    /**
     * Save the company in the DAO
     * @param company
     * @throws Exception
     */
    public void save(Company company) throws Exception;

    /**
     * Delete the company in the DAO
     * @param email
     * @throws Exception
     */
    public void delete(String email) throws Exception;

    /**
     * Update a company in the DAO
     * @param companyModif
     * @throws Exception
     */
    public void update(Map<String, String> companyModif) throws Exception ;

    /**
     * Get the list of all companies
     * @return
     * @throws Exception
     */
    public ArrayList<Company> getListOfCompanies() throws Exception;
}
