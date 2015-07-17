package ru.terra.dms.constants;

/**
 * Date: 14.07.14
 * Time: 12:27
 */
public class URLConstants {
    public static final String URL = "http://localhost:5555/dms";

    public static class Objects {
        public static final String OBJECTS = "/objects";
        public static final String LIST_BY_NAME = "/do.list.byname.json";
        public static final String LIST_BY_PARENT = "/do.list.byparent.json";
    }

    public static class Configuration {
        public static final String CONFIGURATION = "/configuration";
    }


    public static class Login {
        public static final String USERS = "/users";
        public static final String DO_LOGIN = "/do.login.json";
        public static final String DO_REG = "/do.reg.json";
        public static final String PARAM_USER = "user";
        public static final String PARAM_PASS = "pass";
    }
}
