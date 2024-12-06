package com.three_amigas.LaundryOps;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SMTPClient {
    private final String host = "smtp.gmail.com";
    private final String user = "washing.well.oop@gmail.com";
    private final String password = "idll zmsq ekcp vdoy";
    
    public void sendMail(String email, String name) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", this.host);
        properties.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("🌪Your Laundry is Ready for Pickup!");
            message.setText("Hello " + name + ",\n\nYour laundry is ready! All your items have been washed and are ready for pickup. Thank you for using our service!");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}