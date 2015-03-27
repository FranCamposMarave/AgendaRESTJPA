package modelo.dao;

import modelo.datos.Persona;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by oscar on 30/01/15.
 */
@Stateless
public class PersonaJPA {
    public static Persona ENTRADA_NULL = new Persona();
    @PersistenceContext(unitName = "personasJTA")
//    @PersistenceContext(unitName = "personasJTADerby")
    EntityManager em;

    public void nuevaPersona(Persona persona) {
        em.persist(persona);
    }

    public Persona buscaPersonaPorNIF(String nif) {
        TypedQuery<Persona> query = em.createNamedQuery("Persona.encuentraPorNif", Persona.class);
        query.setParameter("nif", nif);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return ENTRADA_NULL;
        }
    }

    public Persona[] listaTodasPersonas() {
        TypedQuery<Persona> query = em.createNamedQuery("Persona.encuentraTodas", Persona.class);
        List<Persona> listaPersonas = query.getResultList();
        Persona[] personas = new Persona[listaPersonas.size()];
        listaPersonas.toArray(personas);
        return personas;
    }

    public boolean creaNuevaEntrada(Persona persona) {
        em.persist(persona);
        return true;
    }

    public boolean actualizaPersona(Persona persona) {
        TypedQuery<Persona> query = em.createNamedQuery("Persona.encuentraPorNif", Persona.class);
        query.setParameter("nif", persona.getNif());
        try {
            Persona personaBBDD = query.getSingleResult();
            personaBBDD.setNombre(persona.getNombre());
            personaBBDD.setApellidos(persona.getApellidos());
            personaBBDD.setNif(persona.getNif());
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean borraPersona(String nif) {
        TypedQuery<Persona> query = em.createNamedQuery("Persona.borraPorNif", Persona.class);
        query.setParameter("nif", nif);
        try {
            int deletedRows = query.executeUpdate();
            if(deletedRows == 1) return true;
            else return false;
        } catch (NoResultException e) {
            return false;
        }
    }
}
