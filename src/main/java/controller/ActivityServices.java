package controller;

import modelo.dao.ActivityJPA;
import modelo.datos.Activity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("services")
@Stateless
public class ActivityServices {

    @Inject
    ActivityJPA activityDAO;


    public ActivityServices() {
        super();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllActivities() {
        Activity[] actividades = activityDAO.listAll();
        return Response.ok(actividades).build();
    }

}