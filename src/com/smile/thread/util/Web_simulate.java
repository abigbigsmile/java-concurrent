package com.smile.thread.util;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Web_simulate {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(1234);
        while(!Thread.interrupted()){
            Socket client = server.accept();
            new Thread(new ServerThread(client)).start();
        }

    }
}
