# Desafios de Entrevista Técnica

Este repositório contém implementações de algoritmos e estruturas de dados fundamentais, organizados por tópicos e comumente utilizados em entrevistas técnicas para posições de desenvolvimento de software.

## 📁 Estrutura do Projeto

### 🧮 [Algorithms](./algorithms/)
**LRU Cache Implementation**
- Implementação elegante usando herança de `LinkedHashMap`
- Cache com remoção automática do elemento menos recentemente usado
- Demonstra uso inteligente de estruturas de dados existentes
- **Tecnologias**: Java, Maven, JUnit
- **Conceitos**: Herança, Generics, Data Structures

### ➕ [Two Sums](./two-sums/)
**Problema Clássico Two Sum**
- Duas soluções: Força Bruta O(n²) vs Hash Table O(n)
- Análise comparativa de complexidade e performance
- Exemplo fundamental de otimização algorítmica
- **Tecnologias**: Java
- **Conceitos**: Algoritmos, Hash Tables, Otimização

### 🏛️ [Roman to Integer](./roman-to-int/)
**Conversor de Números Romanos**
- Algoritmo eficiente para conversão de números romanos para inteiros
- Implementa regras de subtração (IV=4, IX=9, XL=40, etc.)
- Processamento linear com análise de caracteres adjacentes
- **Tecnologias**: Java
- **Conceitos**: String Processing, Switch Statements, Algoritmos

### 🚦 [Rate Limiter](./rate-limiter/)
**Algoritmos de Rate Limiting**
- **Sliding Window**: Controle preciso com timestamps
- **Token Bucket**: Permite burst com reabastecimento
- Implementações thread-safe para sistemas distribuídos
- **Tecnologias**: Java, Maven, JUnit
- **Conceitos**: Concorrência, Rate Limiting, Thread Safety

### 🧵 [Concurrency](./concurrency/)
**Padrões de Concorrência**
- **Producer-Consumer**: Coordenação com BlockingQueue
- **Simple Thread Pool**: Execução paralela com workers
- Implementações com graceful shutdown e resource management
- **Tecnologias**: Java, Maven, JUnit
- **Conceitos**: Multi-threading, Synchronization, Design Patterns

## 🎯 Objetivos do Repositório

Este repositório foi criado para:

- **📚 Estudo**: Referência para algoritmos fundamentais
- **💼 Entrevistas**: Preparação para entrevistas técnicas
- **🔧 Implementação**: Exemplos práticos de código limpo
- **📖 Educação**: Documentação detalhada com explicações

## 🚀 Como Usar

### Pré-requisitos
- **Java 17+**
- **Maven 3.6+**

### Executando os Projetos

#### Projetos Maven (algorithms, rate-limiter, concurrency)
```bash
cd <projeto>
mvn compile
mvn exec:java -Dexec.mainClass="<MainClass>"
mvn test
```

#### Projetos Java Simples (two-sums, roman-to-int)
```bash
# Two Sums
cd two-sums
javac TwoSums.java
java TwoSums

# Roman to Integer
cd roman-to-int
javac RomanToInt.java
java RomanToInt
```

## 📊 Complexidade dos Algoritmos

| Projeto | Algoritmo | Tempo | Espaço | Dificuldade |
|---------|-----------|-------|--------|-------------|
| **LRU Cache** | LinkedHashMap | O(1) | O(n) | ⭐⭐ |
| **Two Sums** | Hash Table | O(n) | O(n) | ⭐ |
| **Two Sums** | Força Bruta | O(n²) | O(1) | ⭐ |
| **Roman to Integer** | String Processing | O(n) | O(1) | ⭐ |
| **Rate Limiter** | Sliding Window | O(n) | O(n) | ⭐⭐⭐ |
| **Rate Limiter** | Token Bucket | O(1) | O(1) | ⭐⭐⭐ |
| **Concurrency** | Producer-Consumer | - | O(k) | ⭐⭐ |
| **Concurrency** | Thread Pool | - | O(n) | ⭐⭐⭐ |

## 🎓 Conceitos Abordados

### Estruturas de Dados
- **Hash Tables**: HashMap, LinkedHashMap
- **Queues**: ArrayDeque, BlockingQueue, LinkedBlockingQueue
- **Collections**: Generics, Iterator patterns

### Algoritmos
- **Busca**: Hash-based lookup, Linear search
- **String Processing**: Roman numeral parsing, Character analysis
- **Cache**: LRU eviction, Access patterns
- **Rate Limiting**: Sliding window, Token bucket

### Concorrência
- **Thread Safety**: Synchronized, AtomicLong, volatile
- **Patterns**: Producer-Consumer, Thread Pool
- **Resource Management**: AutoCloseable, Graceful shutdown

### Design Patterns
- **Template Method**: removeEldestEntry override
- **Factory**: Thread creation patterns
- **Resource Management**: try-with-resources

## 📈 Níveis de Dificuldade

### ⭐ Iniciante
- **Two Sums**: Problema fundamental de arrays e hash tables
- **Roman to Integer**: Processamento de strings e lógica condicional
- Conceitos básicos de otimização algorítmica

### ⭐⭐ Intermediário
- **LRU Cache**: Uso avançado de estruturas de dados
- **Producer-Consumer**: Introdução à concorrência

### ⭐⭐⭐ Avançado
- **Rate Limiter**: Algoritmos para sistemas distribuídos
- **Thread Pool**: Gerenciamento avançado de concorrência

## 🔗 Links Úteis

- [Java Documentation](https://docs.oracle.com/en/java/)
- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Java Concurrency in Practice](https://jcip.net/)

## 📝 Próximos Passos

Possíveis extensões para este repositório:

- **🌳 Tree Algorithms**: Binary Search Tree, AVL, Red-Black
- **📊 Graph Algorithms**: BFS, DFS, Dijkstra, A*
- **🔄 Sorting**: QuickSort, MergeSort, HeapSort
- **🧮 Dynamic Programming**: Fibonacci, Knapsack, LCS
- **🌐 Distributed Systems**: Consistent Hashing, Raft Algorithm

## 🤝 Contribuições

Contribuições são bem-vindas! Por favor:

1. Fork o repositório
2. Crie uma branch para sua feature
3. Adicione testes para novas implementações
4. Mantenha a documentação atualizada
5. Submeta um Pull Request

## 📄 Licença

Este projeto é open source e está disponível sob a [MIT License](LICENSE).

---

*Desenvolvido para estudo e preparação para entrevistas técnicas. Cada implementação inclui análise de complexidade, casos de uso e boas práticas de desenvolvimento.*
