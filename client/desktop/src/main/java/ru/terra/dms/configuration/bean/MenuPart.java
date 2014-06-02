package ru.terra.dms.configuration.bean;

import java.io.Serializable;

/**
 * Date: 26.05.14
 * Time: 15:30
 */
public class MenuPart implements Serializable {
    public static enum MenuPartType {
        VIEWPART, SPLITTER
    }

    private String text, shortcut, viewPart;
    private MenuPartType type;

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
}



