package model.dao;

import model.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserJPA {

    public static User NULL = new User();

    @PersistenceContext(unitName = "naturAdventureJTA")
    EntityManager em;

    public User[] listAll() {
        TypedQuery<User> query = em.createNamedQuery("User.getAll", User.class);
        List<User> usersList = query.getResultList();
        User[] users = new User[usersList.size()];
        usersList.toArray(users);
        return users;
    }

    public User get(Long id) {
        TypedQuery<User> query = em.createNamedQuery("User.get", User.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return NULL;
        }
    }

    public User getByUserName(String userName) {
        TypedQuery<User> query = em.createNamedQuery("User.getByUserName", User.class);
        query.setParameter("userName", userName);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return NULL;
        }
    }

    public boolean delete(Long id) {
        TypedQuery<User> query = em.createNamedQuery("User.delete", User.class);
        query.setParameter("id", id);
        try {
            int deletedRows = query.executeUpdate();
            return deletedRows == 1;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean add(User user) {
        em.persist( user );
        return true;
    }

    public boolean update(User user) {
        TypedQuery<User> query = em.createNamedQuery("User.get", User.class);
        query.setParameter("id", user.getId());
        try {
            User oldUser = query.getSingleResult();
            oldUser.setPassword(user.getPassword());
            oldUser.setUserName(user.getUserName());
            oldUser.setPermission(user.getPermission());
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
