# Two Sum Problem

Este projeto implementa duas soluções para o clássico problema **Two Sum**, demonstrando diferentes abordagens algorítmicas e suas respectivas complexidades.

## 📋 Problema

Dado um array de inteiros `nums` e um valor inteiro `target`, encontre os **índices** de dois números no array que somados resultem no valor `target`.

**Restrições:**
- Cada entrada tem exatamente uma solução
- Não é possível usar o mesmo elemento duas vezes
- A resposta pode ser retornada em qualquer ordem

## 🏗️ Implementações

### 🐌 Solução 1: Força Bruta

```java
public static int[] twoSum(int[] nums, int target) {
    System.out.println("Brute force solution");

    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) {
                return new int[] { i, j };
            }
        }
    }

    throw new IllegalArgumentException("No two sum solution found");
}
```

**Como funciona:**
- Usa dois loops aninhados para testar todas as combinações possíveis
- Para cada elemento `i`, verifica todos os elementos seguintes `j`
- Retorna os índices quando encontra a soma desejada

**Complexidade:**
- ⏱️ **Tempo**: O(n²) - loop duplo
- 💾 **Espaço**: O(1) - apenas variáveis auxiliares

### ⚡ Solução 2: Hash Table

```java
public static int[] twoSumHashTable(int[] nums, int target) {
    System.out.println("Hash table solution");

    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[] { map.get(complement), i };
        }
        map.put(nums[i], i);
    }

    throw new IllegalArgumentException("No two sum solution found");
}
```

**Como funciona:**
1. Usa um `HashMap` para armazenar valores já visitados e seus índices
2. Para cada elemento, calcula o **complemento** (`target - nums[i]`)
3. Verifica se o complemento já existe no HashMap
4. Se existe, retorna os índices; senão, adiciona o elemento atual ao HashMap

**Complexidade:**
- ⏱️ **Tempo**: O(n) - apenas um loop
- 💾 **Espaço**: O(n) - HashMap pode armazenar até n elementos

## 🚀 Exemplo de Uso

```java
public class TwoSums {
    public static void main(String[] args) {
        int[] nums = { 2, 7, 11, 15 };
        int target = 9;

        // Solução força bruta
        int[] result = twoSum(nums, target);
        System.out.println(result[0] + " " + result[1]); // Output: 0 1

        // Solução hash table
        int[] result2 = twoSumHashTable(nums, target);
        System.out.println(result2[0] + " " + result2[1]); // Output: 0 1
    }
}
```

### 📊 Fluxo de Execução (Hash Table)

**Array**: `[2, 7, 11, 15]`, **Target**: `9`

| Iteração | Elemento | Complemento | HashMap | Ação |
|----------|----------|-------------|---------|------|
| 0 | 2 | 7 | {} | Adiciona {2: 0} |
| 1 | 7 | 2 | {2: 0} | ✅ Encontrou! Retorna [0, 1] |

## 📈 Comparação das Soluções

| Aspecto | Força Bruta | Hash Table |
|---------|-------------|------------|
| **Complexidade Tempo** | O(n²) | O(n) |
| **Complexidade Espaço** | O(1) | O(n) |
| **Facilidade de Implementação** | ⭐⭐⭐ | ⭐⭐ |
| **Performance** | Lenta para arrays grandes | Rápida |
| **Uso de Memória** | Mínimo | Moderado |

## 🎯 Quando Usar Cada Solução

**Força Bruta:**
- ✅ Arrays muito pequenos (< 100 elementos)
- ✅ Quando memória é extremamente limitada
- ✅ Implementação simples e direta

**Hash Table:**
- ✅ Arrays grandes (> 100 elementos)
- ✅ Quando performance é prioridade
- ✅ Solução mais elegante e eficiente

## 🏃‍♂️ Como Executar

```bash
# Compilar
javac TwoSums.java

# Executar
java TwoSums
```

**Output esperado:**
```
Brute force solution
0 1
Hash table solution
0 1
```

## 📚 Conceitos Demonstrados

- **Algoritmos**: Comparação entre abordagens O(n²) vs O(n)
- **Estruturas de Dados**: Uso eficiente de HashMap
- **Trade-offs**: Tempo vs Espaço
- **Otimização**: Como melhorar performance algorítmica
- **Complemento Matemático**: Técnica para encontrar pares

## 🎓 Variações do Problema

Este problema é base para outras variações como:
- **3Sum**: Encontrar três números que somem um target
- **4Sum**: Encontrar quatro números que somem um target
- **Two Sum II**: Array já ordenado
- **Two Sum III**: Design de estrutura de dados

---

*Este é um dos problemas mais fundamentais em algoritmos, frequentemente usado em entrevistas técnicas para avaliar conhecimento de estruturas de dados e otimização.*
