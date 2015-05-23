package model.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity

@DiscriminatorValue("ADMIN")
public class Admin2 extends User {


    public Admin2() {
        super();
    }

    public Admin2(Long id, String userName, String password, int permission, String nif, String name, String lastName) {
        super(id, userName, password, permission, nif, name, lastName);
        Admin2 a = new Admin2();
    }

    public void nono(){

    }

}
