package ru.terra.dms.configuration.bean;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Map;

/**
 * Date: 02.06.14
 * Time: 13:36
 */
@XmlRootElement
public class Pojo implements Serializable {
    private String name, type, parent;
    private Map<String, String> fields;

    public Pojo() {
    }

    public Pojo(String name, String type, String parent, Map<String, String> fields) {
        this.name = name;
        this.type = type;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
