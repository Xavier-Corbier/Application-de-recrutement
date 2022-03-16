package com.jobvxs.pl;

import com.jobvxs.bl.*;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestJobCategory {
    AbstractFactoryDAO factory;

    @BeforeEach
    void setUp() {
        try {
            // create a job offer with the new category "test Job Category"
            factory = new FactoryDAOPostGre();
            factory.getJobCategoryDAO().createJobCategory(new JobCategory("test Job Category"));
            Company company = factory.getCompanyDAO().getUserById(new User("company@gmail.com","","company"));
            factory.getJobOfferDAO().createJobOffer(
                    new JobOffer(0,"nameForTest","test Job Category",22,"desc","re","ad","5h","company@gmail.com")
                    ,company
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test Delete Category , set the job offer to \"not type defined\" ")
    void testUpdateCompany() {
        try {
            factory.getJobCategoryDAO().deleteJobCategory("test Job Category");
            JobOffer jobOffer = factory.getJobOfferDAO().getAllJobOfferByEmail("company@gmail.com").stream().filter(
                    jobOffer1 -> jobOffer1.getName().equals("nameForTest")
            ).findFirst().get();

            factory.getJobOfferDAO().deleteJobOffer(jobOffer.getIdJobOffer()); // we delete the job offer for restart the state of the app

            assertTrue(jobOffer.getType().equals("not type defined"), "Type updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
