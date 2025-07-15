# Concurrency Patterns Implementation

Este projeto implementa padrões fundamentais de **concorrência** em Java: **Producer-Consumer** e **Thread Pool**, demonstrando conceitos essenciais para programação multi-threaded.

## 📋 Visão Geral

Concorrência é fundamental em sistemas modernos para maximizar utilização de recursos e melhorar responsividade. Este projeto demonstra dois padrões clássicos usados em entrevistas técnicas e sistemas reais.

## 🏗️ Implementações

### 🏭 Padrão 1: Producer-Consumer

```java
public class ProducerConsumer {
    private static final int CAPACITY = 5;

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(CAPACITY);

        Runnable producer = () -> {
            int value = 0;
            try {
                while (true) {
                    queue.put(value); // Bloqueia se fila cheia
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
                    Integer val = queue.take(); // Bloqueia se fila vazia
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
```

**Como funciona:**
1. **Producer** gera dados e os coloca na fila
2. **Consumer** retira dados da fila para processamento
3. **BlockingQueue** coordena automaticamente a sincronização
4. Threads bloqueiam quando fila está cheia (producer) ou vazia (consumer)

**Características:**
- ✅ **Thread-Safe**: BlockingQueue gerencia sincronização
- ✅ **Backpressure**: Producer para quando fila está cheia
- ✅ **Simplicidade**: Sem locks explícitos necessários
- 🔄 **Desacoplamento**: Producer e Consumer independentes

### 🧵 Padrão 2: Simple Thread Pool

```java
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

    public void submit(Runnable task) throws InterruptedException {
        if (shuttingDown) {
            throw new IllegalStateException("ThreadPool is shutting down");
        }
        taskQueue.put(task);
    }

    private class Worker extends Thread {
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
```

**Como funciona:**
1. **Workers** (threads) ficam em loop esperando tarefas
2. **Task Queue** armazena tarefas pendentes
3. **Submit** adiciona novas tarefas à fila
4. **Shutdown** sinaliza workers para finalizarem graciosamente

**Características:**
- ✅ **Reutilização**: Threads são reutilizadas para múltiplas tarefas
- ✅ **Controle**: Limita número de threads ativas
- ✅ **Graceful Shutdown**: Finalização controlada com AutoCloseable
- ⚡ **Performance**: Evita overhead de criação/destruição de threads

## 📊 Comparação dos Padrões

| Aspecto | Producer-Consumer | Thread Pool |
|---------|-------------------|-------------|
| **Propósito** | Coordenar produção/consumo | Executar tarefas concorrentemente |
| **Threads** | 2+ específicas | N workers genéricos |
| **Fila** | Buffer entre prod/cons | Queue de tarefas |
| **Uso Típico** | Streaming, pipelines | Processamento paralelo |
| **Complexidade** | ⭐⭐ Simples | ⭐⭐⭐ Moderada |

## 🚀 Exemplos de Uso

### Producer-Consumer em Ação
```java
// Output esperado:
Produced 0 (size=1)
Produced 1 (size=2)
Consumed 0 (size=1)
Produced 2 (size=2)
Consumed 1 (size=1)
Produced 3 (size=2)
// ... continua indefinidamente
```

### Thread Pool em Ação
```java
try (SimpleThreadPool pool = new SimpleThreadPool(3, 10)) {
    // Submete 10 tarefas
    for (int i = 0; i < 10; i++) {
        final int taskId = i;
        pool.submit(() -> {
            System.out.println("Executando tarefa " + taskId + 
                             " na thread " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000); // Simula trabalho
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    
    Thread.sleep(5000); // Aguarda execução
} // AutoCloseable fecha o pool automaticamente
```

## 🎯 Casos de Uso Reais

### Producer-Consumer
- **🌊 Stream Processing**: Kafka consumers, data pipelines
- **📁 File Processing**: Leitura/escrita assíncrona
- **🌐 Web Crawling**: URLs para processar vs páginas processadas
- **📊 Log Processing**: Coleta vs análise de logs

### Thread Pool
- **🌐 Web Servers**: Processamento de requisições HTTP
- **🔄 Batch Processing**: Processamento paralelo de dados
- **📧 Email Services**: Envio assíncrono de emails
- **🖼️ Image Processing**: Redimensionamento paralelo

## 🔧 Configurações Recomendadas

### Para I/O Intensivo
```java
// Mais threads que CPUs (I/O bloqueia threads)
SimpleThreadPool ioPool = new SimpleThreadPool(
    Runtime.getRuntime().availableProcessors() * 2, 
    100
);
```

### Para CPU Intensivo
```java
// Threads = número de CPUs
SimpleThreadPool cpuPool = new SimpleThreadPool(
    Runtime.getRuntime().availableProcessors(), 
    50
);
```

### Producer-Consumer com Diferentes Velocidades
```java
// Buffer maior para absorver diferenças de velocidade
BlockingQueue<Task> buffer = new LinkedBlockingQueue<>(1000);
```

## 📚 Conceitos Demonstrados

### Concorrência
- **Thread Safety**: Sincronização sem locks explícitos
- **BlockingQueue**: Coordenação automática entre threads
- **Interruption**: Tratamento correto de InterruptedException
- **Volatile**: Visibilidade de variáveis entre threads

### Design Patterns
- **Producer-Consumer**: Desacoplamento de produção e consumo
- **Thread Pool**: Reutilização de recursos caros
- **AutoCloseable**: Gerenciamento automático de recursos

### Boas Práticas
- **Graceful Shutdown**: Finalização controlada de threads
- **Resource Management**: try-with-resources para cleanup
- **Exception Handling**: Preservação de interrupt status

## ⚠️ Considerações Importantes

### Thread Safety
- **BlockingQueue** é thread-safe por design
- **volatile** garante visibilidade entre threads
- **Interrupt handling** é crucial para shutdown gracioso

### Performance
- **Thread Pool Size**: Balance entre paralelismo e overhead
- **Queue Capacity**: Evita OutOfMemoryError em picos
- **Task Granularity**: Tarefas nem muito pequenas nem muito grandes

## 🌟 Extensões Possíveis

- **Priority Queue**: Tarefas com diferentes prioridades
- **Scheduled Execution**: Tarefas com delay ou periódicas
- **Work Stealing**: Balanceamento dinâmico de carga
- **Metrics**: Monitoramento de throughput e latência
- **Circuit Breaker**: Proteção contra falhas em cascata

---

*Estes padrões são fundamentais em sistemas concorrentes e aparecem frequentemente em entrevistas para posições de backend, especialmente em discussões sobre arquitetura de sistemas distribuídos.*
