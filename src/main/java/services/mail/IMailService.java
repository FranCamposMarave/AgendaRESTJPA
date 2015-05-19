/**
 * Created by cuatroochenta on 5/5/15.
 */
package services.mail;


import model.entities.Reservation;

public interface IMailService {

    public void sendInfoEmailReservationToUser( Reservation reservation );

    public void sendInfoEmailReservationCancel( Reservation reservation );

}