package ru.terra.dms.server.db.entity;

import ru.terra.server.db.entity.AbstractUser;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Date: 27.05.14
 * Time: 12:37
 */
@XmlRootElement
public class User extends AbstractUser implements Serializable {
    @Id
    private Integer id;
    private String pass;

    public User() {
        super(0, 0, "");
    }

    public User(Integer id, int level, String name) {
        super(id, level, name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
