package model.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import model.entities.Activity;


@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name="Reservation.getAll", query = "SELECT r FROM Reservation r"),
        @NamedQuery(name="Reservation.get", query = "SELECT r FROM Reservation r WHERE r.id = :id"),
        @NamedQuery(name="Reservation.deleteById", query = "DELETE FROM Reservation r WHERE r.id = :id")
})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @XmlTransient
    private Long id;

    private String nif;

    private String name;

    private String lastName;

    private String email;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ACTIVITY_ID")
    private Activity activity;

    private int places;

    public Reservation() {
       super();
    }

    public Reservation(Long id, String nif, String name, String lastName, String email, Activity activity, int places) {
        this.id = id;
        this.nif = nif;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.activity = activity;
        this.places = places;
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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                ", id = "+ id +
                ", nif=" + nif +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", places='" + places + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation Reservation = (Reservation) o;

        if (!id.equals(Reservation.id)) return false;

        return true;
    }
}
