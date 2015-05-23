package model.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity

@DiscriminatorValue("ADMIN")
public class Administrator extends User {


    public Administrator() {
        super();
    }

    public Administrator(Long id, String userName, String password, int permission, String nif, String name, String lastName) {
        super(id, userName, password, permission, nif, name, lastName);
        Administrator a = new Administrator();
    }

    public void nono(){

    }

}
