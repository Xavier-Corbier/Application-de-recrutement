package com.jobvxs.pl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jobvxs.bl.*;
import com.jobvxs.pl.postgre.FactoryDAOPostGre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;


public class TestCompany {

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
    void testUpdateCompany() {
        try {
            CompanyDAO companyDAO = factory.getCompanyDAO();
            FacadeCompany facadeCompany = FacadeCompany.getFacadeCompany();
            Company com = companyDAO.getUserById(new User("company@gmail.com", "", "company"));

            facadeCompany.setCompany(com);
            facadeCompany.update("Polytech",
                    com.getAddress(),
                    com.getDescription() + " blabla",
                    com.getPhoneNumber(),
                    com.getNumberOfEmployee() + 5,
                    com.getUser().getEmail(),
                    "test");

            Company com2 = facadeCompany.getCompany(); // company updated

            assertTrue(com2.getName().equals("Polytech"), "Name updated ");
            assertTrue(com2.getAddress().equals(com.getAddress()), "Address not updated");
            assertTrue(com2.getDescription().equals(com.getDescription() + " blabla"), "Desc not updated");
            assertTrue(com2.getPhoneNumber() == com.getPhoneNumber(), "Phone not updated");
            assertTrue(com2.getNumberOfEmployee() == (com.getNumberOfEmployee() + 5), "number updated");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
