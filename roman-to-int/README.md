# Roman to Integer Converter

Este projeto implementa um conversor de números romanos para números inteiros em Java.

## Descrição

O algoritmo converte uma string contendo um número romano válido para seu equivalente em número inteiro, seguindo as regras tradicionais dos números romanos.

## Símbolos Romanos

| Símbolo | Valor |
|---------|-------|
| I       | 1     |
| V       | 5     |
| X       | 10    |
| L       | 50    |
| C       | 100   |
| D       | 500   |
| M       | 1000  |

## Regras de Conversão

### Regra Principal
Os símbolos são somados da esquerda para a direita.

### Regra de Subtração
Quando um símbolo menor aparece antes de um símbolo maior, ele é subtraído:
- `I` pode aparecer antes de `V` (5) e `X` (10) para formar 4 e 9
- `X` pode aparecer antes de `L` (50) e `C` (100) para formar 40 e 90
- `C` pode aparecer antes de `D` (500) e `M` (1000) para formar 400 e 900

## Exemplos

| Número Romano | Valor | Explicação |
|---------------|-------|------------|
| III           | 3     | 1 + 1 + 1 |
| IV            | 4     | 5 - 1 |
| IX            | 9     | 10 - 1 |
| LVIII         | 58    | 50 + 5 + 1 + 1 + 1 |
| MCMXCIV       | 1994  | 1000 + (1000-100) + (100-10) + (5-1) |

## Como Executar

### Pré-requisitos
- Java 8 ou superior

### Compilação e Execução
```bash
# Compilar
javac RomanToInt.java

# Executar
java RomanToInt
```

### Saída Esperada
```
3
4
9
58
1994
```

## Algoritmo

O algoritmo funciona da seguinte forma:

1. **Percorre** a string da esquerda para a direita
2. **Analisa** cada caractere usando um switch
3. **Verifica** se o próximo caractere forma uma combinação de subtração
4. **Soma ou subtrai** o valor correspondente ao resultado final

### Complexidade
- **Tempo**: O(n) - onde n é o comprimento da string
- **Espaço**: O(1) - usa apenas variáveis auxiliares

## Estrutura do Código

```java
public class RomanToInt {
    public static void main(String[] args) {
        // Testes com diferentes números romanos
    }
    
    public int romanToInt(String s) {
        // Implementação do algoritmo de conversão
    }
}
```

## Casos de Teste

O código inclui os seguintes casos de teste no método `main`:
- `"III"` → 3 (soma simples)
- `"IV"` → 4 (subtração I antes de V)
- `"IX"` → 9 (subtração I antes de X)
- `"LVIII"` → 58 (combinação de soma)
- `"MCMXCIV"` → 1994 (múltiplas subtrações)

## Limitações

- Assume que a entrada é sempre um número romano válido
- Não valida a entrada contra regras de formação de números romanos
- Suporta apenas números romanos padrão (1-3999)
