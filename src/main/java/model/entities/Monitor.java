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
public class Monitor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long id;

    private String nif;

    private String name;

    private String lastName;

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

    public Monitor(Long id, String nif, String name, String lastName, Set<Category> categories) {
        this.id = id;
        this.nif = nif;
        this.name = name;
        this.lastName = lastName;
        this.categories = categories;
    }

    public String getNif() {
        return nif;
    }
    public void setNif(String nif) {this.nif = nif;}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() { return id;}
    public void setId(Long id) {this.id = id;}

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }


    @Override
    public String toString() {
        return "Monitor{" +
                "id = "+ id +
                "nif=" + nif +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Monitor monitor = (Monitor) o;

        if (!id.equals(monitor.id)) return false;

        return true;
    }
}
