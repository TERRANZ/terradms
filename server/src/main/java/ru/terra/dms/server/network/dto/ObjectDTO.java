package ru.terra.dms.server.network.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Date: 02.06.14
 * Time: 12:44
 */
@XmlRootElement
public class ObjectDTO {
    public String name;
    public String type;
    public Long id;
    public List<Pair<String, Object>> fields;
}
