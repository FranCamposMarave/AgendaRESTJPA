package services.mail;

import model.entities.Activity;
import model.entities.Reservation;
import org.openqa.selenium.logging.LogType;

import javax.mail.internet.AddressException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cuatroochenta on 5/5/15.
 */
public class MailService implements IMailService {

    private static final String FILE_INFO_RESERVATION = "./emailcontents/info-reservation.html";

    private final String senderMail = "rar.developers@gmail.com";
    private final String senderPassword = "cachibache";


    private MailSender sender;

    public MailService(){
        super();
        this.sender = new MailSender();
        this.sender.setUser( senderMail, senderPassword);

    }

    public void sendInfoEmailReservationToUser( Reservation reservation ){
        Activity activity = reservation.getActivity();

        String name = reservation.getName();
        String lastname = reservation.getLastName();
        String titleActivity = activity.getTitle();
        int places = reservation.getPlaces();
        Date date = activity.getDate();
        DateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat formatterHour = new SimpleDateFormat("HH:mm");
        String dateAct = formatterDate.format( date );
        String hourAct = formatterHour.format( date );
        //String receiver = reservation.getEmail(); TODO
        String receiver = "al227763@uji.es";

        String content = readFile( FILE_INFO_RESERVATION );

        content = content.replace("${TITLE_ACTIVITY}", titleActivity).replace("${NAME}", name)
                .replace("${LASTNAME}", lastname).replace("${HOUR}", hourAct).replace("${DATE}", dateAct);

        sendEmail("Confirmación de reserva", content, receiver);
    }

    private void sendEmail(String subject, String content, String receiver){
        Mail mail = new Mail();
        try {
            mail.addReceiver( receiver );
            mail.setSubject( subject );
            mail.setContent( content );
            this.sender.setMail( mail );

            SenderMailThread thread = new SenderMailThread( sender );
            thread.start();
        } catch (AddressException e) {
            e.printStackTrace();
        }
    }


    private String readFile(String fileName){
        String content = "";
        String line;
        FileReader fr;
        try {
            fr = new FileReader( getClass().getResource( fileName ).getPath().toString() );
            BufferedReader br = new BufferedReader(fr);
            while( ( line = br.readLine() ) != null) {
                content += line;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

}
