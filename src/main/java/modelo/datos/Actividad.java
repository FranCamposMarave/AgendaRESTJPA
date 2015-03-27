package modelo.datos;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by oscar on 27/11/14.
 */
@XmlRootElement
//@XmlType(propOrder = {"title", "description"})
@Entity
@NamedQueries({
        @NamedQuery(name="Actividad.encuentraTodas", query = "SELECT a FROM Actividad a"),
        @NamedQuery(name="Actividad.encuentraTodass", query = "SELECT a FROM Actividad a")
})
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long id;

    private String title;

    private String description;

    public Actividad() {
        super();
    }

    public Actividad(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
