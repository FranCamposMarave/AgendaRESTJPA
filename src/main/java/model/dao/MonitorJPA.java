package model.dao;

import model.entities.Monitor;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by david on 6/4/15.
 */
public class MonitorJPA {

    public static Monitor NULL = new Monitor();

    @PersistenceContext(unitName = "naturAdventureJTA")
    EntityManager em;

    public Monitor[] listAll() {
        TypedQuery<Monitor> query = em.createNamedQuery("Monitor.getAll", Monitor.class);
        List<Monitor> monitorsList = query.getResultList();
        Monitor[] monitors = new Monitor[monitorsList.size()];
        monitorsList.toArray(monitors);
        return monitors;
    }

    public Monitor get(Long id) {
        TypedQuery<Monitor> query = em.createNamedQuery("Monitor.get", Monitor.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return NULL;
        }
    }

    public Monitor getByNif(String nif) {
        TypedQuery<Monitor> query = em.createNamedQuery("Monitor.getByNif", Monitor.class);
        query.setParameter("nif", nif);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return NULL;
        }
    }

    public boolean delete(Long id) {
        TypedQuery<Monitor> query = em.createNamedQuery("Monitor.deleteByNif", Monitor.class);
        query.setParameter("id", id);
        try {
            int deletedRows = query.executeUpdate();
            return deletedRows == 1;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean add(Monitor monitor) {
        em.persist( monitor );
        return true;
    }

    public boolean update(Monitor monitor) {
        TypedQuery<Monitor> query = em.createNamedQuery("Monitor.get", Monitor.class);
        query.setParameter("id", monitor.getId());
        try {
            Monitor oldMonitor = query.getSingleResult();
            oldMonitor.setPassword(monitor.getPassword());
            oldMonitor.setUserName(monitor.getUserName());
            oldMonitor.setPermission(monitor.getPermission());
            oldMonitor.setNif(monitor.getNif());
            oldMonitor.setName(monitor.getName());
            oldMonitor.setLastName(monitor.getLastName());
            oldMonitor.setPhone(monitor.getPhone());
            
            oldMonitor.setCategories( monitor.getCategories() );
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
