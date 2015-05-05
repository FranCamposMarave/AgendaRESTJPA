package services.mail;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Mail {

    private String subject;
    private Date date;
    private String content;
    private ArrayList<InternetAddress> receivers;

    public Mail(){
        super();
        this.subject = "";
        this.date = Calendar.getInstance().getTime();
        this.content = "";
        this.receivers = new ArrayList<InternetAddress>();
    }

    public void setSubject( String subject ){
        this.subject = subject;
    }

    public String getSubject(){
        return this.subject;
    }

    public void setDate( Date date ){
        this.date = date;
    }

    public Date getDate(){
        return this.date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Address[] getReceivers() {
        InternetAddress[] receiversAux = new InternetAddress[ this.receivers.size() ];
        for ( int i = 0; i < this.receivers.size(); i++ ){
            receiversAux[i] = this.receivers.get( i );
        }
        return receiversAux;
    }

    public void addReceiver( String r ) throws AddressException {
        this.receivers.add( new InternetAddress( r ) );
    }

    public void removeReceivers(){
        this.receivers = new ArrayList<InternetAddress>();
    }

}