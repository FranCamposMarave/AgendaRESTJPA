package controller;

import model.dao.CategoryJPA;
import model.entities.Category;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

/**
 * Created by Fran on 15/04/2015.
 */
@Stateless
public class CategoryServices {
    @Inject
    CategoryJPA categoryDAO;


    @Context
    private UriInfo uriInfo;


    public CategoryServices() {
        super();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllCategories() {
        Category[] categories = categoryDAO.listAll();
        return Response.ok(categories).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("id") long id ) {
        Category category = categoryDAO.get( id );
        if (category == categoryDAO.NULL){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(category).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(Category category) {

        //System.out.println(category);

        categoryDAO.add( category );
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path( category.getId() + "" ).build();
        return Response.created(uri).entity( category ).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response deleteActivity(@PathParam("id") long id) {
        Category category = categoryDAO.get(id);
        if (categoryDAO.delete(id)) {
            return Response.status(Response.Status.ACCEPTED).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("id") long id, Category category) {
        if( id != category.getId() ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            if ( categoryDAO.update( category ) ){
                return Response.status(Response.Status.NO_CONTENT).build();
            }else {
                categoryDAO.add( category );
                return Response.ok( category ).build();
            }
        }
    }
}