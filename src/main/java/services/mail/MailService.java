package services.mail;

import model.entities.Activity;
import model.entities.Reservation;
import org.openqa.selenium.logging.LogType;

import javax.mail.internet.AddressException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cuatroochenta on 5/5/15.
 */
public class MailService implements IMailService {

    private static final String FILE_INFO_RESERVATION = "info-reservation.html";

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
        String receiver = reservation.getEmail();

        String content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"ISO-8859-1\">\n" +
                "    <title>Información de registro a actividad</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2>Reserva de ${TITLE_ACTIVITY}</h2>\n" +
                "<p>Estimado/a ${NAME} ${LASTNAME} nos complace comunicarle que la reserva de la actividad ${TITLE_ACTIVITY}\n" +
                "    se ha realizado correctamente.</p><br><br>\n" +
                "<p>A contincuación se muestran los detalles de la reserva:</p>\n" +
                "<ul>\n" +
                "    <li><b>Nombre de reserva: </b> ${NAME} ${LASTNAME}</li>\n" +
                "    <li><b>Actividad: </b> ${TITLE_ACTIVITY}</li>\n" +
                "    <li><b>Plazas: </b> ${PLACES}</li>\n" +
                "    <li><b>Fecha: </b> ${DATE}</li>\n" +
                "    <li><b>Hora: </b> ${HOUR}</li>\n" +
                "</ul>\n" +
                "<br />\n" +
                "<p>\n" +
                "    Muchas gracias por confiar en nuestros servicios. <br />\n" +
                "    <i>Atentamente: NaturAdventure</i>\n" +
                "</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        content = content.replace("${TITLE_ACTIVITY}", titleActivity).replace("${NAME}", name)
                .replace("${LASTNAME}", lastname).replace("${HOUR}", hourAct).replace("${DATE}", dateAct).replace("${PLACES}",Integer.toString( places) );

        sendEmail("Confirmación de reserva", content, receiver);
    }

    public void sendInfoEmailReservationCancel( Reservation reservation ){
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
        String receiver = reservation.getEmail();

        String content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"ISO-8859-1\">\n" +
                "    <title>Información de cancelación de reserva</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2>Reserva de ${TITLE_ACTIVITY}</h2>\n" +
                "<p>Estimado/a ${NAME} ${LASTNAME} nos complace comunicarle que la reserva de la actividad ${TITLE_ACTIVITY} " +
                "    del día ${DATE} a las ${HOUR} se cancelado por motivos justificados.</p><br><br>\n" +
                "<p>Disculpe las moléstias</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        content = content.replace("${TITLE_ACTIVITY}", titleActivity).replace("${NAME}", name)
                .replace("${LASTNAME}", lastname).replace("${HOUR}", hourAct).replace("${DATE}", dateAct);

        sendEmail("Cancelación de reserva", content, receiver);
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

}
