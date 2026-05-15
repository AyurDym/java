package by.epam.learn.net;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();
    private static List<String> clientList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Чат-сервер запущен на порту " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        }
    }

    static synchronized void addClient(String name) {
        clientList.add(name);
        broadcastClientList();
    }

    static synchronized void removeClient(String name) {
        clientList.remove(name);
        broadcastClientList();
    }

    static synchronized List<String> getClientList() {
        return new ArrayList<>(clientList);
    }

    static void broadcastClientList() {
        String listMsg = "CLIENT_LIST:" + String.join(",", clientList);
        for (ClientHandler client : clients) {
            client.sendMessage(listMsg);
        }
    }

    static void broadcastMessage(String message, String senderName) {
        for (ClientHandler client : clients) {
            if (!client.getClientName().equals(senderName)) {
                client.sendMessage(senderName + ": " + message);
            }
        }
    }

    static void sendToClient(String targetName, String message, String senderName) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(targetName)) {
                client.sendMessage("PRIVATE:" + senderName + ": " + message);
                break;
            }
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public String getClientName() {
            return clientName;
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Введите ваше имя:");
                clientName = in.readLine();

                if (clientName == null || clientName.trim().isEmpty()) {
                    clientName = "Аноним_" + System.currentTimeMillis();
                }

                addClient(clientName);
                System.out.println(clientName + " подключился к чату");

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/msg ")) {
                        String[] parts = message.split(" ", 3);
                        if (parts.length >= 3) {
                            String target = parts[1];
                            String privateMsg = parts[2];
                            sendToClient(target, privateMsg, clientName);
                            out.println("[ЛИЧНОЕ " + target + "]: " + privateMsg);
                        }
                    } else if (message.startsWith("/list")) {
                        out.println("Список клиентов: " + getClientList());
                    } else {
                        broadcastMessage(message, clientName);
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка клиента " + clientName + ": " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                removeClient(clientName);
                clients.remove(this);
                System.out.println(clientName + " отключился");
            }
        }
    }
}