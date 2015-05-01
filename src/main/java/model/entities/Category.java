package model.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * Created by Fran on 14/04/2015.
 */
@XmlRootElement
@Entity
@Table( uniqueConstraints= {
        @UniqueConstraint(columnNames={"title"})
})
@NamedQueries({
        @NamedQuery(name="Category.getAll", query = "SELECT c FROM Category c"),
        @NamedQuery(name="Category.get", query = "SELECT c FROM Category c WHERE c.id = :id"),
        @NamedQuery(name="Category.getByTitle", query = "SELECT c FROM Category c WHERE c.title = :title"),
        @NamedQuery(name="Category.deleteById", query = "DELETE FROM Category c WHERE c.id = :id")
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @XmlTransient
    private Long id;

    private String title;

    public Category() {
        super();
    }

    public Category(Long id, String title) {
        this.id = id;
        this.title = title;
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

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}