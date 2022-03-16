package com.jobvxs.bl;

import com.jobvxs.pl.AbstractFactoryDAO;
import com.jobvxs.pl.UserDAO;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFacadeUser {
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
    @DisplayName("Test login")
    void testLogin() {
        User user = null;
        try {
            FacadeUser.getFacadeUser().login("company@gmail.com","test");
            user = FacadeUser.getFacadeUser().getUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(user.getEmail(),"company@gmail.com");
    }
}
