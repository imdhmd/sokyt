package com.imdhmd.sokyt;

import com.imdhmd.sokyt.client.Client;
import com.imdhmd.sokyt.server.Server;

import java.io.*;

public class Main {
    private static final char EOF = '\0';

    public static void main(String... args) throws IOException {
        new Server().start();
        new Client().start();
    }

    public static void log(String msg) {
        System.out.println(msg);
    }

    public static String readAsString(InputStream is) throws IOException {
        DataInputStream in = new DataInputStream(is);
        char nextChar = EOF;
        StringBuilder buffer = new StringBuilder();
        while ((nextChar = in.readChar()) != EOF) {
            buffer.append(nextChar);
        }

        return buffer.toString();
    }

    public static void writeString(String string, OutputStream os) throws IOException {
        DataOutputStream out = new DataOutputStream(os);
        out.writeChars(string);
        out.writeChar(EOF);
        out.flush();
    }
}
