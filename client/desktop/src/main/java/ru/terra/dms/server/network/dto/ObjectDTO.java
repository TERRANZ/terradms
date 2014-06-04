package ru.terra.dms.server.network.dto;

import ru.terra.dms.desktop.dto.Pair;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 02.06.14
 * Time: 12:44
 */
@XmlRootElement
public class ObjectDTO {
    public String type;
    public Integer id;
    public List<Pair<String, Object>> fields;
}