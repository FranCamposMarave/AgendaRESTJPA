package modelo.datos;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by oscar on 27/11/14.
 */
@XmlRootElement
@XmlType(propOrder = {"nombre", "apellidos", "nif"})
@Entity
@NamedQueries({
        @NamedQuery(name="Persona.encuentraTodas", query = "SELECT p FROM Persona p"),
        @NamedQuery(name = "Persona.encuentraPorNif", query = "SELECT p FROM Persona p WHERE p.nif = :nif"),
        @NamedQuery(name="Persona.borraPorNif", query = "DELETE FROM Persona p WHERE p.nif = :nif")
})
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long id;
    private String nombre;
    private String apellidos;
    private String nif;

    public Persona() {
        super();
    }

    public Persona(String nombre, String apellidos, String nif) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}
