package controller;

import controller.validators.Validator;
import model.dao.UserJPA;
import model.entities.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("users")
@Stateless
public class UserServices {

    @Inject
    private UserJPA userDAO;

    //@Inject
    //private Validator<User> validatorUser;

    @Context
    private UriInfo uriInfo;

    public UserServices() {
        super();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllUsers() {
        User[] users = userDAO.listAll();
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id ) {
        User user = userDAO.get( id );
        if (user == userDAO.NULL){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        System.out.println(user.toString());

        /*
        if ( !validatorUser.validate( user) ){
            return Response.status( Response.Status.FORBIDDEN ).build();
        }
        */
        if ( userDAO.get(user.getId()) != UserJPA.NULL ){
            return Response.status( Response.Status.CONFLICT ).build();
        }
        userDAO.add(user);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path( user.getId() + "" ).build();
        return Response.created(uri).entity( user ).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response deleteUsers(@PathParam("id") Long id) {
        if (userDAO.delete(id)) {
            return Response.status(Response.Status.ACCEPTED).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUsers(@PathParam("id") Long id, User user) {
        if( !id.equals(user.getId())  ) {
            return Response.status(Response.Status.BAD_REQUEST).build(); //400 -> Error en conexión
        }
        else {
            /*
            if ( !validatorUser.validate( user) ){
                return Response.status( Response.Status.FORBIDDEN ).build(); //403  -> Datos Incorrectos
            }*/
            User existent = userDAO.getByUserName(user.getUserName());
            if ( existent != UserJPA.NULL && ! existent.equals(user) ){
                return Response.status( Response.Status.CONFLICT ).build();  //409  -> Nif ya existe
            }
            if ( userDAO.update( user ) ){
                return Response.status(Response.Status.NO_CONTENT).build(); //401 -> Error en conexión
            }else {
                userDAO.add( user );
                return Response.ok( user ).build();
            }
        }
    }




}