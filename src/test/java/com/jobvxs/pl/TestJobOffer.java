package com.jobvxs.pl;

import com.jobvxs.bl.*;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJobOffer {
    AbstractFactoryDAO factory;

    @BeforeEach
    void setUp() {
        try {
            factory = new FactoryDAOPostGre();
            factory.getJobOfferDAO().createJobOffer(new JobOffer(
                    1,"stage IT TEST","CDI",500,"titi","toto","tata","5h-22h","company@gmail.com"),
                    new Company(new User("company@gmail.com","","Company"),
                            "..","..",1,"22",0505050505)
            );
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test JobOffer created")
    void testUpdateJobSeeker(){
        try{
            AtomicBoolean find = new AtomicBoolean(false);
            AtomicInteger idJobOffer = new AtomicInteger(-1);
            JobOfferDAO jobOfferDAO = factory.getJobOfferDAO();
            jobOfferDAO.getAllJobOffer().forEach( j -> {
                if(!find.get()){ // we didn't find the job offer
                    if(j.getName().equals("stage IT TEST")){
                        find.set(true);
                        idJobOffer.set(j.getIdJobOffer());
                        assertTrue(j.getType().equals("CDI"),"type equal");
                        assertTrue(j.getDescription().equals("titi"),"description equal");
                        assertTrue(j.getAdvantages().equals("tata"),"advantages equal ");
                        assertTrue(j.getEmailCompany().equals("company@gmail.com"),"email equal");
                        assertTrue(j.getSalary() == 500,"salary equal");
                        assertTrue(j.getWorkSchedule().equals("5h-22h"),"work Scheduled equal");
                    }
                }
            });

            // delete the job offer for restart the state of the application
            if(idJobOffer.get() != -1){
                jobOfferDAO.deleteJobOffer(idJobOffer.get());
            }


            assertTrue(find.get(),"JobOffer found");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
