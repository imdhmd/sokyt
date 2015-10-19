package com.imdhmd.p2p.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.imdhmd.p2p.Main.log;

public class Client extends Thread {

    private Socket socket;
    private DataOutputStream out;

    public void run() {
        try {
            log("C Connecting");
            socket = new Socket("localhost", 8812);
            out = new DataOutputStream(socket.getOutputStream());
            communicate("So it begins!");

            boolean stay = true;

            while (stay) {
                String input = nextInput().trim();
                communicate(input);
                stay = !"bye".equals(input.toLowerCase());
            }

            log("C Client done");
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

    private String nextInput() throws IOException {
        log("C Next input: ");
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

    private void communicate(String input) throws IOException {
        out.writeBytes(input + "\r");
        log("C New Response " + convertStreamToString(socket.getInputStream()));
    }

    String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
