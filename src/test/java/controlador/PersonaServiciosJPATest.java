package controlador;

import org.apache.openejb.OpenEjbContainer;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import java.util.Properties;

/**
 * Created by oscar on 31/01/15.
 */
public class PersonaServiciosJPATest {
    private static Context context;
    private static PersonaServicios servicios;

    @BeforeClass
    public static void setUp() {
        Properties properties = new Properties();
        properties.setProperty(OpenEjbContainer.OPENEJB_EMBEDDED_REMOTABLE, "true");
        context = EJBContainer.createEJBContainer(properties).getContext();
    }

    @Test
    public void primerTest() {

    }
}
