package ru.terra.dms.client.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for viewPart complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="viewPart">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="controllerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{}pojo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "viewPart", propOrder = {
        "controllerType",
        "id",
        "name",
        "pojo"
})
public class ViewPart {

    protected String controllerType;
    protected String id;
    protected String name;
    protected Pojo pojo;

    /**
     * Gets the value of the controllerType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getControllerType() {
        return controllerType;
    }

    /**
     * Sets the value of the controllerType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setControllerType(String value) {
        this.controllerType = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the pojo property.
     *
     * @return possible object is
     * {@link Pojo }
     */
    public Pojo getPojo() {
        return pojo;
    }

    /**
     * Sets the value of the pojo property.
     *
     * @param value allowed object is
     *              {@link Pojo }
     */
    public void setPojo(Pojo value) {
        this.pojo = value;
    }

}
