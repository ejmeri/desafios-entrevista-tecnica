# Clean Code – Resumo Prático em Java

[![Java](https://img.shields.io/badge/language-Java-blue.svg)](https://www.java.com/)

> Resumo dos princípios essenciais do livro **Clean Code** (Robert C. Martin), focado em exemplos práticos de Java.

---

## 📝 Sumário

* [O que é Código Limpo?](#o-que-é-código-limpo)
* [Nomes Significativos](#nomes-significativos)
* [Funções Pequenas e Objetivas](#funções-pequenas-e-objetivas)
* [Comentários com Propósito](#comentários-com-propósito)
* [Estrutura e Estilo](#estrutura-e-estilo)
* [Objetos, Dados e Erros](#objetos-dados-e-erros)
* [Code Smells & Refatoração](#code-smells--refatoração)
* [Testes](#testes)
* [Concorrência e Design](#concorrência-e-design)
* [Checklist de Código Limpo](#checklist-de-código-limpo)
* [Conclusão](#conclusão)
* [Exemplos Extras](#exemplos-extras)

---

## O que é Código Limpo?

* Código claro, direto e que transmite a intenção do autor.
* Fácil de ler, manter e modificar.
* **Regra dos Escoteiros:** “Deixe o código um pouco melhor do que encontrou”.

---

## Nomes Significativos

* Explique por que existe, o que faz, como usar.
* Evite abreviações e inconsistências, como: `user`, `userObj`, `userObject`.
* **Exemplo:**

  ```java
  // Ruim
  int d;

  // Bom
  int daysSinceLastLogin;
  ```

---

## Funções Pequenas e Objetivas

* Devem ser pequenas, fazer apenas uma coisa, poucos parâmetros e sem efeitos colaterais.
* Evite flags como parâmetro; prefira funções específicas.
* **Exemplo:**

  ```java
  // Ruim
  void process(Data data, boolean isA) {
      if (isA) processA(data);
      else processB(data);
  }

  // Bom
  void processA(Data data) { ... }
  void processB(Data data) { ... }
  ```

---

## Comentários com Propósito

* Use comentários apenas para explicar intenção, avisos ou pontos legais/TODO.
* Não comente o óbvio – prefira bons nomes de função.
* **Exemplo:**

  ```java
  // Ruim:
  // incrementa contador
  i = i + 1;

  // Melhor:
  incrementCounter();
  ```

---

## Estrutura e Estilo

* Estruture como um jornal: ideias principais no topo, detalhes abaixo.
* Declare variáveis perto de onde são usadas; agrupe funções relacionadas.
* Linhas curtas (\~100 colunas).

### Constantes ao invés de números mágicos

```java
// Ruim:
if (total > 1000) discount = total * 0.1;

// Bom:
private static final double DISCOUNT_THRESHOLD = 1000;
private static final double DISCOUNT_RATE = 0.1;

if (total > DISCOUNT_THRESHOLD) discount = total * DISCOUNT_RATE;
```

---

## Objetos, Dados e Erros

### Objetos vs Estruturas de Dados

* Objetos encapsulam dados e comportamento; estruturas de dados carregam apenas dados.
* **Exemplo:**

  ```java
  class User {
      private int age;
      boolean isAdult() { return age >= 18; }
  }
  ```

### Tratamento de Erros

* Prefira lançar exceções a retornar `null` ou códigos de erro.
* **Exemplo:**

  ```java
  // Ruim:
  if (x == null) return -1;

  // Bom:
  if (x == null)
      throw new IllegalArgumentException("x não pode ser nulo");
  ```

---

## Code Smells & Refatoração

* **Duplicação:** extraia métodos ou classes.
* **Complexidade desnecessária:** quebre em funções menores.
* **Rigidez/Fragilidade:** mudanças não devem quebrar outras partes.
* **Opacidade:** código deve ser fácil de entender.

### Exemplos Práticos

#### If/Else longos → Polimorfismo

```java
// Ruim:
if (type == ID) handleID();
else if (type == PASSPORT) handlePassport();

// Bom:
documentHandler.handle(document);
```

#### Encapsular condições

```java
// Ruim:
if (size() == 0) { ... }

// Bom:
if (isEmpty()) { ... }
```

#### Separar responsabilidades

* Transforme classes grandes em componentes menores com testes e refatoração contínua.

---

## Testes

* Tão importantes quanto o código de produção.
* Siga TDD: escreva o teste, implemente o mínimo, refatore.
* Testes devem ser pequenos, rápidos, independentes e repetíveis.
* Priorize a qualidade dos testes.

---

## Concorrência e Design

* Para condições complexas, extraia funções bem nomeadas (ex: `isValidForProcessing()`).
* Prefira polimorfismo a switch/case; facilita a extensão.
* Use adaptadores para isolar dependências externas e facilitar testes.

---

## Checklist de Código Limpo

* [x] Nomes claros e consistentes
* [x] Funções pequenas, objetivas, sem flags
* [x] Formatação tipo jornal
* [x] Poucos comentários, só quando necessários
* [x] Tratamento de erros com exceções
* [x] Refatoração constante
* [x] Testes bem elaborados
* [x] Eliminação de smells: duplicação, complexidade, rigidez, opacidade

---

## Conclusão

Este resumo destaca os principais ensinamentos de *Clean Code*: melhoria contínua, nomeação clara, organização, testes robustos e design bem estruturado. Princípios atemporais, aplicáveis a qualquer projeto ou linguagem.

---

## Exemplos Extras

### Exemplo: Classe com Responsabilidade Única (SRP)

```java
// Ruim: faz de tudo um pouco
class UserManager {
    void saveUser(User user) { ... }
    void sendWelcomeEmail(User user) { ... }
    boolean isAdult(User user) { ... }
}

// Bom: separa responsabilidades
class UserRepository {
    void save(User user) { ... }
}

class EmailService {
    void sendWelcomeEmail(User user) { ... }
}

class UserService {
    boolean isAdult(User user) { ... }
}
```

### Exemplo: Builder para evitar muitos parâmetros

```java
// Ruim
User user = new User("Ana", "ana@email.com", "Rua A", 29, true, "99999-9999");

// Bom - usando Builder
User user = new UserBuilder()
    .withName("Ana")
    .withEmail("ana@email.com")
    .withAddress("Rua A")
    .withAge(29)
    .withActive(true)
    .withPhone("99999-9999")
    .build();
```

### Exemplo: Teste Simples (JUnit)

```java
@Test
void shouldCalculateTotalCorrectly() {
    Order order = new Order();
    order.addItem(new Item("Livro", 30.0));
    order.addItem(new Item("Caneta", 5.0));

    double total = order.calculateTotal();

    assertEquals(35.0, total);
}
```

---

## Referências

* Robert C. Martin, *Clean Code: A Handbook of Agile Software Craftsmanship*.
* [https://refactoring.guru/pt-br/refactoring/smells](https://refactoring.guru/pt-br/refactoring/smells)
* [https://www.baeldung.com/java-clean-code](https://www.baeldung.com/java-clean-code)
