package controller;


import controller.validators.MonitorValidator;
import controller.validators.Validator;
import model.dao.MonitorJPA;
import model.entities.Monitor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;


@Path("monitors")
@Stateless
public class MonitorServices {

    @Inject
    private MonitorJPA monitorDAO;

    @Inject
    private Validator<Monitor> validatorMonitor;

    @Context
    private UriInfo uriInfo;


    public MonitorServices() {
        super();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllMonitors() {
        Monitor[] monitors = monitorDAO.listAll();
        return Response.ok(monitors).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMonitor(@PathParam("id") Long id ) {
        Monitor monitor = monitorDAO.get( id );
        if (monitor == monitorDAO.NULL){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(monitor).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMonitor(Monitor monitor) {

        if ( !validatorMonitor.validate( monitor) ){
            return Response.status( Response.Status.FORBIDDEN ).build();
        }

        if ( monitorDAO.getByNif(monitor.getNif()) != MonitorJPA.NULL ){
            return Response.status( Response.Status.CONFLICT ).build();
        }

        monitorDAO.add(monitor);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path( monitor.getId() + "" ).build();
        return Response.created(uri).entity( monitor ).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response deleteMonitors(@PathParam("id") Long id) {
        if (monitorDAO.delete(id)) {
            return Response.status(Response.Status.ACCEPTED).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMonitors(@PathParam("id") Long id, Monitor monitor) {
        if( !id.equals(monitor.getId())  ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            if ( !validatorMonitor.validate( monitor) ){
                return Response.status( Response.Status.FORBIDDEN ).build();
            }
            Monitor existent = monitorDAO.getByNif(monitor.getNif());
            if ( existent != MonitorJPA.NULL && ! existent.equals(monitor) ){
                return Response.status( Response.Status.CONFLICT ).build();
            }
            if ( monitorDAO.update( monitor ) ){
                return Response.status(Response.Status.NO_CONTENT).build();
            }else {
                monitorDAO.add( monitor );
                return Response.ok( monitor ).build();
            }
        }
    }


}