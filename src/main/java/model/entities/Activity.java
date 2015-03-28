package model.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by oscar on 27/11/14.
 */
@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name="Activity.getAll", query = "SELECT a FROM Activity a"),
        @NamedQuery(name="Activity.get", query = "SELECT a FROM Activity a WHERE a.id = :id"),
})
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long id;

    private String title;

    private String description;

    public Activity() {
        super();
    }

    public Activity(String title, String description) {
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
