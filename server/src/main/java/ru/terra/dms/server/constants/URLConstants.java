package ru.terra.dms.server.constants;

import ru.terra.server.constants.CoreUrlConstants;

/**
 * Date: 02.06.14
 * Time: 11:55
 */

public class URLConstants extends CoreUrlConstants {
    public static class Objects {
        public static final String OBJECTS = "/objects/";
        public static final String LIST_BY_NAME = "do.list.byname.json";
    }

    public static class Configuration {
        public static final String CONFIGURATION = "/configuration/";
    }

    public static class Login {
        public static final String USERS = "/users";
        public static final String DO_LOGIN = "/do.login.json";
        public static final String DO_REG = "/do.reg.json";
        public static final String PARAM_USER = "user";
        public static final String PARAM_PASS = "pass";
    }
}