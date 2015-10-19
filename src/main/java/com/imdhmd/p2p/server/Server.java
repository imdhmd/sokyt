package com.imdhmd.p2p.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

import static com.imdhmd.p2p.Main.log;

public class Server extends Thread {
    private ServerSocket socket;
    private Shared shared;
    private HashSet<RequestHandler> handlers;

    public Server() throws IOException {
        socket = new ServerSocket(8812);
        shared = new Shared();
        handlers = new HashSet<>();
    }

    public void run() {
        log("S Server started");

        try {
            while (true) {
                Socket requestSocket = accept();
                if (shared.quit()) {
                    waitForRemainingHandlers();
                    break;
                }

                log("S New request received");
                newRequestHandler(requestSocket).start();
                freeUpHandlers();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            close();
            log("S Server done");
        }
    }

    private void waitForRemainingHandlers() throws InterruptedException {
        for (RequestHandler handler : handlers) {
            if (handler.isAlive())
                handler.join();
        }
    }

    private RequestHandler newRequestHandler(Socket requestSocket) throws IOException {
        RequestHandler handler = new RequestHandler(requestSocket, shared);
        handlers.add(handler);
        return handler;
    }

    private void freeUpHandlers() {
        HashSet<RequestHandler> aliveHandlers = new HashSet<>();
        for (RequestHandler handler : handlers) {
            if (handler.isAlive())
                aliveHandlers.add(handler);
        }
        handlers.clear();
        handlers.addAll(aliveHandlers);
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
