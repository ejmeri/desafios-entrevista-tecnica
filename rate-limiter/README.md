# Rate Limiter Implementation

Este projeto implementa dois algoritmos clássicos de **Rate Limiting** em Java: **Sliding Window** e **Token Bucket**, demonstrando diferentes abordagens para controle de taxa de requisições.

## 📋 Visão Geral

Rate Limiting é uma técnica fundamental para controlar o número de requisições que um sistema pode processar em um determinado período, protegendo contra sobrecarga e ataques DDoS.

## 🏗️ Implementações

### 🪟 Algoritmo 1: Sliding Window

```java
public class SlidingWindow {
    private final int windowSizeSeconds;
    private final int maxRequests;
    private final Deque<Long> timestamps = new ArrayDeque<>();

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
```

**Como funciona:**
1. Mantém um `Deque` com timestamps de todas as requisições
2. Remove timestamps que estão fora da janela de tempo atual
3. Permite requisição se ainda há "espaço" na janela
4. Adiciona timestamp da requisição atual se permitida

**Características:**
- ✅ **Precisão**: Controle exato do número de requisições por janela
- ✅ **Memória**: Armazena apenas timestamps necessários
- ⚠️ **Sincronização**: Método `synchronized` para thread-safety
- 📊 **Complexidade**: O(n) para limpeza, onde n = requisições na janela

### 🪣 Algoritmo 2: Token Bucket

```java
public class TokenBucket {
    private final long capacity;
    private final long refillTokens;
    private final long refillIntervalNanos;
    private final AtomicLong availableTokens = new AtomicLong();
    private volatile long lastRefillTimestamp;

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
```

**Como funciona:**
1. Mantém um "balde" com tokens disponíveis
2. Tokens são reabastecidos em intervalos regulares
3. Cada requisição consome um token
4. Permite "burst" até a capacidade máxima do balde

**Características:**
- ✅ **Burst**: Permite rajadas de requisições até a capacidade
- ✅ **Suavização**: Distribui carga ao longo do tempo
- ✅ **Eficiência**: O(1) para operações básicas
- 🔧 **Flexibilidade**: Configurável taxa de reabastecimento

## 📊 Comparação dos Algoritmos

| Aspecto | Sliding Window | Token Bucket |
|---------|----------------|--------------|
| **Precisão** | ⭐⭐⭐ Exata | ⭐⭐ Aproximada |
| **Burst Handling** | ❌ Não permite | ✅ Permite burst |
| **Memória** | O(n) timestamps | O(1) constante |
| **Complexidade** | O(n) limpeza | O(1) operações |
| **Thread Safety** | Synchronized | AtomicLong + volatile |
| **Uso Típico** | APIs críticas | Sistemas web |

## 🚀 Exemplos de Uso

### Sliding Window Example
```java
// Máximo 5 requisições por 10 segundos
SlidingWindow limiter = new SlidingWindow(10, 5);

for (int i = 0; i < 10; i++) {
    if (limiter.tryAcquire()) {
        System.out.println("Requisição " + i + " permitida");
    } else {
        System.out.println("Requisição " + i + " bloqueada");
    }
}
```

### Token Bucket Example
```java
// Capacidade: 10 tokens, reabastece 2 tokens a cada 1000ms
TokenBucket limiter = new TokenBucket(10, 2, 1000);

for (int i = 0; i < 15; i++) {
    if (limiter.tryAcquire()) {
        System.out.println("Requisição " + i + " permitida");
    } else {
        System.out.println("Requisição " + i + " bloqueada");
    }
    Thread.sleep(100); // Simula intervalo entre requisições
}
```

## 🎯 Quando Usar Cada Algoritmo

### Sliding Window
- ✅ **APIs críticas** que precisam de controle exato
- ✅ **Sistemas de pagamento** com limites rígidos
- ✅ **Rate limiting por usuário** com precisão
- ✅ **Compliance** com SLAs específicos

### Token Bucket
- ✅ **Sistemas web** que precisam lidar com picos
- ✅ **CDNs** e proxies reversos
- ✅ **Microserviços** com tráfego variável
- ✅ **APIs públicas** que permitem burst

## 🔧 Configurações Recomendadas

### Para APIs REST
```java
// Sliding Window: 100 req/min para usuários normais
SlidingWindow userLimit = new SlidingWindow(60, 100);

// Token Bucket: 1000 req/min com burst de 50 para premium
TokenBucket premiumLimit = new TokenBucket(50, 17, 1000); // ~1000/min
```

### Para Microserviços
```java
// Token Bucket com capacidade para absorver picos
TokenBucket serviceLimit = new TokenBucket(200, 10, 100); // 600/min com burst
```

## 📚 Conceitos Demonstrados

- **Rate Limiting**: Controle de taxa de requisições
- **Thread Safety**: Sincronização e operações atômicas
- **Data Structures**: Deque para janela deslizante
- **Time Management**: Trabalho com timestamps e nanosegundos
- **Algorithm Design**: Trade-offs entre precisão e performance
- **Concurrency**: AtomicLong e volatile para thread-safety

## 🎓 Complexidade

### Sliding Window
- **Tempo**: O(n) para limpeza, O(1) para verificação
- **Espaço**: O(n) onde n = requisições na janela

### Token Bucket
- **Tempo**: O(1) para todas as operações
- **Espaço**: O(1) constante

## 🌟 Extensões Possíveis

- **Distributed Rate Limiting**: Usando Redis ou Hazelcast
- **Weighted Tokens**: Diferentes custos por tipo de requisição
- **Adaptive Limits**: Ajuste dinâmico baseado em carga
- **Metrics Integration**: Monitoramento com Prometheus/Micrometer

---

*Estes algoritmos são fundamentais em sistemas distribuídos modernos e frequentemente aparecem em entrevistas técnicas para posições de backend e arquitetura de sistemas.*
