package com.jobvxs.bl.tools;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGenerateCode {
    @Test
    @DisplayName("Test mail")
    void testLogin() {
        try{
            SendMail.sendCode(GenerateCode.generate(),"corbierxavier@gmail.com");
        }catch (Exception e){
            System.out.println("Error no mail send");
        }

    }
}
