package com.imdhmd.p2p.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static com.imdhmd.p2p.Main.*;

public class Client extends Thread {

    private final Socket socket;
    private final BufferedReader consoleIn;

    public Client() throws IOException {
        socket = new Socket("localhost", 8812);
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        try {
            communicate("So it begins!");

            boolean stay = true;
            while (stay) {
                String input = nextInput();
                communicate(input);
                stay = !("bye".equals(input.toLowerCase()) || "quit".equals(input.toLowerCase()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close();
            log("C Client done");
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
        return consoleIn.readLine().trim();
    }

    private void communicate(String input) {
        try {
            log("C Communicating: " + input);
            writeString(input, socket.getOutputStream());

            String response = readAsString(socket.getInputStream());
            log("C New Response: " + response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
