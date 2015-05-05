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
        senderMailing.send();
    }
}