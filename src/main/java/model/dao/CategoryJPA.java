package model.dao;

import model.entities.Activity;
import model.entities.Category;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Fran on 15/04/2015.
 */
@Stateless
public class CategoryJPA {
    public static Category NULL = new Category();

    @PersistenceContext(unitName = "naturAdventureJTA")
    EntityManager em;

    public Category[] listAll() {
        TypedQuery<Category> query = em.createNamedQuery("Category.getAll", Category.class);
        List<Category> categoriesList = query.getResultList();
        Category[] categories = new Category[categoriesList.size()];
        categoriesList.toArray(categories);
        return categories;
    }

    public Category get(long id) {
        TypedQuery<Category> query = em.createNamedQuery("Category.get", Category.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return NULL;
        }
    }

    public boolean delete(long id) {
        TypedQuery<Category> query = em.createNamedQuery("Category.deleteById", Category.class);
        query.setParameter("id", id);
        try {
            int deletedRows = query.executeUpdate();
            return deletedRows == 1;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean add(Category category) {
        em.persist( category );
        return true;
    }

    public boolean update(Category category) {
        TypedQuery<Category> query = em.createNamedQuery("Category.get", Category.class);
        query.setParameter("id", category.getId());
        try {
            Category oldCategory = query.getSingleResult();
            oldCategory.setTitle( category.getTitle() );
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

}
