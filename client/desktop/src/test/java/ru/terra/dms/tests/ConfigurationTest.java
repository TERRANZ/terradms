package ru.terra.dms.tests;

import junit.framework.TestCase;
import ru.terra.dms.configuration.Configuration;
import ru.terra.dms.configuration.bean.MenuPart;
import ru.terra.dms.configuration.bean.Pojo;
import ru.terra.dms.configuration.bean.ViewPart;
import ru.terra.dms.configuration.parser.JSONConfigurationReader;
import ru.terra.dms.configuration.parser.JSONConfigurationWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Date: 02.06.14
 * Time: 13:47
 */
public class ConfigurationTest extends TestCase {
    public ConfigurationTest() {
        super("Configuration read write test");
    }

    public void test1() throws IOException {
        Configuration configuration = new Configuration();

        String viewPart1Name = "View part 1";
        ViewPart viewPart1 = new ViewPart();
        viewPart1.setId(UUID.randomUUID().toString());
        viewPart1.setName(viewPart1Name);
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

        JSONConfigurationWriter.write("configuration.json", configuration);
        JSONConfigurationReader.load("configuration.json");
    }

    public void test2() {
    }
}
