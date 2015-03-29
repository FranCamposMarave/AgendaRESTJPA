package controller;

import model.dao.ActivityJPA;
import model.entities.Activity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
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
        if (activity == activityDAO.NULL){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(activity).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response deleteActivity(@PathParam("id") long id) {
        if ( activityDAO.delete(id) ){
            return Response.status(Response.Status.ACCEPTED).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateActivity(@PathParam("id") long id, Activity activity) {
        if( id != activity.getId() ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            if ( activityDAO.update( activity ) ){
                return Response.status(Response.Status.NO_CONTENT).build();
            }else {
                activityDAO.add( activity );
                return Response.ok( activity ).build();
            }
        }
    }

}