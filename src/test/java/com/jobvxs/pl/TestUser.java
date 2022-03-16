package com.jobvxs.pl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jobvxs.bl.Administrator;
import com.jobvxs.bl.Company;
import com.jobvxs.bl.JobSeeker;
import com.jobvxs.bl.User;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class TestUser {
    AbstractFactoryDAO factory;

    @BeforeEach
    void setUp() {
        try {
            factory = new FactoryDAOPostGre();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test User type Administrator")
    void testUserTypeAdministrator() {
        User user = null;
        try {
            UserDAO userDAO = factory.getUserDAO();
            user = userDAO.getUserById("admin@gmail.com");

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(user.getRole() == User.UserType.ADMINISTRATOR,"");
    }

    @Test
    @DisplayName("Test User type Company")
    void testUserTypeCompany() {
        User user = null;
        try {
            UserDAO userDAO = factory.getUserDAO();
            user = userDAO.getUserById("company@gmail.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(user.getRole() == User.UserType.COMPANY,"");
    }

    @Test
    @DisplayName("Test User type JobSeeker")
    void testUserTypeJobSeeker() {
        User user = null;
        try {
            UserDAO userDAO = factory.getUserDAO();
            user = userDAO.getUserById("jobseeker@gmail.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(user.getRole() == User.UserType.JOBSEEKER,"");
    }
}