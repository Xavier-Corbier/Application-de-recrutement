package com.jobvxs.bl.tools;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Java Program to send text mail using default SMTP server and
 * without authentication.
 * You need mail.jar, smtp.jar and activation.jar to run this program.
 */
public class SendMail{

    /**
     * Send code to an email with stmp Gmail
     * @param code
     * @param mail
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public static void sendCode(String code,String mail) throws UnsupportedEncodingException, MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.localhost", "yoursite.com");

        Session s = Session.getInstance(props, null);
        s.setDebug(false);

        MimeMessage message = new MimeMessage(s);

        InternetAddress from = new InternetAddress("noreply.jobvxs@gmail.com",
                "No reply");
        InternetAddress to = new InternetAddress(mail);

        message.setSentDate(new Date());
        message.setFrom(from);
        message.addRecipient(Message.RecipientType.TO, to);

        message.setSubject("JobVXS - Check Code");
        message.setContent("Your code is : "+code, "text/plain");

        Transport tr = s.getTransport("smtp");
        tr.connect("smtp.gmail.com", "projetjava.jobvxs@gmail.com", "projetjava34");
        message.saveChanges();
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();
    }

}