package services.mail;

/**
 * Created by cuatroochenta on 5/5/15.
 */
public class SenderMailThread extends Thread {

    private MailSender senderMailing;

    public SenderMailThread( MailSender sender ){
        super();
        this.senderMailing = sender;
    }

    public void run(){
        System.out.println("Inicio de envio de email...");
        senderMailing.send();
        System.out.println("Email Enviado!!");
    }
}