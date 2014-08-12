package ru.terra.dms.configuration;

import ru.terra.dms.configuration.bean.MenuPart;
import ru.terra.dms.configuration.bean.Pojo;
import ru.terra.dms.configuration.bean.ViewPart;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @XmlRootElement Date: 26.05.14
 * Time: 15:29
 */
@XmlRootElement
public class Configuration implements Serializable {
    private String name, comment;
    private List<MenuPart> menus = new ArrayList<>();
    private List<ViewPart> viewParts = new ArrayList<>();
    private List<Pojo> pojos = new ArrayList<>();

    public Configuration() {
    }

    public List<MenuPart> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuPart> menus) {
        this.menus = menus;
    }

    public List<ViewPart> getViewParts() {
        return viewParts;
    }

    public void setViewParts(List<ViewPart> viewParts) {
        this.viewParts = viewParts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Pojo> getPojos() {
        return pojos;
    }

    public void setPojos(List<Pojo> pojos) {
        this.pojos = pojos;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", menus=" + menus +
                ", viewParts=" + viewParts +
                '}';
    }

    public ViewPart getViewPart(String name) {
        for (ViewPart viewPart : viewParts)
            if (viewPart.getName().equals(name))
                return viewPart;
        return null;
    }

    public Pojo getPojo(String type) {
        for (Pojo pojo : pojos)
            if (pojo.getName().equals(type))
                return pojo;
        return null;
    }
}
