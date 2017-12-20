package ru.terra.twitsaver;

/**
 * Date: 21.06.15
 * Time: 11:19
 */

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.terra.dms.configuration.Configuration;
import ru.terra.dms.configuration.bean.Pojo;
import ru.terra.dms.rest.RestService;
import ru.terra.dms.shared.dto.ObjectDTO;
import ru.terra.server.dto.LoginDTO;
import ru.terra.twitsaver.dto.Medium;
import ru.terra.twitsaver.dto.Twit;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static String USER = "awd";
    public static String PASS = "awd";
    public static String CONF_NAME = "TerraFile";
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static Pojo tePojo;

    public static void run(String... tag) throws InterruptedException, IOException {
        try {
            LoginDTO ret = RestService.getInstance().login(USER, PASS);
            if (ret.logged) {
                RestService.getInstance().setSession(ret.session);
                final Configuration configuration = RestService.getInstance().loadConfiguration();
                tePojo = configuration.getPojo(CONF_NAME);
                logger.info("Got pojo: " + tePojo.toString());
            } else {
                logger.info("Unable to login");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Client client = null;
        try {
            ExecutorService service = Executors.newFixedThreadPool(30);

            BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
            // add some track terms
            StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
            endpoint.followings(Lists.newArrayList(902974493890674689L, 2810110856L, 1520108017L, 967631443L, 558929794L, 2568114020L, 2224934952L, 256253131L, 725718923342024704L));

            Authentication auth = new OAuth1("mxkj9PpSNFO7Qgkmpasq1ec4S", "nLtcxVNsnT3lhtSf9qraSlEJ6u4uK9m0jRR0JIHBEBzZnnJTEc", "2550132680-cPN1zH41SJMnSzPEgp7eyouaCXbZYIE6sOCVDIv", "TzCBXwp7elFJZQqIs869EVtB03WskjuULhWLuHC9fRkBG");
            // Authentication auth = new BasicAuth(username, password);

            // Create a new BasicClient. By default gzip is enabled.
            client = new ClientBuilder()
                    .hosts(Constants.STREAM_HOST)
                    .endpoint(endpoint)
                    .authentication(auth)
                    .processor(new StringDelimitedProcessor(queue))
                    .build();

            // Establish a connection
            client.connect();
            new Thread(() -> {
                while (true) {
                    int MESSAGES = 20;
                    if (queue.size() >= MESSAGES) {
                        List<String> messages = new ArrayList();
                        queue.drainTo(messages, MESSAGES);
                        service.submit(() -> {
                                    for (String msg : messages) {
                                        try {
                                            Twit twit = new ObjectMapper().readValue(msg, Twit.class);
                                            List<Medium> media = twit.getEntities().getMedia();
                                            service.submit(() -> {
                                                try {
                                                    for (Medium m : media)
                                                        Main.downloadImage(m.getMediaUrl());

                                                    if (twit.getExtendedEntities() != null && twit.getExtendedEntities().getMedia() != null && twit.getExtendedEntities().getMedia().size() > 0) {
                                                        List<Medium> extendedMedia = twit.getExtendedEntities().getMedia();
                                                        for (Medium m : extendedMedia) {
                                                            Main.downloadImage(m.getMediaUrl());
                                                            if (m.getAdditionalProperties().containsKey("video_info")) {
                                                                Map video_info = (Map) m.getAdditionalProperties().get("video_info");
                                                                List variants = (List) video_info.get("variants");
                                                                if (variants.size() > 0) {
                                                                    for (Object v : variants) {
                                                                        Map variant = (Map) v;
                                                                        if (variant.containsKey("url")) {
                                                                            Main.downloadImage((String) variant.get("url"));
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            });
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                        );
                    }
                }
            }
            ).start();
            while (true) {
                Thread.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null)
                client.stop();
        }
    }

    private static void downloadImage(String mediaUrl) throws IOException {
        ObjectDTO dto = new ObjectDTO();
        dto.id = 0;
        dto.type = "TerraFile";
        dto.fields = new HashMap<>();
        dto.fields.put("url", mediaUrl);
        dto.fields.put("folder", produceFolderName());
        dto.fields.put("filename", mediaUrl.substring(mediaUrl.lastIndexOf("/") + 1));
        dto.fields.put("needcheck", Boolean.toString(true));
        logger.info("Result: " + RestService.getInstance().createObjects(new ObjectMapper().writeValueAsString(dto)).errorCode);
    }

    public static void main(String[] args) throws Exception {
        try {
            Main.run(args);
        } catch (InterruptedException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String produceFolderName() {
        Calendar twitDate = Calendar.getInstance();
        twitDate.setTime(new Date());
        String folderName = "images/";
        folderName += String.valueOf(twitDate.get(Calendar.MONTH) + 1);
        folderName += "/";
        folderName += twitDate.get(Calendar.DAY_OF_MONTH);
        folderName += "/";
        return folderName;
    }
}
