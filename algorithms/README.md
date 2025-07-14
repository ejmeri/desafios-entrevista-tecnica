# LRU Cache Implementation

Este projeto implementa um **LRU (Least Recently Used) Cache** em Java, demonstrando uma solução elegante e eficiente usando herança da classe `LinkedHashMap`.

## 📋 Visão Geral

O LRU Cache é uma estrutura de dados que mantém um número limitado de elementos, removendo automaticamente o elemento menos recentemente usado quando a capacidade máxima é atingida.

## 🏗️ Implementação

### Classe LRUCache

```java
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
```

### 🔧 Como Funciona

**1. Herança Inteligente:**
- Herda de `LinkedHashMap<K, V>` que mantém a ordem de inserção/acesso
- Usa generics `<K, V>` para permitir qualquer tipo de chave e valor

**2. Construtor:**
```java
super(capacity, 0.75f, true);
```
- `capacity`: capacidade inicial do HashMap
- `0.75f`: fator de carga (load factor)
- `true`: **parâmetro crucial** - habilita o modo "access-order" (ordem de acesso)

**3. Remoção Automática:**
```java
@Override
protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return size() > capacity;
}
```
- Método chamado automaticamente após cada inserção
- Retorna `true` quando o tamanho excede a capacidade
- Remove automaticamente o elemento mais antigo quando necessário

## 🚀 Exemplo de Uso

```java
public class App {
    public static void main(String[] args) {
        // Cria cache com capacidade para 3 elementos
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        
        // Adiciona elementos
        cache.put(1, "one");    // Cache: [1="one"]
        cache.put(2, "two");    // Cache: [1="one", 2="two"]
        cache.put(3, "three");  // Cache: [1="one", 2="two", 3="three"]
        cache.put(4, "four");   // Cache: [2="two", 3="three", 4="four"] (1 foi removido)

        // Testa recuperação
        System.out.println(cache.get(1)); // null (foi removido)
        System.out.println(cache.get(2)); // "two"
        System.out.println(cache.get(3)); // "three"
        System.out.println(cache.get(4)); // "four"
    }
}
```

### 📊 Fluxo de Execução

1. **Inserção inicial**: Cache cresce até atingir capacidade máxima (3)
2. **Overflow**: Quando inserimos o 4º elemento, o menos recentemente usado (1) é removido
3. **Acesso**: Elementos acessados recentemente ficam no "topo" da ordem LRU

## ✅ Vantagens desta Implementação

- **🎯 Simplicidade**: Aproveita funcionalidade existente do LinkedHashMap
- **⚡ Eficiência**: Operações O(1) para get/put
- **🤖 Automática**: LinkedHashMap gerencia automaticamente a ordem LRU
- **📦 Compacta**: Apenas 19 linhas para funcionalidade completa
- **🔧 Flexível**: Suporte a generics para qualquer tipo de chave/valor


## 📚 Conceitos Demonstrados

- **Herança**: Reutilização inteligente de código existente
- **Generics**: Tipagem flexível e type-safe
- **Override**: Personalização de comportamento de métodos herdados
- **Data Structures**: Implementação eficiente de cache LRU
- **Design Patterns**: Uso do padrão Template Method (removeEldestEntry)

## 🎓 Complexidade

- **Tempo**: O(1) para operações get/put
- **Espaço**: O(n) onde n é a capacidade do cache

---

*Esta implementação demonstra como usar herança de forma elegante para criar estruturas de dados eficientes aproveitando funcionalidades já existentes na biblioteca Java.*
