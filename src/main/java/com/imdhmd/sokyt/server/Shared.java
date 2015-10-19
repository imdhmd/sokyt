package com.imdhmd.sokyt.server;

import java.io.IOException;
import java.net.Socket;

public class Shared {
    private boolean quit = false;

    public synchronized void checkQuit(String request) {
        if (quit())
            return;

        if ((quit = "quit".equals(request))) {
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
