package model.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@XmlRootElement
@Entity
@Table( uniqueConstraints={
        @UniqueConstraint( columnNames={"title", "date", "category"} )
})
@NamedQueries({
        @NamedQuery(name="Activity.getAll", query = "SELECT a FROM Activity a"),
        @NamedQuery(name="Activity.getAllByCategory", query = "SELECT a FROM Activity a JOIN a.category c WHERE c.title = :category"),
        @NamedQuery(name="Activity.get", query = "SELECT a FROM Activity a WHERE a.id = :id"),
        @NamedQuery(name="Activity.getByTitleDateCategory", query = "SELECT a FROM Activity a WHERE a.title = :title and a.date = :date and a.category = :category"),
        //@NamedQuery(name="Activity.getByTitleDateCategory", query = "SELECT a FROM Activity a WHERE a.title = :title"),
        @NamedQuery(name="Activity.deleteById", query = "DELETE FROM Activity a WHERE a.id = :id")

})
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @XmlTransient
    private Long id;

    private String title;

    private String description;

    private String place;

    private Category category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private float price;

    private int totalPlaces;

    private int remainingPlaces;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MONITOR_ID")
    private Monitor monitor;

    private String picture;

    public Activity() {
        super();
    }

    public Activity(Long id, String title, String description, String place, Category category, Date date, float price, int totalPlaces, int remainingPlaces, Monitor monitor, String picture) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.place = place;
        this.category =category;
        this.price = price;
        this.totalPlaces = totalPlaces;
        this.remainingPlaces = remainingPlaces;
        this.monitor=monitor;
        this.picture = picture;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getDate() {
        return date;
    }

    public Category getCategory(){
        return category;
    }

    public void setCategory(Category category){
        this.category =category;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public void setTotalPlaces(int totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

    public int getRemainingPlaces() {
        return remainingPlaces;
    }

    public void setRemainingPlaces(int remainingPlaces) {
        this.remainingPlaces = remainingPlaces;
    }

    public Monitor getMonitor() { return monitor; }

    public void setMonitor(Monitor monitor) { this.monitor=monitor; }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", place='" + place + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", totalPlaces=" + totalPlaces +
                ", remainingPlaces=" + remainingPlaces +
                ", monitor=" + monitor +
                ", picture='" + picture + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (id != null ? !id.equals(activity.id) : activity.id != null) return false;

        return true;
    }

}
