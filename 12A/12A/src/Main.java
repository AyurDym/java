import model.*;
import java.util.concurrent.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        System.out.println("НАЧАЛО СИМУЛЯЦИИ КОЛЛ-ЦЕНТРА");
        System.out.println("========================================");

        int operatorCount = 3;
        int queueCapacity = 5;
        int clientCount = 12;
        int simulationTimeSec = 40;

        CallCenter center = new CallCenter("MainCenter", operatorCount, queueCapacity);
        center.start();

        ExecutorService clientPool = Executors.newFixedThreadPool(clientCount);

        System.out.println("\nClients are appearing...");
        for (int i = 0; i < clientCount; i++) {
            Client client = new Client(center, 3, 4);
            clientPool.submit(client);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 2000));
            } catch (InterruptedException e) {
                break;
            }
        }

        System.out.println("\nSimulation running for " + simulationTimeSec + " seconds...");

        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(simulationTimeSec));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        center.stop();

        clientPool.shutdownNow();
        try {
            clientPool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            clientPool.shutdownNow();
        }

        System.out.println("\n========================================");
        System.out.println("ИТОГОВАЯ СТАТИСТИКА");
        System.out.println("Обработанные звонки: " + center.getProcessedCalls());
        System.out.println("Сброшенные звонки: " + center.getDroppedCalls());

        System.out.println("\nСводка по операторам:");
        for (Operator op : center.getOperators()) {
            System.out.println("  Operator #" + op.getId() + " handled " + op.getCallsHandled() + " calls");
        }

        double successRate = (double) center.getProcessedCalls() /
                (center.getProcessedCalls() + center.getDroppedCalls()) * 100;
        System.out.println("\nSuccess rate: " + String.format("%.1f", successRate) + "%");

        System.out.println("========================================");
        System.out.println("КОНЕЦ СИМУЛЯЦИИ КОЛЛ-ЦЕНТРА");
    }
}