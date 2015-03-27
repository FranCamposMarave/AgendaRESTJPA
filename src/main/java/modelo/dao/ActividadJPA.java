package modelo.dao;

import modelo.datos.Actividad;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

//@Stateless
public class ActividadJPA {

    public static Actividad ENTRADA_NULL = new Actividad();

    @PersistenceContext(unitName = "actividadesJTA")
    EntityManager em;

    public Actividad[] listaTodasActividads() {
        System.out.println("Lista de actividades");
        TypedQuery<Actividad> query = em.createNamedQuery("Actividad.encuentraTodas", Actividad.class);
        System.out.println("Antes de consultar");
        List<Actividad> listaActividades = query.getResultList();

        for(Actividad a:listaActividades){
            System.out.println(a.getTitle());
        };

        Actividad[] actividades = new Actividad[listaActividades.size()];
        listaActividades.toArray(actividades);
        System.out.println("To Array");
        return actividades;
    }

}
