package controlador.utilidades;

import es.uji.www.GeneradorDatosINE;
import modelo.dao.PersonaJPA;
import modelo.datos.Persona;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by oscar on 30/11/14.
 */
@Path("crea")
@Stateless
public class CreaPersonas {
    private GeneradorDatosINE generador = new GeneradorDatosINE();
    @Inject
//    private BaseDatos  bd;
    private PersonaJPA personaDAO;

    @POST
    @Path("{cuantas}")
    public Response creaPersonas(@PathParam("cuantas") int cuantas) {
        Persona[] personas = new Persona[cuantas];
        Persona persona = new Persona("Oscar", "Belmonte", "123X");
        personaDAO.creaNuevaEntrada(persona);
        personas[0] = persona;
        for(int i = 1; i < cuantas; i++) {
            persona = new Persona();
            persona.setNombre(generador.getNombre());
            persona.setApellidos(generador.getApellido());
            persona.setNif(generador.getNIF());
//            bd.nuevaPersona(persona);
            personaDAO.creaNuevaEntrada(persona);
            personas[i] = persona;
        }
        Response response = Response.ok(personas).build();
        return response;
    }
}
