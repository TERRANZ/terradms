
package ru.terra.dms.client.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for menuPart complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="menuPart">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shortcut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{}menuPartType" minOccurs="0"/>
 *         &lt;element name="viewPart" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "menuPart", propOrder = {
    "shortcut",
    "text",
    "type",
    "viewPart"
})
public class MenuPart {

    protected String shortcut;
    protected String text;
    protected MenuPartType type;
    protected String viewPart;

    /**
     * Gets the value of the shortcut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortcut() {
        return shortcut;
    }

    /**
     * Sets the value of the shortcut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortcut(String value) {
        this.shortcut = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link MenuPartType }
     *     
     */
    public MenuPartType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link MenuPartType }
     *     
     */
    public void setType(MenuPartType value) {
        this.type = value;
    }

    /**
     * Gets the value of the viewPart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViewPart() {
        return viewPart;
    }

    /**
     * Sets the value of the viewPart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViewPart(String value) {
        this.viewPart = value;
    }

}
