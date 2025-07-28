# 🎨 Design Patterns para Desenvolvedores

[![Java](https://img.shields.io/badge/language-Java-blue.svg)](https://www.java.com/)
[![Design Patterns](https://img.shields.io/badge/topic-Design%20Patterns-orange.svg)]()

> Guia prático de Design Patterns com implementações em Java para entrevistas técnicas e desenvolvimento profissional.

---

## 🎯 Por que Design Patterns são Importantes?

### 💡 **Comunicação Eficiente**

- **Linguagem comum** entre desenvolvedores
- **Documentação viva** do código
- **Facilita code reviews** e manutenção

### 🔧 **Soluções Testadas**

- **Problemas recorrentes** já resolvidos
- **Boas práticas** consolidadas pela comunidade
- **Reduz bugs** e melhora qualidade

### 🚀 **Carreira e Entrevistas**

- **Diferencial técnico** em processos seletivos
- **Demonstra maturidade** arquitetural
- **Base para frameworks** (Spring, Hibernate, etc.)

---

## 📚 Por Onde Começar?

### 🥇 **Nível Iniciante** - Padrões Fundamentais

1. **Singleton** - Uma única instância
2. **Factory Method** - Criação de objetos
3. **Observer** - Notificação de mudanças

### 🥈 **Nível Intermediário** - Padrões Estruturais

4. **Adapter** - Compatibilidade entre interfaces
5. **Decorator** - Adicionar funcionalidades
6. **Strategy** - Algoritmos intercambiáveis

### 🥉 **Nível Avançado** - Padrões Comportamentais

7. **Command** - Encapsular requisições
8. **Template Method** - Esqueleto de algoritmos
9. **Builder** - Construção complexa de objetos

---

## 🔍 Por que Estudar Design Patterns?

### 📈 **Benefícios Técnicos**

- **Código mais limpo** e organizizado
- **Facilita manutenção** e extensibilidade
- **Reduz acoplamento** entre componentes
- **Melhora testabilidade** do código

### 💼 **Benefícios Profissionais**

- **Requisito em vagas** pleno/sênior
- **Base para arquiteturas** complexas
- **Facilita trabalho em equipe**
- **Acelera desenvolvimento** de features

### 🧠 **Benefícios Cognitivos**

- **Desenvolve pensamento** arquitetural
- **Melhora capacidade** de abstração
- **Ensina quando NÃO usar** padrões
- **Prepara para criar** soluções próprias

---

## 💻 Exemplos Práticos em Java

### 🔸 1. Singleton - Instância Única

**Problema:** Garantir que uma classe tenha apenas uma instância.

```java
public class DatabaseConnection {
    private static volatile DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        // Inicialização custosa
        this.connection = DriverManager.getConnection("jdbc:...");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
```

**Uso em Entrevistas:**

- Cache de configurações
- Pool de conexões
- Loggers centralizados

---

### 🔸 2. Factory Method - Criação Flexível

**Problema:** Criar objetos sem especificar a classe exata.

```java
// Interface do produto
interface Notification {
    void send(String message);
}

// Implementações concretas
class EmailNotification implements Notification {
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class SMSNotification implements Notification {
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}

// Factory
class NotificationFactory {
    public static Notification createNotification(String type) {
        switch (type.toLowerCase()) {
            case "email": return new EmailNotification();
            case "sms": return new SMSNotification();
            default: throw new IllegalArgumentException("Tipo inválido: " + type);
        }
    }
}

// Uso
public class Main {
    public static void main(String[] args) {
        Notification notification = NotificationFactory.createNotification("email");
        notification.send("Bem-vindo!");
    }
}
```

**Vantagens:**

- Desacopla criação do uso
- Facilita adição de novos tipos
- Centraliza lógica de criação

---

### 🔸 3. Observer - Notificação Automática

**Problema:** Notificar múltiplos objetos sobre mudanças de estado.

```java
import java.util.*;

// Interface do Observer
interface Observer {
    void update(String event);
}

// Subject (Observable)
class EventManager {
    private List<Observer> observers = new ArrayList<>();

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String event) {
        for (Observer observer : observers) {
            observer.update(event);
        }
    }
}

// Observers concretos
class EmailListener implements Observer {
    public void update(String event) {
        System.out.println("Email enviado para: " + event);
    }
}

class LogListener implements Observer {
    public void update(String event) {
        System.out.println("Log registrado: " + event);
    }
}

// Uso
public class Main {
    public static void main(String[] args) {
        EventManager eventManager = new EventManager();

        eventManager.subscribe(new EmailListener());
        eventManager.subscribe(new LogListener());

        eventManager.notifyObservers("Novo usuário cadastrado");
    }
}
```

**Casos de Uso:**

- Sistemas de eventos
- MVC (Model-View-Controller)
- Notificações em tempo real

---

### 🔸 4. Strategy - Algoritmos Intercambiáveis

**Problema:** Escolher algoritmo em tempo de execução.

```java
 // Interface da estratégia
interface PaymentStrategy {
    void pay(double amount);
}

// Estratégias concretas
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void pay(double amount) {
        System.out.println("Pagamento de R$ " + amount + " via cartão: " + cardNumber);
    }
}

class PixPayment implements PaymentStrategy {
    private String pixKey;

    public PixPayment(String pixKey) {
        this.pixKey = pixKey;
    }

    public void pay(double amount) {
        System.out.println("Pagamento de R$ " + amount + " via PIX: " + pixKey);
    }
}

// Contexto
class PaymentProcessor {
    private PaymentStrategy strategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void processPayment(double amount) {
        if (strategy == null) {
            throw new IllegalStateException("Estratégia de pagamento não definida");
        }
        strategy.pay(amount);
    }
}

// Uso
public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();

        // Pagamento via cartão
        processor.setPaymentStrategy(new CreditCardPayment("1234-5678"));
        processor.processPayment(100.0);

        // Mudança para PIX
        processor.setPaymentStrategy(new PixPayment("user@email.com"));
        processor.processPayment(50.0);
    }
}
```

---

### 🔸 5. Builder - Construção Complexa

**Problema:** Construir objetos complexos passo a passo.

```java
// Produto complexo
class Computer {
    private String cpu;
    private String ram;
    private String storage;
    private String gpu;
    private boolean hasWifi;

    // Construtor privado
    private Computer(ComputerBuilder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.hasWifi = builder.hasWifi;
    }

    @Override
    public String toString() {
        return "Computer{cpu='" + cpu + "', ram='" + ram +
               "', storage='" + storage + "', gpu='" + gpu +
               "', hasWifi=" + hasWifi + "}";
    }

    // Builder interno
    public static class ComputerBuilder {
        private String cpu;
        private String ram;
        private String storage;
        private String gpu;
        private boolean hasWifi;

        public ComputerBuilder setCpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public ComputerBuilder setRam(String ram) {
            this.ram = ram;
            return this;
        }

        public ComputerBuilder setStorage(String storage) {
            this.storage = storage;
            return this;
        }

        public ComputerBuilder setGpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public ComputerBuilder setWifi(boolean hasWifi) {
            this.hasWifi = hasWifi;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }
}

// Uso
public class Main {
    public static void main(String[] args) {
        Computer gamingPC = new Computer.ComputerBuilder()
            .setCpu("Intel i9")
            .setRam("32GB")
            .setStorage("1TB SSD")
            .setGpu("RTX 4080")
            .setWifi(true)
            .build();

        System.out.println(gamingPC);
    }
}
```

---

## 📊 Resumo dos Padrões por Categoria

### 🏗️ **Padrões Criacionais**

| Padrão                  | Propósito            | Quando Usar                    |
| ------------------------ | --------------------- | ------------------------------ |
| **Singleton**      | Uma instância única | Cache, Config, Logger          |
| **Factory Method** | Criação flexível   | Diferentes tipos de objetos    |
| **Builder**        | Construção complexa | Objetos com muitos parâmetros |

### 🔧 **Padrões Estruturais**

| Padrão             | Propósito                | Quando Usar            |
| ------------------- | ------------------------- | ---------------------- |
| **Adapter**   | Compatibilidade           | APIs incompatíveis    |
| **Decorator** | Adicionar funcionalidades | Extensão sem herança |
| **Facade**    | Interface simplificada    | Sistemas complexos     |

### 🎭 **Padrões Comportamentais**

| Padrão            | Propósito                  | Quando Usar                 |
| ------------------ | --------------------------- | --------------------------- |
| **Observer** | Notificação automática   | Eventos e mudanças         |
| **Strategy** | Algoritmos intercambiáveis | Múltiplas implementações |
| **Command**  | Encapsular requisições    | Undo/Redo, Filas            |

---

## 🎯 Dicas para Entrevistas

### ✅ **O que Mencionar**

- **Problema que resolve** cada padrão
- **Trade-offs** e quando NÃO usar
- **Exemplos práticos** do dia a dia
- **Relação com frameworks** (Spring, etc.)

### ❌ **Armadilhas Comuns**

- **Over-engineering** - usar padrões desnecessariamente
- **Decorar sem entender** o propósito
- **Não saber quando NÃO usar**
- **Confundir padrões** similares

### 💡 **Perguntas Frequentes**

1. "Qual a diferença entre Factory e Builder?"
2. "Quando você usaria Singleton vs Static?"
3. "Como implementar Observer thread-safe?"
4. "Qual padrão você usaria para cache?"

---

## 🚀 Próximos Passos

### 📖 **Estudo Recomendado**

1. **Pratique implementações** dos padrões básicos
2. **Identifique padrões** em frameworks que usa
3. **Refatore código existente** aplicando padrões
4. **Estude anti-patterns** para evitar armadilhas

### 🔗 **Recursos Adicionais**

- **Gang of Four** - Livro clássico de Design Patterns
- **Head First Design Patterns** - Abordagem prática
- **Refactoring Guru** - Exemplos visuais online
- **Spring Framework** - Padrões em ação

---

## 🎓 Conclusão

Design Patterns são **ferramentas essenciais** para desenvolvedores que querem:

- ✅ **Escrever código** mais limpo e manutenível
- ✅ **Comunicar-se efetivamente** com outros desenvolvedores
- ✅ **Destacar-se em entrevistas** técnicas
- ✅ **Evoluir para cargos** de arquitetura

**Lembre-se:** Padrões são soluções, não regras. Use com sabedoria! 🧠

---

*Desenvolvido para preparação em entrevistas técnicas e crescimento profissional em Java.*
