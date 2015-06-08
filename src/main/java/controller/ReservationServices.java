package controller;

import controller.validators.Validator;
import model.dao.ActivityJPA;
import model.dao.ReservationJPA;
import model.entities.Reservation;
import services.mail.IMailService;
import services.mail.MailService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;


@Path("reservations")
@Stateless
public class ReservationServices {


    @Inject
    private ReservationJPA reservationDAO;

    @Inject
    private Validator<Reservation> validatorReservation;

    @Context
    private UriInfo uriInfo;


    public ReservationServices() {
        super();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllReservations() {
        Reservation[] reservations = reservationDAO.listAll();
        return Response.ok(reservations).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservation(@PathParam("id") Long id ) {
        Reservation reservation = reservationDAO.get( id );
        if (reservation == reservationDAO.NULL){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(reservation).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReservation(Reservation reservation) {

        if ( !validatorReservation.validate( reservation) ){
            return Response.status( Response.Status.FORBIDDEN ).build();
        }

        if ( reservationDAO.get(reservation.getId()) != ReservationJPA.NULL ){
            return Response.status( Response.Status.CONFLICT ).build();
        }

        reservationDAO.add(reservation);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path( reservation.getId() + "" ).build();
        return Response.created(uri).entity( reservation ).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response deleteReservations(@PathParam("id") Long id) {
        Reservation reservation = reservationDAO.get( id );
        if (reservationDAO.delete(id)) {
            IMailService mailService = new MailService();
            mailService.sendInfoEmailReservationCancel( reservation );
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAÆ\nAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            return Response.status(Response.Status.ACCEPTED).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReservations(@PathParam("id") Long id, Reservation reservation) {
        if( !id.equals(reservation.getId())  ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            if ( !validatorReservation.validate( reservation) ){
                return Response.status( Response.Status.FORBIDDEN ).build();
            }
            Reservation existent = reservationDAO.get(reservation.getId());
            if ( existent != ReservationJPA.NULL && ! existent.equals(reservation) ){
                return Response.status( Response.Status.CONFLICT ).build();
            }
            if ( reservationDAO.update( reservation ) ){
                //Enviar emilio de confirmación de reserva realizada
                IMailService mailService = new MailService();
                mailService.sendInfoEmailReservationToUser( reservation );
                return Response.status(Response.Status.NO_CONTENT).build();
            }else {
                reservationDAO.add( reservation );
                return Response.ok( reservation ).build();
            }
        }
    }


}