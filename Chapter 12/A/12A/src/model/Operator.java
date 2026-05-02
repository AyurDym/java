package model;

import java.util.concurrent.*;
import java.util.Random;

public class Operator implements Runnable {
    private final int id;
    private final CallCenter center;
    private volatile boolean running;
    private int callsHandled;
    private String state;
    private final Random random;

    public Operator(int id, CallCenter center) {
        this.id = id;
        this.center = center;
        this.running = true;
        this.callsHandled = 0;
        this.state = "idle";
        this.random = new Random();
    }

    @Override
    public void run() {
        System.out.println("Operator #" + id + " ready");

        while (running && center.isOpen()) {
            try {
                state = "waiting";
                Client client = center.getNextClient();

                if (client == null) {
                    state = "rest";
                    int restTime = random.nextInt(3) + 1;
                    System.out.println("Operator #" + id + " resting for " + restTime + " sec");
                    Thread.sleep(TimeUnit.SECONDS.toMillis(restTime));
                    continue;
                }

                state = "talking";
                int talkTime = client.getCallDuration();
                System.out.println("Operator #" + id + " talking to " + client.getName() +
                        " (" + talkTime + " sec)");

                Thread.sleep(talkTime * 1000L);

                center.finishCall(client, this);
                callsHandled++;
                state = "idle";

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        state = "offline";
        System.out.println("Operator #" + id + " stopped. Handled: " + callsHandled);
    }

    public void stop() {
        running = false;
    }

    public int getId() { return id; }
    public int getCallsHandled() { return callsHandled; }
    public String getState() { return state; }
}