package com.challenge.ratelimiter;

import java.util.concurrent.atomic.AtomicLong;

/** Rate‑Limiter estilo Token Bucket. */
public class TokenBucket {
    private final long capacity;
    private final long refillTokens;
    private final long refillIntervalNanos;
    private final AtomicLong availableTokens = new AtomicLong();
    private volatile long lastRefillTimestamp;

    public TokenBucket(long capacity, long refillTokens, long refillIntervalMillis) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillIntervalNanos = refillIntervalMillis * 1_000_000L;
        this.availableTokens.set(capacity);
        this.lastRefillTimestamp = System.nanoTime();
    }

    /**
     * Tenta consumir 1 token; retorna true se permitido, false se rate‑limitado.
     */
    public synchronized boolean tryAcquire() {
        refill();
        if (availableTokens.get() > 0) {
            availableTokens.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.nanoTime();
        long elapsed = now - lastRefillTimestamp;
        if (elapsed >= refillIntervalNanos) {
            long tokensToAdd = (elapsed / refillIntervalNanos) * refillTokens;
            long newCount = Math.min(capacity, availableTokens.get() + tokensToAdd);
            availableTokens.set(newCount);
            lastRefillTimestamp = now;
        }
    }
}