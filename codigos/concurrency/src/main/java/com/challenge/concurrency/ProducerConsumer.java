package com.challenge.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/** Exemplo clássico Producer × Consumer usando bloqueio implícito da fila. */
public class ProducerConsumer {
    private static final int CAPACITY = 5;

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(CAPACITY);

        Runnable producer = () -> {
            int value = 0;
            try {
                while (true) {
                    queue.put(value);
                    System.out.printf("Produced %d (size=%d)%n", value, queue.size());
                    value++;
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable consumer = () -> {
            try {
                while (true) {
                    Integer val = queue.take();
                    System.out.printf("Consumed %d (size=%d)%n", val, queue.size());
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        new Thread(producer, "producer").start();
        new Thread(consumer, "consumer").start();
    }
}