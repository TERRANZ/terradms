package ru.terra.dms.server;

import ru.terra.dms.server.im.jabber.JabberManager;
import ru.terra.server.ServerBoot;

import java.io.IOException;

/**
 * Date: 27.05.14
 * Time: 12:05
 */

public class Main {
    public static void main(String... args) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JabberManager.getInstance().start();
            }
        }).start();
        new ServerBoot().start();
    }
}
