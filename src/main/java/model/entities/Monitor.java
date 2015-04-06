package model.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * Created by oscar on 27/11/14.
 */
@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name="Monitor.getAll", query = "SELECT m FROM Monitor m"),
        @NamedQuery(name="Monitor.get", query = "SELECT m FROM Monitor m WHERE m.id = :id"),
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


    public Monitor() {
        super();
    }

    public Monitor(String nif, String name, String lastName) {
        this.nif = nif;
        this.name = name;
        this.lastName = lastName;
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


    @Override
    public String toString() {
        return "Monitor{" +
                "id = "+ id +
                "nif=" + nif +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }


}
