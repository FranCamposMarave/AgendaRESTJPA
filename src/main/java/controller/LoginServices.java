package controller;

import model.dao.UserJPA;
import model.entities.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("login")
@Stateless
public class LoginServices {

    @Inject
    private UserJPA userDAO;

    @Context
    private UriInfo uriInfo;

    public LoginServices() {
        super();
    }


    @PUT
    @Path("/")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {

        User existent = userDAO.get(user.getUserName(),user.getPassword());
        System.out.println("=== LOGIN USER=="+user.getUserName()+",,"+user.getPassword());
        System.out.println("=== LOGIN USER=="+ existent.toString());
        if ( existent != UserJPA.NULL && ! existent.equals(user) ){

            int randomNum = 1 + (int) (Math.random() * 100);

            existent.setToken(randomNum+""+existent.getId());

            if ( userDAO.update( existent ) ){
                return Response.ok( existent.getToken()).build();
            }else {
                return Response.status(Response.Status.NO_CONTENT).build(); //401 -> Error en conexión
            }
        }

        return Response.status(Response.Status.FORBIDDEN).build();


    }





}