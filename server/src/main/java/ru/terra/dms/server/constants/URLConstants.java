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
        public static final String LIST_BY_PARENT = "do.list.byparent.json";
        public static final String COUNT_BY_NAME = "do.count.byname.json";
        public static final String COUNT_BY_PARENT = "do.count.byparent.json";
    }

    public static class Configuration {
        public static final String CONFIGURATION = "/configuration/";
    }

    public class Resources {
        public static final String RESOURCES = "/resources/";
    }

    public static class Login {
        public static final String USERS = "/users";
        public static final String DO_LOGIN = "/do.login.json";
        public static final String DO_REG = "/do.reg.json";
        public static final String PARAM_USER = "user";
        public static final String PARAM_PASS = "pass";
    }

    public static class UI {
        public static final String UI = "ui/";
        public static final String MAIN = "main";

        public static class Login {
            public static final String URL = UI + "login/";
            public static final String LOGIN = "do.login";
        }

        public static class Configuration {
            public static final String URL = UI + "configuration/";
            public static final String CONFIGURATION = "do.configuration.show";

            public static class Menu {
                public static final String URL = Configuration.URL + "menu";
            }

            public static class ViewPart {
                public static final String URL = Configuration.URL + "/viewpart";

                public static class Pojo {
                    public static final String URL = ViewPart.URL + "/pojo";
                }
            }
        }
    }
}
