package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyServer {
    private static final int PROXY_PORT = 8080;
    private static final String TARGET_HOST = "localhost";
    private static final int TARGET_PORT = 9090;

    public static void main(String[] args) {
        System.out.println("Запуск TCP прокси на порту " + PROXY_PORT);

        try (ServerSocket serverSocket = new ServerSocket(PROXY_PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Новое подключение от клиента: " + clientSocket.getInetAddress());

                Thread handlerThread = new Thread(new ClientHandler(clientSocket, TARGET_HOST, TARGET_PORT));
                handlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}