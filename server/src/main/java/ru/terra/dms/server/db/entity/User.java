package ru.terra.dms.server.db.entity;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Date: 27.05.14
 * Time: 12:37
 */
@XmlRootElement
public class User implements Serializable {
    @Id
    private Integer id;
    private String name;
    private Integer level;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
