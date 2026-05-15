package com.example;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final String targetHost;
    private final int targetPort;

    public ClientHandler(Socket clientSocket, String targetHost, int targetPort) {
        this.clientSocket = clientSocket;
        this.targetHost = targetHost;
        this.targetPort = targetPort;
    }

    @Override
    public void run() {
        try (
                Socket targetSocket = new Socket(targetHost, targetPort);
                InputStream clientIn = clientSocket.getInputStream();
                OutputStream clientOut = clientSocket.getOutputStream();
                InputStream targetIn = targetSocket.getInputStream();
                OutputStream targetOut = targetSocket.getOutputStream()
        ) {
            Thread clientToTarget = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientIn));
                    PrintWriter writer = new PrintWriter(targetOut, true);

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String modifiedMessage = "[PROXY] " + line;
                        System.out.println("От клиента: " + line);
                        System.out.println("На сервер: " + modifiedMessage);
                        writer.println(modifiedMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Thread targetToClient = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(targetIn));
                    PrintWriter writer = new PrintWriter(clientOut, true);

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String modifiedResponse = "[SERVER] " + line;
                        System.out.println("От сервера: " + line);
                        System.out.println("Клиенту: " + modifiedResponse);
                        writer.println(modifiedResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            clientToTarget.start();
            targetToClient.start();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}