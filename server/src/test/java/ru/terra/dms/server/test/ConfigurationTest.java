package ru.terra.dms.server.test;

import junit.framework.TestCase;
import org.junit.Test;
import ru.terra.dms.configuration.Configuration;
import ru.terra.dms.configuration.bean.MenuPart;
import ru.terra.dms.configuration.bean.Pojo;
import ru.terra.dms.configuration.bean.ViewPart;

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

        String productsViewPartName = "Products";
        ViewPart productsViewPart = new ViewPart();
        productsViewPart.setId(UUID.randomUUID().toString());
        productsViewPart.setName(productsViewPartName);
        productsViewPart.setControllerType("simpletable");

        Pojo productPojo = new Pojo();
        productPojo.setName("Products");
        productPojo.setType("products");
        Map<String, String> productFields = new HashMap<>();
        productFields.put("id", "Long");
        productFields.put("name", "String");
        productFields.put("price", "Integer");
        productFields.put("group", "Long");
        productPojo.setFields(productFields);
        configuration.getPojos().add(productPojo);
        productsViewPart.setPojo(productPojo.getType());

        String groupsViewPartName = "Groups";
        ViewPart groupsViewPart = new ViewPart();
        groupsViewPart.setId(UUID.randomUUID().toString());
        groupsViewPart.setName(groupsViewPartName);
        groupsViewPart.setControllerType("simpletable");

        Pojo groupPojo = new Pojo();
        groupPojo.setName("Groups");
        groupPojo.setType("groups");
        Map<String, String> groupFields = new HashMap<>();
        groupFields.put("id", "Long");
        groupFields.put("name", "String");
        groupPojo.setFields(groupFields);
        configuration.getPojos().add(groupPojo);
        groupsViewPart.setPojo(groupPojo.getName());

        configuration.setViewParts(Arrays.asList(productsViewPart, groupsViewPart));

        MenuPart productMenuPart = new MenuPart();
        productMenuPart.setShortcut("ctrl+1");
        productMenuPart.setText("Products");
        productMenuPart.setType(MenuPart.MenuPartType.VIEWPART);
        productMenuPart.setViewPart(productPojo.getName());

        MenuPart groupMenuPart = new MenuPart();
        groupMenuPart.setShortcut("ctrl+2");
        groupMenuPart.setText("Groups");
        groupMenuPart.setType(MenuPart.MenuPartType.VIEWPART);
        groupMenuPart.setViewPart(groupsViewPartName);

        configuration.setMenus(Arrays.asList(groupMenuPart, productMenuPart));
        configuration.setComment("comment 1");
        configuration.setName("configuration name 1");

//        new ObjectMapper().writeValue(new File("configuration.json"), configuration);
    }
}
