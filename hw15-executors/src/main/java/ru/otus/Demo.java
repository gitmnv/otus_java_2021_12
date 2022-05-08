package ru.otus;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        Demo demo = new Demo();
        var t = new Thread(() -> demo.loop("thread0"));
        var t1 = new Thread(() -> demo.loop("thread1"));
        t.start();
        t1.start();
        t.join();
        t1.join();

    }

    private synchronized void loop(String threadName) {

        boolean b = false;
        AtomicInteger count = new AtomicInteger(1);
        while (true) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            System.out.println("count: " + count + " " + threadName);


            if (!b) {
                count.incrementAndGet();
            } else {
                count.decrementAndGet();
            }
            if (count.get() == 10) {
                b = true;
            }
            if (count.get() == 1) {
                b = false;
            }
            notify();

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
