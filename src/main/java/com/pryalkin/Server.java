package com.pryalkin;

import com.pryalkin.factory.Factory;
import com.pryalkin.handler.Handler;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try (var server = new ServerSocket(this.port)) {
            System.out.println("Сервер запущен на порту 8080");
            while (true) {
                var socket = server.accept();
                var thread = new Handler(socket);
                thread.start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       Factory.init();
       new Server(8080).start();
    }
}
