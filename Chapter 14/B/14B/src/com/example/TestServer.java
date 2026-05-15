package com.example;

import java.io.*;
import java.net.*;

public class TestServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9090)) {
            System.out.println("Тестовый сервер запущен на порту 9090");

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Клиент подключился: " + client.getInetAddress());

                new Thread(() -> {
                    try {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(client.getInputStream()));
                        PrintWriter out = new PrintWriter(
                                client.getOutputStream(), true);

                        String message;
                        while ((message = in.readLine()) != null) {
                            System.out.println("Получено: " + message);
                            out.println("Эхо: " + message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}