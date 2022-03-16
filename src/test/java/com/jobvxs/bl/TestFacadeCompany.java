package com.jobvxs.bl;

import com.jobvxs.pl.AbstractFactoryDAO;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFacadeCompany {
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
    @DisplayName("Test Notification")
    void testNotification() {
        ArrayList<Notification> list = new ArrayList<>();
        try {
            User user = factory.getUserDAO().getUserById("company@gmail.com");
            FacadeUser.getFacadeUser().login("company@gmail.com","test");
            FacadeUser facade = FacadeUser.getFacadeUser();
            list = facade.getAllNotification();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("At the moment of the test , there are 4 notifications");
        assertEquals(list.size(),4);
    }

    @Test
    @DisplayName("Test Comment")
    void testComment() {
        ArrayList<CommentJobOffer> list = new ArrayList<>();
        try {
            User user = factory.getUserDAO().getUserById("jobseeker@gmail.com");
            FacadeUser.getFacadeUser().login("jobseeker@gmail.com","test");
            JobSeeker jobSeeker = factory.getJobSeekerDAO().getUserById(user);
            FacadeCompany facade = FacadeCompany.getFacadeCompany();
            list = facade.getAllCommentJobOffer(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("At the moment of the test there are 8 comment");
        assertEquals(list.size(),8);

    }
}
