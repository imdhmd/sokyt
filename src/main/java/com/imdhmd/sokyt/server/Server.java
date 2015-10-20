package com.imdhmd.sokyt.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static com.imdhmd.sokyt.Main.log;

public class Server implements Runnable {
    private final ExecutorService executorService;
    private ServerSocket socket;
    private Shared shared;

    public Server(ExecutorService executorService) throws IOException {
        socket = new ServerSocket(8812);
        shared = new Shared();
        this.executorService = executorService;
    }

    public void run() {
        log("S Server started");

        try {
            while (true) {
                Socket requestSocket = accept();
                if (shared.quit())
                    break;

                log("S New connection");
                executorService.execute(
                        new RequestHandler(requestSocket, shared));
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
            executorService.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Socket accept() throws IOException {
        log("S Waiting for request");
        return socket.accept();
    }
}
