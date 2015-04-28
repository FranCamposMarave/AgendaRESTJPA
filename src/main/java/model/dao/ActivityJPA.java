package model.dao;

import model.entities.Activity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
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

    public boolean delete(long id) {
        TypedQuery<Activity> query = em.createNamedQuery("Activity.deleteById", Activity.class);
        query.setParameter("id", id);
        try {
            int deletedRows = query.executeUpdate();
            return deletedRows == 1;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean add(Activity activity) {
        em.persist( activity );
        return true;
    }

    public boolean update(Activity activity) {
        TypedQuery<Activity> query = em.createNamedQuery("Activity.get", Activity.class);
        query.setParameter("id", activity.getId());
        try {
            Activity oldActivity = query.getSingleResult();
            oldActivity.setTitle( activity.getTitle() );
            oldActivity.setDescription( activity.getDescription() );
            oldActivity.setPlace( activity.getPlace() );
            oldActivity.setCategory( activity.getCategory() );
            oldActivity.setDate( activity.getDate() );
            oldActivity.setPicture( activity.getPicture() );
            oldActivity.setPrice( activity.getPrice() );
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

}
