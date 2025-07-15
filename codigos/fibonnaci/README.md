# Sequência de Fibonacci

Este projeto implementa o cálculo da **Sequência de Fibonacci** usando recursão simples, demonstrando um algoritmo clássico fundamental em ciência da computação.

## 📋 Visão Geral

A Sequência de Fibonacci é uma série matemática onde cada número é a soma dos dois números anteriores. A sequência tradicionalmente começa com 0 e 1:

```
F(0) = 0
F(1) = 1
F(n) = F(n-1) + F(n-2) para n > 1
```

**Sequência**: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, ...

## 🏗️ Implementação

### Algoritmo Recursivo

```java
public class Fibonacci {
    static long fibo(int n) {
        if (n < 2)
            return n;
        return fibo(n - 1) + fibo(n - 2);
    }
}
```

**Como funciona:**

1. **Caso base**: Se `n < 2`, retorna `n` (0 para n=0, 1 para n=1)
2. **Caso recursivo**: Para `n ≥ 2`, calcula `F(n-1) + F(n-2)`
3. **Recursão**: Cada chamada gera duas novas chamadas até atingir os casos base

### Exemplo de Execução

```java
public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
        System.out.println(fibo(i));
    }
}
```

**Output:**

```
0
1
1
2
3
5
8
13
21
34
```

## 📊 Análise de Complexidade

### ⏱️ Complexidade de Tempo: O(2^n)

- Cada chamada recursiva gera duas novas chamadas
- Forma uma árvore binária de chamadas com altura n
- Número total de chamadas ≈ 2^n

### 💾 Complexidade de Espaço: O(n)

- Profundidade máxima da pilha de recursão é n
- Cada chamada recursiva adiciona um frame à pilha

### 📈 Árvore de Recursão (exemplo para n=5)

```
                    fibo(5)
                   /        \
              fibo(4)        fibo(3)
             /      \       /      \
        fibo(3)   fibo(2) fibo(2) fibo(1)
       /     \    /    \   /    \
   fibo(2) fibo(1) fibo(1) fibo(0) fibo(1) fibo(0)
   /    \
fibo(1) fibo(0)
```

## 🚀 Como Executar

```bash
# Compilar
javac Fibonacci.java

# Executar
java Fibonacci
```

## ⚠️ Limitações da Implementação Atual

### 1. **Performance Exponencial**

- Para valores grandes de n, o tempo de execução cresce exponencialmente
- `fibo(40)` já demora vários segundos
- `fibo(50)` pode demorar minutos

### 2. **Recálculos Desnecessários**

- Valores como `fibo(2)` são calculados múltiplas vezes
- Não há cache/memoização dos resultados

### 3. **Stack Overflow**

- Para valores muito grandes, pode estourar a pilha de recursão
- Limitado pela profundidade máxima da JVM

## 🔧 Possíveis Otimizações

### 1. **Memoização** **(Top-Down)**

```java
private static Map<Integer, Long> memo = new HashMap<>();

static long fiboMemo(int n) {
    if (n < 2) return n;
    if (memo.containsKey(n)) return memo.get(n);
  
    long result = fiboMemo(n - 1) + fiboMemo(n - 2);
    memo.put(n, result);
    return result;
}
```

**Complexidade**: O(n) tempo, O(n) espaço

### 2. **Programação Dinâmica (Bottom-Up)**

```java
static long fiboDp(int n) {
    if (n < 2) return n;
  
    long[] dp = new long[n + 1];
    dp[0] = 0;
    dp[1] = 1;
  
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }
  
    return dp[n];
}
```

**Complexidade**: O(n) tempo, O(n) espaço

### 3. **Otimização de Espaço**

```java
static long fiboOptimized(int n) {
    if (n < 2) return n;
  
    long prev2 = 0, prev1 = 1;
  
    for (int i = 2; i <= n; i++) {
        long current = prev1 + prev2;
        prev2 = prev1;
        prev1 = current;
    }
  
    return prev1;
}
```

**Complexidade**: O(n) tempo, O(1) espaço

## 📚 Conceitos Demonstrados

- **Recursão**: Implementação clássica de algoritmo recursivo
- **Casos Base**: Condições de parada para recursão
- **Complexidade Exponencial**: Exemplo de algoritmo ineficiente
- **Matemática**: Sequência de Fibonacci e suas propriedades
- **Análise Algorítmica**: Trade-offs entre simplicidade e eficiência

## 🎯 Aplicações da Sequência de Fibonacci

### Matemática e Ciência

- **Razão Áurea**: Limite da razão F(n+1)/F(n) ≈ 1.618
- **Natureza**: Espirais de caracóis, pétalas de flores, galhos de árvores
- **Arte**: Proporções em arquitetura e design

### Ciência da Computação

- **Algoritmos**: Exemplo clássico para ensinar recursão
- **Estruturas de Dados**: Fibonacci Heaps
- **Análise de Complexidade**: Demonstração de crescimento exponencial

## 🎓 Níveis de Dificuldade

### ⭐ **Implementação Básica** (Atual)

- Recursão simples e direta
- Fácil de entender e implementar
- Ineficiente para valores grandes

### ⭐⭐ **Com Memoização**

- Otimização com cache de resultados
- Introduz conceitos de programação dinâmica
- Melhora significativa de performance

### ⭐⭐⭐ **Otimizações Avançadas**

- Implementação iterativa com espaço O(1)
- Fórmula de Binet para cálculo direto
- Exponenciação de matrizes para O(log n)

## 📈 Comparação de Performance

| Implementação        | Tempo    | Espaço | fibo(40) |
| ---------------------- | -------- | ------- | -------- |
| **Recursiva**    | O(2^n)   | O(n)    | ~1.5s    |
| **Memoização** | O(n)     | O(n)    | ~0.001s  |
| **Iterativa**    | O(n)     | O(1)    | ~0.001s  |
| **Matriz**       | O(log n) | O(1)    | ~0.0001s |

## 🔗 Próximos Passos

Para expandir este projeto, considere implementar:

1. **Memoização**: Cache de resultados para evitar recálculos
2. **Versão Iterativa**: Eliminação da recursão
3. **Testes Unitários**: Validação das diferentes implementações
4. **Benchmark**: Comparação de performance entre abordagens
5. **Tratamento de Overflow**: Uso de BigInteger para números grandes
6. **Interface CLI**: Entrada de parâmetros via linha de comando

## 📄 Referências

- [Fibonacci Sequence - Wikipedia](https://en.wikipedia.org/wiki/Fibonacci_number)
- [Dynamic Programming - GeeksforGeeks](https://www.geeksforgeeks.org/dynamic-programming/)
- [Introduction to Algorithms - CLRS](https://mitpress.mit.edu/books/introduction-algorithms)

---

*Este projeto faz parte da coleção de **Desafios de Entrevista Técnica** - algoritmos fundamentais para preparação em entrevistas de desenvolvimento de software.*
