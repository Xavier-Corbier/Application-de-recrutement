package com.jobvxs.bl;

import com.jobvxs.pl.AbstractFactoryDAO;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFacadeJobSeeker {
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
    @DisplayName("Test set CV")
    void testSetCV() {
        CV cv2 = null;
        try {
            User user = factory.getUserDAO().getUserById("jobseeker@gmail.com");
            FacadeUser.getFacadeUser().login("jobseeker@gmail.com","test");
            JobSeeker jobSeeker = factory.getJobSeekerDAO().getUserById(user);
            FacadeJobSeeker facade = FacadeJobSeeker.getFacadeJobSeeker();
            facade.setJobseeker(jobSeeker);
            CV cv = facade.getInfoCV();
            cv.setWorkExperience("polytech");
            facade.updateCV(cv);
            cv2 = facade.getInfoCV();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(cv2.getWorkExperience(),"polytech");

    }
}
