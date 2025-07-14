package com.challenge.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Um ThreadPool minimalista que cobre 90 % das perguntas de entrevista.
 * Demonstra: fila de tarefas (`BlockingQueue`), marcação de shutdown e workers.
 */
public class SimpleThreadPool implements AutoCloseable {
    private final BlockingQueue<Runnable> taskQueue;
    private final Worker[] workers;
    private volatile boolean shuttingDown = false;

    public SimpleThreadPool(int poolSize, int queueCapacity) {
        this.taskQueue = new LinkedBlockingQueue<>(queueCapacity);
        this.workers = new Worker[poolSize];
        for (int i = 0; i < poolSize; i++) {
            workers[i] = new Worker("simple-pool-" + i);
            workers[i].start();
        }
    }

    /** Submete uma tarefa, bloqueando se a fila estiver cheia. */
    public void submit(Runnable task) throws InterruptedException {
        if (shuttingDown) {
            throw new IllegalStateException("ThreadPool is shutting down");
        }
        taskQueue.put(task);
    }

    @Override
    public void close() {
        shuttingDown = true;
        for (Worker w : workers) {
            w.interrupt();
        }
    }

    private class Worker extends Thread {
        Worker(String name) {
            super(name);
        }

        public void run() {
            while (!shuttingDown || !taskQueue.isEmpty()) {
                try {
                    Runnable task = taskQueue.poll(1, TimeUnit.SECONDS);
                    if (task != null) {
                        task.run();
                    }
                } catch (InterruptedException ignored) {
                    // permite que o worker finalize
                }
            }
        }
    }
}