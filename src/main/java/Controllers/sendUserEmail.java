package Controllers;
import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.*;


public class sendUserEmail {
    public sendUserEmail(String email) {

        String to=email;

        String from="Rashmidias819@gmail.com";

        String host="localhost";

        Properties properties=System.getProperties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.starttls.enable", "true");

       // properties.setProperty("mail.smtp.host",host);

        Session session=Session.getDefaultInstance(properties,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("Rashmidias819@gmail.com","wbkdlrirsdikhcvh");
            }
        });

        try{
            MimeMessage message=new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(to)));
            message.setSubject("This it is");
            message.setText("Confirmed");
            Transport.send(message);
            System.out.println("This is messege");
        }catch(MessagingException e){
            e.printStackTrace();
        }



    }

    public static void main(String[] args) {


    }

}
