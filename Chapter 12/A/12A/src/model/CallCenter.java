package model;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

public class CallCenter {
    private final String name;
    private final List<Operator> operators;
    private final BlockingQueue<Client> queue;
    private final AtomicInteger processedCalls;
    private final AtomicInteger droppedCalls;
    private volatile boolean open;

    public CallCenter(String name, int operatorCount, int queueCapacity) {
        this.name = name;
        this.operators = new CopyOnWriteArrayList<>();
        this.queue = new ArrayBlockingQueue<>(queueCapacity);
        this.processedCalls = new AtomicInteger(0);
        this.droppedCalls = new AtomicInteger(0);
        this.open = true;

        for (int i = 1; i <= operatorCount; i++) {
            operators.add(new Operator(i, this));
        }
    }

    public void start() {
        System.out.println("CallCenter '" + name + "' started. Operators: " + operators.size() +
                ", Queue size: " + (queue.remainingCapacity() + queue.size()));
        for (Operator op : operators) {
            new Thread(op, "Operator-" + op.getId()).start();
        }
    }

    public void stop() {
        open = false;
        for (Operator op : operators) {
            op.stop();
        }
        System.out.println("CallCenter '" + name + "' stopped. Processed: " + processedCalls.get() +
                ", Dropped: " + droppedCalls.get());
    }

    public boolean acceptCall(Client client) throws InterruptedException {
        if (!open) {
            return false;
        }
        boolean accepted = queue.offer(client, 2, TimeUnit.SECONDS);
        if (accepted) {
            System.out.println(client.getName() + " added to queue. Queue size: " + queue.size());
        } else {
            droppedCalls.incrementAndGet();
            System.out.println(client.getName() + " rejected. Queue full.");
        }
        return accepted;
    }

    public Client getNextClient() throws InterruptedException {
        return queue.poll(5, TimeUnit.SECONDS);
    }

    public void finishCall(Client client, Operator operator) {
        processedCalls.incrementAndGet();
        System.out.println(client.getName() + " finished by operator #" + operator.getId());
    }

    public boolean isOpen() { return open; }
    public int getQueueSize() { return queue.size(); }
    public int getProcessedCalls() { return processedCalls.get(); }
    public int getDroppedCalls() { return droppedCalls.get(); }
    public List<Operator> getOperators() { return Collections.unmodifiableList(operators); }

    public void printStatus() {
        System.out.println("Status: queue=" + queue.size() + ", processed=" + processedCalls.get() +
                ", dropped=" + droppedCalls.get());
        for (Operator op : operators) {
            System.out.println("  Operator #" + op.getId() + ": " + op.getState());
        }
    }
}
