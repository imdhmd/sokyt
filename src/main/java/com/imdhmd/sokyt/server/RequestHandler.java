package com.imdhmd.sokyt.server;

import java.io.IOException;
import java.net.Socket;

import static com.imdhmd.sokyt.Main.*;

public class RequestHandler extends Thread {
    private Socket socket;
    private Shared shared;

    public RequestHandler(Socket socket, Shared shared) throws IOException {
        this.socket = socket;
        this.shared = shared;
    }

    public void run() {
        try {
            boolean stay = true;
            while (stay) {
                String request = nextRequest();
                log("S New request: " + request);

                reply("Thanks for '" + request + "'");

                stay = !("bye".equals(request.toLowerCase()) || "quit".equals(request.toLowerCase()));
                shared.checkQuit(request);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void reply(String response) throws IOException {
        writeString(response, socket.getOutputStream());
    }

    private String nextRequest() throws IOException {
        return readAsString(socket.getInputStream()).trim().toLowerCase();
    }
}
