package controlador;


import modelo.datos.Persona;
import modelo.persistencia.BaseDatosImpl;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.openejb.jee.WebApp;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.naming.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

//@EnableServices(value = {"jaxrs"}, httpDebug = true)
//@RunWith(value = ApplicationComposer.class)
public class PersonaServiciosTest {
//    private static final String uriBase = "http://localhost:4204/test/personas/";
    private static final String uriBase = "http://localhost:8080/Agenda/personas/";

//    private static Context context;
//
//    @Module
//    @Classes(value = {PersonaServicios.class, PersonaDAO.class, BaseDatosImpl.class}, cdi = true)
//    public WebApp app() {
//        return new WebApp().contextRoot("test");
//    }

    @Test
    public void testCreaNuevaEntrada() {
        Persona persona = new Persona("Oscar", "Belmonte", "123");

        Response response = WebClient.create(uriBase)//, providers)
                .path("123")
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_XML)
                .put(persona);
        assertThat(response.getStatusInfo().getStatusCode(), is(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void testNuevaEntradaDesdeFormulario() {
        Form form = new Form("nombre", "MC")
                .param("apellidos", "Belmonte")
                .param("nif", "566");
        Response response = WebClient.create(uriBase)
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .form(form);
        assertThat(response.getStatusInfo().getStatusCode(), is(Response.Status.CREATED.getStatusCode()));
        response = WebClient.create(uriBase)
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .form(form);
        assertThat(response.getStatusInfo().getStatusCode(), is(Response.Status.CONFLICT.getStatusCode()));
    }
}