import java.util.HashMap;
import java.util.Map;

public class Fibonacci {
    // Constante para formatação de tempo
    private static final String TIME_UNIT = " ms\n";

    // Implementação recursiva original - O(2^n) tempo, O(n) espaço
    static long fibo(int n) {
        if (n < 2)
            return n;
        return fibo(n - 1) + fibo(n - 2);
    }

    // Cache para memoização
    private static Map<Integer, Long> memo = new HashMap<>();

    // 1. Memoização (Top-Down) - O(n) tempo, O(n) espaço
    static long fiboMemo(int n) {
        if (n < 2)
            return n;
        if (memo.containsKey(n))
            return memo.get(n);

        long result = fiboMemo(n - 1) + fiboMemo(n - 2);
        memo.put(n, result);
        return result;
    }

    // 2. Programação Dinâmica (Bottom-Up) - O(n) tempo, O(n) espaço
    static long fiboDp(int n) {
        if (n < 2)
            return n;

        long[] dp = new long[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    // 3. Otimização de Espaço - O(n) tempo, O(1) espaço
    static long fiboOptimized(int n) {
        if (n < 2)
            return n;

        long prev2 = 0;
        long prev1 = 1;

        for (int i = 2; i <= n; i++) {
            long current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    // Método para demonstrar performance das diferentes implementações
    static void demonstratePerformance() {
        int n = 40;

        System.out.println("=== Demonstração de Performance ===");
        System.out.println("Calculando Fibonacci(" + n + "):\n");

        // Teste da implementação com memoização (mais rápida para teste)
        memo.clear(); // Limpa o cache
        long startTime = System.nanoTime();
        long result = fiboMemo(n);
        long endTime = System.nanoTime();
        System.out.println("Memoização: " + result);
        System.out.println("Tempo: " + (endTime - startTime) / 1_000_000.0 + TIME_UNIT);

        // Teste da implementação com programação dinâmica
        startTime = System.nanoTime();
        result = fiboDp(n);
        endTime = System.nanoTime();
        System.out.println("Programação Dinâmica: " + result);
        System.out.println("Tempo: " + (endTime - startTime) / 1_000_000.0 + TIME_UNIT);

        // Teste da implementação otimizada
        startTime = System.nanoTime();
        result = fiboOptimized(n);
        endTime = System.nanoTime();
        System.out.println("Otimizada (O(1) espaço): " + result);
        System.out.println("Tempo: " + (endTime - startTime) / 1_000_000.0 + TIME_UNIT);

        // Aviso sobre a implementação recursiva
        System.out.println("AVISO: Implementação recursiva não testada para n=40");
        System.out.println("(demoraria vários segundos)");
    }

    public static void main(String[] args) {
        System.out.println("=== Sequência de Fibonacci (primeiros 10 números) ===");
        System.out.println("Usando implementação recursiva original:");
        for (int i = 0; i < 10; i++) {
            System.out.println("F(" + i + ") = " + fibo(i));
        }

        System.out.println("\n=== Comparação de Implementações ===");
        int testN = 15;
        System.out.println("Calculando F(" + testN + ") com diferentes métodos:");

        memo.clear(); // Limpa o cache para teste justo
        System.out.println("Recursiva: " + fibo(testN));
        System.out.println("Memoização: " + fiboMemo(testN));
        System.out.println("Prog. Dinâmica: " + fiboDp(testN));
        System.out.println("Otimizada: " + fiboOptimized(testN));

        System.out.println();
        demonstratePerformance();
    }
}