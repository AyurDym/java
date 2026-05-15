package by.epam.learn.net;

import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Подключение к чат-серверу...");

            System.out.print("Введите ваше имя: ");
            String name = userInput.readLine();
            out.println(name);

            Thread receiverThread = new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = in.readLine()) != null) {
                        if (serverMsg.startsWith("PRIVATE:")) {
                            System.out.println("\n[ЛИЧНОЕ] " + serverMsg.substring(8));
                        } else if (serverMsg.startsWith("CLIENT_LIST:")) {
                            System.out.println("\n[ОБНОВЛЕНИЕ СПИСКА КЛИЕНТОВ]");
                            System.out.println(serverMsg);
                        } else {
                            System.out.println(serverMsg);
                        }
                        System.out.print("> ");
                    }
                } catch (IOException e) {
                    System.out.println("Соединение с сервером потеряно");
                }
            });
            receiverThread.setDaemon(true);
            receiverThread.start();

            System.out.println("=== Команды чата ===");
            System.out.println("/list - показать список клиентов");
            System.out.println("/msg <имя> <сообщение> - отправить личное сообщение");
            System.out.println("==================");
            System.out.print("> ");

            String userMsg;
            while ((userMsg = userInput.readLine()) != null) {
                out.println(userMsg);
                System.out.print("> ");
            }

        } catch (UnknownHostException e) {
            System.err.println("Сервер не найден: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}
