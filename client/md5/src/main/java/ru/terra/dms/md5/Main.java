package ru.terra.dms.md5;

import ru.terra.dms.configuration.Configuration;
import ru.terra.dms.configuration.bean.Pojo;
import ru.terra.dms.rest.RestService;
import ru.terra.server.dto.LoginDTO;

/**
 * Date: 17.07.15
 * Time: 18:48
 */
public class Main {
    public static String USER = "awd";
    public static String PASS = "awd";

    public static void main(String... args) {
        try {
            LoginDTO ret = RestService.getInstance().login(USER, PASS);
            if (ret.logged) {
                RestService.getInstance().setSession(ret.session);
                final Configuration configuration = RestService.getInstance().loadConfiguration();
                System.out.println(configuration.toString());
                final Pojo md5Pojo = configuration.getPojo("MD5Hash");
                System.out.println(md5Pojo.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
