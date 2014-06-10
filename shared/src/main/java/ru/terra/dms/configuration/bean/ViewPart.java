package ru.terra.dms.configuration.bean;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Date: 26.05.14
 * Time: 15:33
 */
@XmlRootElement
public class ViewPart implements Serializable {
    private String id, controllerType, name;
    private Pojo pojo;

    public ViewPart() {
    }

    public ViewPart(String id, String controllerType, String name, Pojo pojo) {
        this.id = id;
        this.controllerType = controllerType;
        this.name = name;
        this.pojo = pojo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getControllerType() {
        return controllerType;
    }

    public void setControllerType(String controllerType) {
        this.controllerType = controllerType;
    }

    public Pojo getPojo() {
        return pojo;
    }

    public void setPojo(Pojo pojo) {
        this.pojo = pojo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
