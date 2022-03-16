package com.jobvxs.pl;

import com.jobvxs.bl.*;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestApplication {

    AbstractFactoryDAO factory;

    @BeforeEach
    void setUp(){
        try{
            factory = new FactoryDAOPostGre();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test create Application")
    void testCreateApplication(){
        try{
            JobSeekerDAO jobSeekerDAO = factory.getJobSeekerDAO();
            FacadeJobSeeker facadeJobSeeker = FacadeJobSeeker.getFacadeJobSeeker();
            JobSeeker jobSeeker = jobSeekerDAO.getUserById(new User("jakifeg157@ritumusic.com", "", "jobSeeker"));
            facadeJobSeeker.setJobseeker(jobSeeker);
            facadeJobSeeker.createApplication(10, "Developpeur Full Stack", "My cover letter");

            AtomicBoolean find = new AtomicBoolean(false);
            ApplicationDAO applicationDAO = factory.getApplicationDAO();
            applicationDAO.getApplicationByJobSeeker("jakifeg157@ritumusic.com").forEach(a -> {
                if(a.getIdJobOffer() == 10 && (a.getNomJobOffer()).equals("Developpeur Full Stack") && (a.getCoveringLetter()).equals("My cover letter")){
                    find.set(true);
                }
            });

            assertTrue(find.get(),"Application found");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
