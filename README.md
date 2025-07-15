# 🚀 Desafios de Entrevista Técnica

[![Java](https://img.shields.io/badge/language-Java-blue.svg)](https://www.java.com/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

> Coleção abrangente de algoritmos, estruturas de dados e boas práticas para preparação em entrevistas técnicas de desenvolvimento de software.

---

## 📁 Estrutura do Repositório

### 🧮 [Códigos](./codigos/)
**Algoritmos e Estruturas de Dados Fundamentais**

Implementações completas com análise de complexidade e múltiplas abordagens:

- **[LRU Cache](./codigos/algorithms/)** - Cache inteligente com LinkedHashMap
- **[Two Sums](./codigos/two-sums/)** - Força bruta vs Hash Table O(n²) → O(n)
- **[Roman to Integer](./codigos/roman-to-int/)** - Conversão eficiente de números romanos
- **[Fibonacci](./codigos/fibonnaci/)** - 4 implementações: Recursiva, Memoização, DP, Otimizada
- **[Rate Limiter](./codigos/rate-limiter/)** - Sliding Window e Token Bucket
- **[Concurrency](./codigos/concurrency/)** - Producer-Consumer e Thread Pool

### 📚 [Extras](./extras/)
**Materiais Complementares**

- **[Clean Code](./extras/clean-code/)** - Resumo prático dos princípios de código limpo

---

## 🎯 Objetivos

Este repositório foi criado para:

- **📖 Estudo**: Referência completa de algoritmos fundamentais
- **💼 Entrevistas**: Preparação estruturada para entrevistas técnicas
- **🔧 Implementação**: Exemplos de código limpo e bem documentado
- **📊 Análise**: Comparação de complexidades e trade-offs

---

## 🚀 Quick Start

### Pré-requisitos
- **Java 17+**
- **Maven 3.6+** (para projetos com dependências)

### Executar Algoritmos

```bash
# Algoritmos simples
cd codigos/<projeto>
javac *.java
java <MainClass>

# Projetos Maven
cd codigos/<projeto>
mvn compile exec:java
mvn test
```

### Exemplos Rápidos

```bash
# Fibonacci com múltiplas implementações
cd codigos/fibonnaci
javac Fibonacci.java
java Fibonacci

# Two Sums - comparação de abordagens
cd codigos/two-sums
javac TwoSums.java
java TwoSums

# LRU Cache com testes
cd codigos/algorithms
mvn test
```

---

## 📊 Visão Geral dos Algoritmos

| Projeto | Melhor Caso | Pior Caso | Espaço | Nível |
|---------|-------------|-----------|--------|-------|
| **Two Sums** | O(n) | O(n²) | O(n) | ⭐ |
| **Roman to Int** | O(n) | O(n) | O(1) | ⭐ |
| **Fibonacci** | O(n) | O(2^n) | O(1) | ⭐⭐ |
| **LRU Cache** | O(1) | O(1) | O(n) | ⭐⭐ |
| **Rate Limiter** | O(1) | O(n) | O(n) | ⭐⭐⭐ |
| **Concurrency** | - | - | O(n) | ⭐⭐⭐ |

---

## 🎓 Níveis de Aprendizado

### ⭐ **Iniciante**
Conceitos fundamentais de algoritmos e estruturas de dados
- Arrays, Hash Tables, String Processing
- Análise básica de complexidade
- Otimização simples

### ⭐⭐ **Intermediário** 
Técnicas avançadas de otimização
- Memoização e Programação Dinâmica
- Estruturas de dados especializadas
- Design patterns básicos

### ⭐⭐⭐ **Avançado**
Sistemas distribuídos e concorrência
- Thread safety e sincronização
- Rate limiting para alta escala
- Arquiteturas robustas

---

## 🔗 Navegação Rápida

### Por Conceito
- **Recursão**: [Fibonacci](./codigos/fibonnaci/)
- **Hash Tables**: [Two Sums](./codigos/two-sums/)
- **String Processing**: [Roman to Integer](./codigos/roman-to-int/)
- **Cache**: [LRU Cache](./codigos/algorithms/)
- **Concorrência**: [Rate Limiter](./codigos/rate-limiter/), [Concurrency](./codigos/concurrency/)

### Por Complexidade
- **O(1)**: LRU Cache, Token Bucket
- **O(n)**: Two Sums (otimizado), Fibonacci (otimizado)
- **O(n²)**: Two Sums (força bruta)
- **O(2^n)**: Fibonacci (recursivo)

---

## 📈 Próximos Tópicos

Extensões planejadas:
- **🌳 Árvores**: BST, AVL, Red-Black
- **📊 Grafos**: BFS, DFS, Dijkstra
- **🔄 Ordenação**: QuickSort, MergeSort
- **🧮 DP Avançado**: Knapsack, LCS

---

## 🤝 Contribuições

Contribuições são bem-vindas! 

1. Fork o repositório
2. Crie uma branch: `git checkout -b feature/nova-implementacao`
3. Commit suas mudanças: `git commit -m 'Add: nova implementação'`
4. Push para a branch: `git push origin feature/nova-implementacao`
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 🎯 Sobre

Desenvolvido para estudo e preparação para entrevistas técnicas. Cada implementação inclui:

- ✅ **Código limpo** e bem documentado
- ✅ **Análise de complexidade** detalhada
- ✅ **Múltiplas abordagens** quando aplicável
- ✅ **Casos de teste** e exemplos práticos
- ✅ **Documentação** educativa
