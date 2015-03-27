package model.dao;

import model.datos.Activity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

//@Stateless
public class ActivityJPA {

    public static Activity NULL = new Activity();

    @PersistenceContext(unitName = "actividadesJTA")
    EntityManager em;

    public Activity[] listAll() {
        TypedQuery<Activity> query = em.createNamedQuery("Activity.getAll", Activity.class);
        List<Activity> listaActividades = query.getResultList();
        Activity[] actividades = new Activity[listaActividades.size()];
        listaActividades.toArray(actividades);
        return actividades;
    }

}
