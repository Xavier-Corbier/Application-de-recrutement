package com.jobvxs.pl.postgre;

import com.jobvxs.bl.User;
import com.jobvxs.pl.UserDAO;
import com.jobvxs.pl.connect.PostGreSqlConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implement UserDAO for PostGreSQL
 */
public class UserDAOPostGre implements UserDAO {
    Connection conn;
    /**
     * Construct UserDAO in PostGreSQL.
     * It is possible to handle an error if the Database is not connected
     * @throws SQLException
     */
    public UserDAOPostGre(Connection conn) throws SQLException {
        this.conn=conn;
    }

    /**
     * Get by email and password the User object save in the database
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public User getUserById(String email) throws Exception {
        User user = null;
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM public.\"User\" WHERE email='"+email+"'");

        //exécuter la requête
        if (!res.next()){
            throw new Exception("Error : No user found");
        } else {
             do {
                 String role = res.getString("type");

                 if(role.equals("Administrator") || role.equals("Job Seeker")  || role.equals("Company")  ){
                     user = new User(res.getString("email"), res.getString("password"),res.getString("type"));
                 } else {
                    throw new Exception("Error : No type of this user");
                 }
            } while(res.next());
        }
        return user;
    }

}
