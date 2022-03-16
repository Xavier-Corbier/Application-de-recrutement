package com.jobvxs.pl;

import com.jobvxs.bl.FacadeJobSeeker;
import com.jobvxs.bl.JobSeeker;
import com.jobvxs.bl.User;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TestJobSeeker {

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
    @DisplayName("Test Company Update")
    void testUpdateJobSeeker(){
        try{
            JobSeekerDAO jobSeekerDAO = factory.getJobSeekerDAO();
            FacadeJobSeeker facadeJobSeeker = FacadeJobSeeker.getFacadeJobSeeker();
            User js  = new User("jobseeker007@gmail.com", "", "Job Seeker");
            JobSeeker jobSeeker = jobSeekerDAO.getUserById(js);

            facadeJobSeeker.setJobseeker(jobSeeker);

            Date date = new Date(1990, 3, 4);
            facadeJobSeeker.update("Toto",
                    "8 rue toto",
                    "The best job seeker",
                    "01020304",
                    date,
                    jobSeeker.getEmail(),
                    js.getPassword()
                    );

            JobSeeker jobSeeker2 = facadeJobSeeker.getJobseeker();

            assertTrue(jobSeeker2.getName().equals("Toto"), "Name updated");
            assertTrue(jobSeeker2.getAddress().equals("8 rue toto"), "Address updated");
            assertTrue(jobSeeker2.getDescription().equals("The best job seeker"), "Description updated");
            assertTrue(jobSeeker2.getPhone().equals("01020304"), "Phone number updated");
            assertTrue(jobSeeker2.getBirthday() == date, "Birthday updated");
            assertTrue(jobSeeker2.getEmail().equals(jobSeeker.getEmail()), "Email not updated");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}