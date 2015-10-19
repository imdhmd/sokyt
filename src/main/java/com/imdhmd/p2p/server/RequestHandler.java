package com.imdhmd.p2p.server;

import java.io.IOException;
import java.net.Socket;

import static com.imdhmd.p2p.Main.*;

public class RequestHandler extends Thread {
    private Socket socket;

    public RequestHandler(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void run() {
        try {
            boolean stay = true;
            while (stay) {
                String request = nextRequest();
                log("S New request: " + request);

                reply("Thanks for: " + request);

                stay = !"bye".equals(request.trim().toLowerCase());
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
        return readAsString(socket.getInputStream());
    }
}
