/**
 * Created by cuatroochenta on 5/5/15.
 */
package services.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailSender {

    private String username;
    private  String password;
    private Mail mail;

    //private String puertoEnvio="587";// puertoEnvio="587";//465 para mensajes normales y 587 con adjuntos

    public MailSender(){
        super();
        this.username = "";
        this.password =  "";
    }

    public MailSender( String username, String password ){
        super();
        this.username = username;
        this.password = password;
    }

    public void setUser( String username, String password ){
        this.username = username;
        this.password = password;
    }

    public void setMail( Mail mail ){
        this.mail = mail;
    }

    public void send() {
        Properties props = new Properties();
        props.put("mail.smtp.username", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress( username ));
            msg.setRecipients(Message.RecipientType.TO, this.mail.getReceivers() );
            msg.setSubject( this.mail.getSubject() );
            msg.setSentDate( this.mail.getDate() );
            msg.setContent( this.mail.getContent(),"text/html; charset=utf-8" );

            Transport.send(msg);

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}