package ru.terra.dms.configuration.bean;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.UUID;

/**
 * Date: 26.05.14
 * Time: 15:30
 */
@XmlRootElement
public class MenuPart implements Serializable {
    public static enum MenuPartType {
        VIEWPART, SPLITTER
    }

    private String text, shortcut, viewPart, uid = UUID.randomUUID().toString();
    private MenuPartType type;

    public MenuPart(String text, String shortcut, String viewPart, MenuPartType type) {
        this.text = text;
        this.shortcut = shortcut;
        this.viewPart = viewPart;
        this.type = type;
    }

    public MenuPart() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getViewPart() {
        return viewPart;
    }

    public void setViewPart(String viewPart) {
        this.viewPart = viewPart;

    }

    public MenuPartType getType() {
        return type;
    }

    public void setType(MenuPartType type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "MenuPart{" +
                "text='" + text + '\'' +
                ", shortcut='" + shortcut + '\'' +
                ", viewPart='" + viewPart + '\'' +
                ", uid='" + uid + '\'' +
                ", type=" + type +
                '}';
    }
}



