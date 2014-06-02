package ru.terra.dms.configuration;


import ru.terra.dms.configuration.bean.MenuPart;
import ru.terra.dms.configuration.bean.ViewPart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 26.05.14
 * Time: 15:29
 */
public class Configuration implements Serializable {
    private String name, comment;
    private List<MenuPart> menus = new ArrayList<>();
    private List<ViewPart> viewParts = new ArrayList<>();

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


}
