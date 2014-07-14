package ru.terra.dms.client.rest;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for menuPartType.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="menuPartType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VIEWPART"/>
 *     &lt;enumeration value="SPLITTER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "menuPartType")
@XmlEnum
public enum MenuPartType {

    VIEWPART,
    SPLITTER;

    public String value() {
        return name();
    }

    public static MenuPartType fromValue(String v) {
        return valueOf(v);
    }

}
