package model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Client implements Runnable {
    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    private final int id;
    private final String name;
    private final CallCenter center;
    private final int maxAttempts;
    private final int baseDuration;
    private volatile boolean served;
    private int attemptCount;
    private final Random random;

    public Client(CallCenter center) {
        this(center, 3, 4);
    }

    public Client(CallCenter center, int maxAttempts, int baseDuration) {
        this.id = idGenerator.incrementAndGet();
        this.name = "Client-" + id;
        this.center = center;
        this.maxAttempts = maxAttempts;
        this.baseDuration = baseDuration;
        this.served = false;
        this.attemptCount = 0;
        this.random = new Random();
    }

    @Override
    public void run() {
        System.out.println(name + " wants to call");

        while (!served && attemptCount < maxAttempts && center.isOpen()) {
            try {
                int delay = random.nextInt(3) + 1;
                Thread.sleep(TimeUnit.SECONDS.toMillis(delay));

                attemptCount++;
                System.out.println(name + " calling (attempt " + attemptCount + "/" + maxAttempts + ")");

                boolean accepted = center.acceptCall(this);

                if (accepted) {
                    synchronized (this) {
                        while (!served && center.isOpen()) {
                            wait(1000);
                        }
                    }
                } else {
                    if (attemptCount < maxAttempts) {
                        int retryDelay = random.nextInt(5) + 3;
                        System.out.println(name + " will retry after " + retryDelay + " sec");
                        Thread.sleep(TimeUnit.SECONDS.toMillis(retryDelay));
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        if (!served) {
            System.out.println(name + " left after " + attemptCount + " attempts");
        }
    }

    public void markServed() {
        synchronized (this) {
            served = true;
            notifyAll();
        }
    }

    public int getCallDuration() {
        return baseDuration + random.nextInt(6);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public boolean isServed() { return served; }
    public int getAttemptCount() { return attemptCount; }
}
