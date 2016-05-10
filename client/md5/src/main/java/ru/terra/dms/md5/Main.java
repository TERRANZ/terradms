package ru.terra.dms.md5;

import org.codehaus.jackson.map.ObjectMapper;
import ru.terra.dms.configuration.Configuration;
import ru.terra.dms.configuration.bean.Pojo;
import ru.terra.dms.rest.RestService;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terra.server.dto.LoginDTO;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                ExecutorService service = Executors.newFixedThreadPool(20);
                RestService.getInstance().setSession(ret.session);
                final Configuration configuration = RestService.getInstance().loadConfiguration();
                System.out.println(configuration.toString());
                final Pojo md5Pojo = configuration.getPojo("MD5Hash");
                System.out.println(md5Pojo.toString());
                try {
                    File targetDir = new File(args[0]);
                    if (targetDir != null) {
                        Files.walk(Paths.get(targetDir.toURI())).parallel().forEach(path -> {
                            if (path.toFile().isFile()) {
                                ObjectDTO dto = new ObjectDTO();
                                dto.id = 0;
                                dto.type = "MD5Hash";
                                dto.fields = new HashMap<>();
                                dto.fields.put("name", path.toFile().getAbsolutePath());
                                dto.fields.put("hash", doMd5(path));
                                service.submit(() -> {
                                    try {
                                        System.out.println("Result: " + RestService.getInstance().createObjects(new ObjectMapper().writeValueAsString(dto)).errorCode);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                service.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String doMd5(Path p) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(p.toFile());

            byte[] dataBytes = new byte[1024];

            int nread = 0;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            ;
            byte[] mdbytes = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
