package model.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.Set;

@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name="Monitor.getAll", query = "SELECT m FROM Monitor m"),
        @NamedQuery(name="Monitor.get", query = "SELECT m FROM Monitor m WHERE m.id = :id"),
        @NamedQuery(name="Monitor.getByNif", query = "SELECT m FROM Monitor m WHERE m.nif = :nif"),
        @NamedQuery(name="Monitor.deleteByNif", query = "DELETE FROM Monitor m WHERE m.id = :id")
})

@DiscriminatorValue("MONITOR")
public class Monitor extends User {

    @OneToMany
    @JoinTable
    (name="MONITOR_CATEGORY",
     joinColumns={ @JoinColumn(name="monitor_id", referencedColumnName="ID") },
     inverseJoinColumns={ @JoinColumn(name="category_id", referencedColumnName="ID", unique=false) }
    )
    private Set<Category> categories;

    public Monitor() {
        super();
    }

    public Monitor(Long id, String userName, String password, int permission, String nif, String name, String lastName, Set<Category> categories) {
        super(id, userName, password, permission, nif, name, lastName);
        this.categories = categories;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
