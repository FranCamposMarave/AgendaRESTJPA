package model.dao;

import model.entities.Activity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

//@Stateless
public class ActivityJPA {

    public static Activity NULL = new Activity();

    @PersistenceContext(unitName = "naturAdventureJTA")
    EntityManager em;

    public Activity[] listAll() {
        TypedQuery<Activity> query = em.createNamedQuery("Activity.getAll", Activity.class);
        List<Activity> activitiesList = query.getResultList();
        Activity[] activities = new Activity[activitiesList.size()];
        activitiesList.toArray(activities);
        return activities;
    }

    public Activity get(long id) {
        TypedQuery<Activity> query = em.createNamedQuery("Activity.get", Activity.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return NULL;
        }
    }

}
