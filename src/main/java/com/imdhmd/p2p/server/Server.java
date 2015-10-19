package com.imdhmd.p2p.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.imdhmd.p2p.Main.log;

public class Server extends Thread {
    private ServerSocket socket;
    private Shared shared;

    public Server() throws IOException {
        socket = new ServerSocket(8812);
        shared = new Shared();
    }

    public void run() {
        log("S Server started");

        try {
            while (true) {
                Socket requestSocket = accept();
                if (shared.quit())
                    break;

                log("S New request received");
                new RequestHandler(requestSocket, shared).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close();
            log("S Server done");
        }
    }

    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Socket accept() throws IOException {
        log("S Waiting for request");
        return socket.accept();
    }
}
