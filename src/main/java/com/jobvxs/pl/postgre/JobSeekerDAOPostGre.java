package com.jobvxs.pl.postgre;

import com.jobvxs.bl.JobSeeker;
import com.jobvxs.bl.User;
import com.jobvxs.pl.JobSeekerDAO;
import com.jobvxs.pl.connect.PostGreSqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Implement JobSeekerDAO for PostGreSQL
 */
public class JobSeekerDAOPostGre implements JobSeekerDAO {
    Connection conn ;

    /**
     * Construct JobSeekerDAO in PostGreSQL.
     * It is possible to handle an error if the Database is not connected
     * @throws SQLException
     */
    public JobSeekerDAOPostGre(Connection conn) throws SQLException {
        this.conn = conn;
    }

    /**
     * Get by user email the Job Seeker associated object save in the database
     * @param user user information of the application
     * @return the job seeker corresponding to the user
     * @throws Exception for the DB
     */
    @Override
    public JobSeeker getUserById(User user) throws Exception {
        JobSeeker jobSeeker = null;
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM public.\"JobSeeker\" WHERE email= ? ");
        // execute the request
        pstmt.setString(1,user.getEmail());
        ResultSet res = pstmt.executeQuery();
        // if it is empty
        if(!res.next()){
            throw new Exception("Error: no Job Seeker Found");
        } else {
            do {
                jobSeeker = new JobSeeker(user,res.getString("name"), res.getString("address"),res.getDate("birthday"),res.getString("description"),res.getString("phone"));
            } while(res.next());
            // while we have a row
        }
        return jobSeeker;
    }

    /**
     * Get the list of all jobSeeker
     * @return the list of all the job seeker
     * @throws Exception for the DB
     */
    @Override
    public ArrayList<JobSeeker> getListOfJobSeeker() throws Exception {
        ArrayList<JobSeeker> jobSeekers = new ArrayList<>();
        Statement stmt = conn.createStatement();
        // execute the request
        ResultSet res = stmt.executeQuery("SELECT * FROM public.\"JobSeeker\"");
        // if it is empty
        if(!res.next()){
            throw new Exception("Error: no Job Seeker Found");
        } else {
            do {
                jobSeekers.add(new JobSeeker(new User(res.getString("email"),"","Job Seeker"),res.getString("name"), res.getString("address"),res.getDate("birthday"),res.getString("description"),res.getString("phone")));
            } while(res.next());
            // while we have a row
        }
        return jobSeekers;
    }

    /**
     * Update a jobseeker in the database
     * @param jobseekerModif Map that contains all the modification of the job seeker
     * @throws Exception for the DB
     */
    @Override
    public void update(Map<String, String> jobseekerModif) throws Exception {
        StringBuilder requete = new StringBuilder("UPDATE public.\"JobSeeker\" ");
        String whereCondition = "";
        PreparedStatement pstmt;
        int nbChange = 0;
        // create the syntax of the request
        for(Map.Entry<String,String> entry : jobseekerModif.entrySet()){
            if(entry.getKey().equals("email")){ // is the where condition
                whereCondition = " WHERE email = ?";
            }
            else{
                if(nbChange == 0){
                    requete.append("SET ");
                }
                nbChange++;
                requete.append(entry.getKey()).append(" = ?,");
            }
        }
        requete.deleteCharAt(requete.length()-1);

        pstmt = conn.prepareStatement(String.valueOf(requete.append(whereCondition)));

        int i = 1;
        // Set the value of the ?
        for(Map.Entry<String,String> entry : jobseekerModif.entrySet()){
            if(entry.getKey().equals("email")){ // is the where condition
                pstmt.setString(nbChange+1,entry.getValue());
            }
            else{
                if (entry.getKey().equals("birthday")){ // is sql.date
                    pstmt.setDate(i, Date.valueOf(entry.getValue()));
                }
                else{
                    pstmt.setString(i, entry.getValue());
                }
                i++;
            }
        }

        pstmt.executeUpdate();
    }

    /**
     * Delete a jobSeeker from the database
     * @param email email of the job seeker
     * @throws Exception for the DB
     */
    @Override
    public void delete(String email) throws Exception {
        // execute the request
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM public.\"User\" WHERE email = ?");

        pstmt.setString(1,email);
        pstmt.executeUpdate();

    }

    /**
     * Register a jobSeeker in the database
     * @param jobSeeker State of the new Job Seeker
     * @throws Exception for the DB
     */
    @Override
    public void save(JobSeeker jobSeeker) throws Exception {

        User user = jobSeeker.getUser();

        String requete = "INSERT INTO public.\"User\" VALUES (?,?,'Job Seeker');";
        requete += "INSERT INTO public.\"JobSeeker\" VALUES (?,?,?,?,?,?);";

        PreparedStatement pstmt = conn.prepareStatement(requete);
        pstmt.setString(1,user.getEmail());
        pstmt.setString(2,user.getPassword());
        pstmt.setString(3,user.getEmail());
        pstmt.setString(4,jobSeeker.getName());
        pstmt.setString(5,jobSeeker.getAddress());
        pstmt.setDate(6,jobSeeker.getBirthday());
        pstmt.setString(7,jobSeeker.getDescription());
        pstmt.setString(8,jobSeeker.getPhone());

        // execute the request
        pstmt.executeUpdate();

    }
}
