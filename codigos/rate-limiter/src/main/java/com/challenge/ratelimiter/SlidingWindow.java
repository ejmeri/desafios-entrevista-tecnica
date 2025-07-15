package com.challenge.ratelimiter;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Rate‑Limiter Sliding Window simples (contagem de requisições por segundo).
 */
public class SlidingWindow {
    private final int windowSizeSeconds;
    private final int maxRequests;
    private final Deque<Long> timestamps = new ArrayDeque<>();

    public SlidingWindow(int windowSizeSeconds, int maxRequests) {
        this.windowSizeSeconds = windowSizeSeconds;
        this.maxRequests = maxRequests;
    }

    /**
     * Retorna true se a chamada pode prosseguir; false se excedeu o limite.
     */
    public synchronized boolean tryAcquire() {
        long now = Instant.now().getEpochSecond();
        long boundary = now - windowSizeSeconds;

        // Remove timestamps fora da janela
        while (!timestamps.isEmpty() && timestamps.peekFirst() <= boundary) {
            timestamps.pollFirst();
        }

        if (timestamps.size() < maxRequests) {
            timestamps.addLast(now);
            return true;
        }
        return false;
    }
}