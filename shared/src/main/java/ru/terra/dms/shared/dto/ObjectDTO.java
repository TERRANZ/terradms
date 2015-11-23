package ru.terra.dms.shared.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * Date: 02.06.14
 * Time: 12:44
 */
@XmlRootElement
public class ObjectDTO {
    public String type;
    public Integer id;
    public Map<String, String> fields;
    public Integer parent;
    public Integer version;
    public Long created;
    public Long updated;
}
