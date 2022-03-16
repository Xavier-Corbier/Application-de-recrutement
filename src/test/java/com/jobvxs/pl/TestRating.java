package com.jobvxs.pl;

import com.jobvxs.bl.*;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRating {

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
    @DisplayName("Test update rate")
    void testUpdateRate(){
        try{
            RateCompanyDAO rateCompanyDAO = factory.getRateCompanyDAO();
            RatingCompany ratingCompany = new RatingCompany(5, 2, "Good company", "yetotev571@pyrelle.com", "company@gmail.com", "blabla");


            FacadeCompany facadeCompany = FacadeCompany.getFacadeCompany();
            facadeCompany.updateRate(ratingCompany);
            RatingCompany ratingCompany1 =  rateCompanyDAO.getRateCompany(5);


            assertTrue(ratingCompany1.getIdRate() == ratingCompany.getIdRate());
            assertTrue(ratingCompany1.getEmailCompany().equals(ratingCompany.getEmailCompany()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
