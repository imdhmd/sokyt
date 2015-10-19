package com.imdhmd.p2p;

import com.imdhmd.p2p.client.Client;
import com.imdhmd.p2p.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String... args) throws IOException {
        new Server().start();
        new Client().start();
    }

    public static void log(String msg) {
        System.out.println(msg);
    }
}
