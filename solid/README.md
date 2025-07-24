# 🏗️ SOLID

[![OOP](https://img.shields.io/badge/paradigm-OOP-blue.svg)]()
[![Design](https://img.shields.io/badge/focus-Design-green.svg)]()
[![Best Practices](https://img.shields.io/badge/practices-SOLID-orange.svg)]()

> Guia completo dos princípios SOLID para desenvolvimento orientado a objetos e preparação para entrevistas técnicas.

---

## 🎯 Por que SOLID é Fundamental?

### 💼 **Importância Profissional**
- **Base da programação OO** - Fundamento para código de qualidade
- **Requisito em entrevistas** - Pergunta obrigatória para vagas pleno/sênior
- **Manutenibilidade** - Código mais fácil de modificar e estender
- **Testabilidade** - Facilita criação de testes unitários

### 🚀 **Benefícios Técnicos**
- **Baixo acoplamento** - Classes independentes e reutilizáveis
- **Alta coesão** - Responsabilidades bem definidas
- **Extensibilidade** - Fácil adição de novas funcionalidades
- **Legibilidade** - Código mais claro e compreensível

---

## 📋 Os 5 Princípios SOLID

### 🔸 **S - Single Responsibility Principle (SRP)**
**"Uma classe deve ter apenas um motivo para mudar"**

#### ❌ **Violação do SRP:**
```java
// Classe com múltiplas responsabilidades
public class User {
    private String name;
    private String email;
    
    // Responsabilidade 1: Gerenciar dados do usuário
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    
    // Responsabilidade 2: Validação
    public boolean isValidEmail() {
        return email.contains("@");
    }
    
    // Responsabilidade 3: Persistência
    public void saveToDatabase() {
        // Código para salvar no banco
    }
    
    // Responsabilidade 4: Notificação
    public void sendWelcomeEmail() {
        // Código para enviar email
    }
}
```

#### ✅ **Aplicando SRP:**
```java
// Cada classe tem uma única responsabilidade
public class User {
    private String name;
    private String email;
    
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    // Getters e setters apenas
    public String getName() { return name; }
    public String getEmail() { return email; }
}

public class EmailValidator {
    public boolean isValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}

public class UserRepository {
    public void save(User user) {
        // Lógica de persistência
    }
}

public class EmailService {
    public void sendWelcomeEmail(User user) {
        // Lógica de envio de email
    }
}
```

---

### 🔸 **O - Open/Closed Principle (OCP)**
**"Classes devem estar abertas para extensão, mas fechadas para modificação"**

#### ❌ **Violação do OCP:**
```java
public class DiscountCalculator {
    public double calculateDiscount(String customerType, double amount) {
        if (customerType.equals("REGULAR")) {
            return amount * 0.05;
        } else if (customerType.equals("PREMIUM")) {
            return amount * 0.10;
        } else if (customerType.equals("VIP")) {
            return amount * 0.20;
        }
        return 0;
    }
}
// Problema: Para adicionar novo tipo, precisa modificar a classe
```

#### ✅ **Aplicando OCP:**
```java
// Interface para estratégia de desconto
public interface DiscountStrategy {
    double calculateDiscount(double amount);
}

// Implementações específicas
public class RegularCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.05;
    }
}

public class PremiumCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.10;
    }
}

public class VipCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.20;
    }
}

// Calculadora que usa estratégias
public class DiscountCalculator {
    public double calculateDiscount(DiscountStrategy strategy, double amount) {
        return strategy.calculateDiscount(amount);
    }
}

// Uso
DiscountCalculator calculator = new DiscountCalculator();
double discount = calculator.calculateDiscount(new PremiumCustomerDiscount(), 100.0);
```

---

### 🔸 **L - Liskov Substitution Principle (LSP)**
**"Objetos de uma superclasse devem ser substituíveis por objetos de suas subclasses"**

#### ❌ **Violação do LSP:**
```java
public class Bird {
    public void fly() {
        System.out.println("Flying...");
    }
}

public class Penguin extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly!");
    }
}

// Problema: Penguin não pode substituir Bird sem quebrar o comportamento
public void makeBirdFly(Bird bird) {
    bird.fly(); // Pode lançar exceção se for Penguin
}
```

#### ✅ **Aplicando LSP:**
```java
// Hierarquia correta
public abstract class Bird {
    public abstract void move();
}

public class FlyingBird extends Bird {
    @Override
    public void move() {
        fly();
    }
    
    public void fly() {
        System.out.println("Flying...");
    }
}

public class SwimmingBird extends Bird {
    @Override
    public void move() {
        swim();
    }
    
    public void swim() {
        System.out.println("Swimming...");
    }
}

public class Eagle extends FlyingBird {
    // Herda comportamento de voo
}

public class Penguin extends SwimmingBird {
    // Herda comportamento de natação
}

// Agora qualquer Bird pode ser substituída
public void makeBirdMove(Bird bird) {
    bird.move(); // Sempre funciona corretamente
}
```

---

### 🔸 **I - Interface Segregation Principle (ISP)**
**"Clientes não devem ser forçados a depender de interfaces que não usam"**

#### ❌ **Violação do ISP:**
```java
// Interface muito grande
public interface Worker {
    void work();
    void eat();
    void sleep();
    void attendMeeting();
    void writeCode();
    void designUI();
}

// Robot é forçado a implementar métodos que não usa
public class Robot implements Worker {
    @Override
    public void work() { /* implementa */ }
    
    @Override
    public void eat() { 
        throw new UnsupportedOperationException("Robots don't eat");
    }
    
    @Override
    public void sleep() { 
        throw new UnsupportedOperationException("Robots don't sleep");
    }
    
    // ... outros métodos não aplicáveis
}
```

#### ✅ **Aplicando ISP:**
```java
// Interfaces segregadas
public interface Workable {
    void work();
}

public interface Eatable {
    void eat();
}

public interface Sleepable {
    void sleep();
}

public interface Meetable {
    void attendMeeting();
}

// Implementações específicas
public class Human implements Workable, Eatable, Sleepable, Meetable {
    @Override
    public void work() { /* implementa */ }
    
    @Override
    public void eat() { /* implementa */ }
    
    @Override
    public void sleep() { /* implementa */ }
    
    @Override
    public void attendMeeting() { /* implementa */ }
}

public class Robot implements Workable {
    @Override
    public void work() { /* implementa apenas o que faz sentido */ }
}
```

---

### 🔸 **D - Dependency Inversion Principle (DIP)**
**"Módulos de alto nível não devem depender de módulos de baixo nível. Ambos devem depender de abstrações"**

#### ❌ **Violação do DIP:**
```java
// Dependência direta de implementação concreta
public class OrderService {
    private MySQLOrderRepository repository; // Dependência concreta
    
    public OrderService() {
        this.repository = new MySQLOrderRepository(); // Acoplamento forte
    }
    
    public void createOrder(Order order) {
        repository.save(order);
    }
}

public class MySQLOrderRepository {
    public void save(Order order) {
        // Salva no MySQL
    }
}
```

#### ✅ **Aplicando DIP:**
```java
// Abstração
public interface OrderRepository {
    void save(Order order);
    Order findById(Long id);
}

// Implementações concretas
public class MySQLOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {
        // Implementação MySQL
    }
    
    @Override
    public Order findById(Long id) {
        // Implementação MySQL
    }
}

public class MongoOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {
        // Implementação MongoDB
    }
    
    @Override
    public Order findById(Long id) {
        // Implementação MongoDB
    }
}

// Serviço depende da abstração
public class OrderService {
    private final OrderRepository repository;
    
    // Injeção de dependência
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }
    
    public void createOrder(Order order) {
        repository.save(order);
    }
}

// Configuração (Spring Boot exemplo)
@Configuration
public class AppConfig {
    @Bean
    public OrderRepository orderRepository() {
        return new MySQLOrderRepository(); // Pode ser facilmente trocado
    }
    
    @Bean
    public OrderService orderService(OrderRepository repository) {
        return new OrderService(repository);
    }
}
```

---

## 🎯 Preparação para Entrevistas

### ✅ **Perguntas Frequentes**

#### **Conceitual**
**P:** "Explique o princípio da Responsabilidade Única"
**R:** "Uma classe deve ter apenas um motivo para mudar. Isso significa que cada classe deve ter uma única responsabilidade bem definida. Por exemplo, uma classe User deve apenas gerenciar dados do usuário, não validação, persistência ou envio de emails."

#### **Prática**
**P:** "Como você aplicaria o Open/Closed Principle neste código?"
**R:** "Usaria o padrão Strategy para permitir extensão sem modificação. Criaria uma interface para o comportamento variável e implementações específicas, permitindo adicionar novos comportamentos sem alterar o código existente."

#### **Identificação**
**P:** "Identifique violações SOLID neste código"
**R:** Analise sistematicamente:
- SRP: A classe tem múltiplas responsabilidades?
- OCP: Precisa modificar código para adicionar funcionalidades?
- LSP: Subclasses podem substituir a superclasse?
- ISP: Interface tem métodos não utilizados?
- DIP: Há dependências diretas de implementações concretas?

---

## 📈 Roadmap de Aprendizado

### 🥇 **Nível Iniciante (1-2 semanas)**
1. Entender cada princípio individualmente
2. Identificar violações em código simples
3. Praticar refatoração básica
4. Estudar exemplos práticos

### 🥈 **Nível Intermediário (2-4 semanas)**
1. Aplicar SOLID em projetos reais
2. Combinar princípios efetivamente
3. Usar padrões de design que seguem SOLID
4. Code review focado em SOLID

### 🥉 **Nível Avançado (1-2 meses)**
1. Arquitetar sistemas seguindo SOLID
2. Ensinar e mentoring sobre SOLID
3. Balancear SOLID com pragmatismo
4. Contribuir para discussões arquiteturais

---

## 🎓 Conclusão

SOLID é **fundamental** para desenvolvedores porque:

- ✅ **Base da programação OO** - Princípios universais
- ✅ **Código de qualidade** - Manutenível e extensível
- ✅ **Preparação para entrevistas** - Pergunta obrigatória
- ✅ **Crescimento profissional** - Diferencial técnico
- ✅ **Trabalho em equipe** - Código mais colaborativo

**Lembre-se:** SOLID são princípios, não regras absolutas. Use com bom senso! 🧠

---

*Desenvolvido para preparação em entrevistas técnicas e crescimento profissional em SOLID Principles.*
