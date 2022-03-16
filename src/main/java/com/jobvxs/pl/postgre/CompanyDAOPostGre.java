package com.jobvxs.pl.postgre;

import com.jobvxs.bl.Company;
import com.jobvxs.bl.User;
import com.jobvxs.pl.CompanyDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Implement CompanyDAO for PostGreSQL
 */
public class CompanyDAOPostGre implements CompanyDAO {
    Connection conn ;

    /**
     * Construct CompanyDAO in PostGreSQL.
     * It is possible to handle an error if the Database is not connected
     * @throws SQLException
     */
    public CompanyDAOPostGre(Connection conn) throws SQLException {
        this.conn=conn;
    }

    /**
     * Get by user email the Company associated object save in the database
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public  Company getUserById(User user) throws Exception {
        Company company = null;
        Statement stmt = conn.createStatement();
        // execute request
        ResultSet res = stmt.executeQuery("SELECT * FROM public.\"Company\" WHERE email='"+user.getEmail()+"'");
        // if it is empty
        if(!res.next()){
            throw new Exception("Error : no company found");
        } else {
            do {
                company = new Company(user,res.getString("name"),res.getString("address"), res.getInt("numberofemployee"),res.getString("description"), res.getInt("phonenumber"));
            } while(res.next());
            // while we have a row
        }
        return company;
    }

    /**
     * Save the company in the database
     * @param company
     * @throws Exception
     */
    @Override
    public void save(Company company) throws Exception {
        User user = company.getUser();
        String requete = "INSERT INTO public.\"User\" VALUES (?,?,'Company');";
        requete += "INSERT INTO public.\"Company\" VALUES (?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(requete);
        pstmt.setString(1,user.getEmail());
        pstmt.setString(2,user.getPassword());
        pstmt.setString(3,user.getEmail());
        pstmt.setString(4,company.getDescription());
        pstmt.setString(5,company.getAddress());
        pstmt.setInt(6,company.getNumberOfEmployee());
        pstmt.setString(7,company.getName());
        pstmt.setInt(8,company.getPhoneNumber());
        // execute the request
        pstmt.executeUpdate();
    }

    /**
     * Delete a company from the database
     * @param email
     * @throws Exception
     */
    @Override
    public void delete(String email) throws Exception {
        // execute the request
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM public.\"User\" WHERE email = ?");
        pstmt.setString(1,email);
        pstmt.executeUpdate();
    }

    /**
     * Update a company in the database
     * @param companyModif
     * @throws Exception
     */
    @Override
    public void update(Map<String, String> companyModif) throws Exception {
        StringBuilder requete = new StringBuilder("UPDATE public.\"Company\" ");
        StringBuilder whereCondition = new StringBuilder("");
        PreparedStatement pstmt;
        int nbChange = 0;
        // create the syntax of the request
        nbChange = getNbChange(companyModif, requete, whereCondition, nbChange);

        if(nbChange>0){
            requete.deleteCharAt(requete.length()-1);
            pstmt = conn.prepareStatement(requete.toString() + whereCondition);
            // set the request
            setNewValuesRequest(companyModif, pstmt, nbChange);
            pstmt.executeUpdate();
        }
    }

    /**
     * Set the new values in the request
     * @param companyModif
     * @param pstmt
     * @param nbChange
     * @throws SQLException
     */
    private void setNewValuesRequest(Map<String, String> companyModif, PreparedStatement pstmt, int nbChange) throws SQLException {
        int i = 1;
        // Set the value of the ?
        for(Map.Entry<String,String> entry : companyModif.entrySet()){
            if(entry.getKey().equals("email")){ // is the where condition
                pstmt.setString(nbChange +1,entry.getValue());
            }
            else{
                if (entry.getKey().equals("numberofemployee")||entry.getKey().equals("phonenumber")){ // is int
                    pstmt.setInt(i, Integer.parseInt(entry.getValue()));
                }
                else{
                    pstmt.setString(i, entry.getValue());
                }
                i++;
            }
        }
    }

    /**
     * Get the number of changement in the request
     * @param companyModif
     * @param requete
     * @param nbChange
     * @return
     */
    private int getNbChange(Map<String, String> companyModif, StringBuilder requete,StringBuilder whereCondition, int nbChange) {
        for(Map.Entry<String,String> entry : companyModif.entrySet()){
            if(entry.getKey().equals("email")){ // is the where condition
                whereCondition.append(" WHERE email = ?");
            }
            else{
                if(nbChange == 0){
                    requete.append("SET ");
                }
                nbChange++;
                requete.append(entry.getKey()).append(" = ?,");
            }
        }
        return nbChange;
    }

    /**
     * Get the list of all companies
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Company> getListOfCompanies() throws Exception {
        ArrayList<Company> companies = new ArrayList<>();
        Statement stmt = conn.createStatement();
        // execute the request
        ResultSet res = stmt.executeQuery("SELECT * FROM public.\"Company\"");
        // if it is empty
        if(!res.next()){
            throw new Exception("Error: no Job Seeker Found");
        } else {
            do {
                companies.add(new Company(new User(
                        res.getString("email"),
                        "",
                        "Company")
                        ,res.getString("name"),
                        res.getString("address"),
                        res.getInt("numberofemployee"),
                        res.getString("description"),
                        res.getInt("phonenumber")));
            } while(res.next());
            // while we have a row
        }
        return companies;
    }
}
