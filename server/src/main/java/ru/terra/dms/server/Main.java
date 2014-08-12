package ru.terra.dms.server;

import ru.terra.server.ServerBoot;

import java.io.IOException;

/**
 * Date: 27.05.14
 * Time: 12:05
 */

public class Main {
    public static void main(String... args) throws IOException {
        new ServerBoot().start();
    }
}
