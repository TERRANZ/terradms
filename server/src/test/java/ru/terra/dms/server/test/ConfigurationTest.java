package ru.terra.dms.server.test;

import junit.framework.TestCase;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import ru.terra.dms.configuration.Configuration;
import ru.terra.dms.configuration.bean.MenuPart;
import ru.terra.dms.configuration.bean.Pojo;
import ru.terra.dms.configuration.bean.ViewPart;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Date: 10.06.14
 * Time: 11:52
 */
public class ConfigurationTest extends TestCase {

    @Test
    public void test1() throws IOException {
        Configuration configuration = new Configuration();

        String viewPart1Name = "View part 1";
        ViewPart viewPart1 = new ViewPart();
        viewPart1.setId(UUID.randomUUID().toString());
        viewPart1.setName(viewPart1Name);
        viewPart1.setControllerType("simpletable");

        Pojo viewPart1Pojo = new Pojo();
        viewPart1Pojo.setName("View part pojo 1");
        viewPart1Pojo.setType("products");
        Map<String, String> pojo1Fields = new HashMap<>();
        pojo1Fields.put("id", "Long");
        pojo1Fields.put("name", "String");
        pojo1Fields.put("price", "Integer");
        viewPart1Pojo.setFields(pojo1Fields);
        viewPart1.setPojo(viewPart1Pojo);

        configuration.setViewParts(Arrays.asList(viewPart1));

        MenuPart menuPart1 = new MenuPart();
        menuPart1.setShortcut("ctrl+1");
        menuPart1.setText("bean 1 table");
        menuPart1.setType(MenuPart.MenuPartType.VIEWPART);
        menuPart1.setViewPart(viewPart1Name);

        configuration.setMenus(Arrays.asList(menuPart1));
        configuration.setComment("comment 1");
        configuration.setName("configuration name 1");

        new ObjectMapper().writeValue(new File("configuration.json"), configuration);
    }
}
