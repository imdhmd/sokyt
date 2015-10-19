package com.imdhmd.p2p.server;

import java.io.IOException;
import java.net.Socket;

public class Shared {
    private boolean quit = false;

    public synchronized void checkQuit(String request) {
        if (quit)
            return;

        quit = "quit".equals(request);
        if (quit) {
            try {
                new Socket("localhost", 8812);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean quit() {
        return quit;
    }
}