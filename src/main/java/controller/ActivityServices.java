package controller;

import controller.validators.TokenValidator;
import controller.validators.Validator;
import model.dao.ActivityJPA;
import model.entities.Activity;
import services.ImageUploaderService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


@Path("activities")
@Stateless
public class ActivityServices {

    @Inject
    private ActivityJPA activityDAO;

    @Inject
    private Validator<Activity> validatorActivity;

    @Inject
    private TokenValidator tokenValidator;

    @Context
    private UriInfo uriInfo;

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
    @Path("/category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllActivitiesByCategory(@PathParam("category") String category) {
        Activity[] activities = activityDAO.listAllByCategory(category);
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

    @POST
    @Path("/a/{token}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addActivity(@PathParam("token") String token, Activity activity) {

        Response r = tokenValidator.validate(token,Permissions.ADD_ACTIVITY);
        if(r != null) {
            return r;
        }

        if ( !validatorActivity.validate( activity) ){
            return Response.status( Response.Status.FORBIDDEN ).build();  //403  -> Datos Incorrectos
        }
        Activity existent = activityDAO.getByTitleDateCategory(activity.getTitle(), activity.getDate(), activity.getCategory());
        if ( existent != activityDAO.NULL){
            return Response.status( Response.Status.CONFLICT ).build(); //409  -> Actividad ya existe
        }

        System.out.println(activity);

        activityDAO.add( activity );
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path( activity.getId() + "" ).build();
        return Response.created(uri).entity( activity ).build();
    }

    @DELETE
    @Path("/{id}/{token}")
    @Produces("application/json")
    public Response deleteActivity(@PathParam("id") long id, @PathParam("token") String token) {

        Response r = tokenValidator.validate(token,Permissions.DELETE_ACTIVITY);
        if(r != null) {
            return r;
        }

        Activity activity = activityDAO.get(id);
        if (activityDAO.delete(id)) {
            ImageUploaderService service = new ImageUploaderService();
            service.deleteImage(activity.getPicture());
            return Response.status(Response.Status.ACCEPTED).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}/{token}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateActivity(@PathParam("id") long id, @PathParam("token") String token, Activity activity) {

        Response r = tokenValidator.validate(token, Permissions.UPDATE_ACTIVITY);
        if(r != null) {
            return r;
        }

        //System.out.println(user.getROLE());
        String oldPicture = activityDAO.get(id).getPicture();
        if( id != activity.getId() ) {
            return Response.status(Response.Status.BAD_REQUEST).build();  //400 -> Error en conexión
        }
        else {
            if ( !validatorActivity.validate(activity)){
                return Response.status( Response.Status.FORBIDDEN ).build(); //403  -> Datos Incorrectos
            }
            Activity existent = activityDAO.getByTitleDateCategory(activity.getTitle(), activity.getDate(), activity.getCategory());
            if (existent != ActivityJPA.NULL && ! existent.equals(activity)){
                return Response.status( Response.Status.CONFLICT ).build();  //409  -> Actividad ya existe
            }
            if ( activityDAO.update( activity ) ){
                if (!(oldPicture==null) && !(activity.getPicture().equals(null)) && !(oldPicture.equals(activity.getPicture()))){
                    ImageUploaderService service = new ImageUploaderService();
                    service.deleteImage(oldPicture);
                }
                return Response.status(Response.Status.NO_CONTENT).build();  //401 -> Error en conexión
            }else {
                activityDAO.add( activity );
                return Response.ok( activity ).build();
            }
        }
    }
    /*
    else {
            if ( !validatorMonitor.validate( monitor) ){
                return Response.status( Response.Status.FORBIDDEN ).build(); //403  -> Datos Incorrectos
            }
            Monitor existent = monitorDAO.getByNif(monitor.getNif());
            if ( existent != MonitorJPA.NULL && ! existent.equals(monitor) ){
                return Response.status( Response.Status.CONFLICT ).build();  //409  -> Nif ya existe
            }
            if ( monitorDAO.update( monitor ) ){
                return Response.status(Response.Status.NO_CONTENT).build(); //401 -> Error en conexión
            }else {
                monitorDAO.add( monitor );
                return Response.ok( monitor ).build();
            }
        }
     */

    @POST
    @Path("/image")
    public Response uploadImageJPG(InputStream image) throws IOException {
        ImageUploaderService service = new ImageUploaderService();
        String imageName = service.uploadImage( image, "png" );

        if ( imageName != null ){
            return Response.ok( imageName ).build();
        }else{
            return Response.serverError().build();
        }
    }

}