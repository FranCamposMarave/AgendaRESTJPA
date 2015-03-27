package controlador;

import modelo.dao.ActividadJPA;
import modelo.datos.Actividad;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


@Path("actividades")
@Stateless
public class ActividadServicios {

    @Inject
    ActividadJPA actividadDAO;


    public ActividadServicios() {
        super();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listaTodasActividads() {
        System.out.println("Recivida");
        Actividad[] actividades = actividadDAO.listaTodasActividads();
        System.out.println("ACTIVIDADES RECIVIDAS");
        //return Response.ok().build();
        return Response.ok(actividades).build();
    }

}