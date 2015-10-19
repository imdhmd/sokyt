package com.imdhmd.p2p.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.imdhmd.p2p.Main.log;

public class RequestHandler extends Thread {
    private Socket socket;
    private DataOutputStream out;

    public RequestHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    public void run() {
        try {
            boolean stay = true;
//            while (stay) {
                String request = nextRequest();
                log("S New request: " + request);

                reply("Thanks for " + request);

                stay = !"bye".equals(request.toLowerCase().trim());
//            }
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
        out.writeBytes(response);
    }

    private String nextRequest() throws IOException {
        return convertStreamToString(socket.getInputStream());
    }

    String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
