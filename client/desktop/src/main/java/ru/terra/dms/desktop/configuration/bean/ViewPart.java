package ru.terra.dms.desktop.configuration.bean;

import java.io.Serializable;

/**
 * Date: 26.05.14
 * Time: 15:33
 */
public class ViewPart implements Serializable {
    private String id, controllerType, name;
    private Pojo pojo;

    public ViewPart() {
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
