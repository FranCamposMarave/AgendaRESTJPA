package controller;

import model.dao.ActivityJPA;
import model.entities.Activity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("activities")
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
        Activity[] activities = activityDAO.listAll();
        return Response.ok(activities).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivity(@PathParam("id") long id ) {
        Activity activity = activityDAO.get( id );
        if (activity == activityDAO.NULL)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(activity).build();
    }

}