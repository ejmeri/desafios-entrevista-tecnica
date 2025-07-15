public class RomanToInt {
    public static void main(String[] args) {
        RomanToInt romanToInt = new RomanToInt();
        System.out.println(romanToInt.romanToInt("III")); // Output: 3
        System.out.println(romanToInt.romanToInt("IV")); // Output: 4
        System.out.println(romanToInt.romanToInt("IX")); // Output: 9
        System.out.println(romanToInt.romanToInt("LVIII")); // Output: 58
        System.out.println(romanToInt.romanToInt("MCMXCIV")); // Output: 1994
    }

    public int romanToInt(String s) {
        int resultado = 0;

        for (int i = 0; i < s.length(); i++) {
            int currentValue = getRomanValue(s.charAt(i));

            if (shouldSubtract(s, i)) {
                resultado -= currentValue;
            } else {
                resultado += currentValue;
            }
        }

        return resultado;
    }

    private int getRomanValue(char romanChar) {
        switch (romanChar) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }

    private boolean shouldSubtract(String s, int index) {
        if (index + 1 >= s.length()) {
            return false;
        }

        char current = s.charAt(index);
        char next = s.charAt(index + 1);

        return isSubtractionCase(current, next);
    }

    private boolean isSubtractionCase(char current, char next) {
        return (current == 'I' && (next == 'V' || next == 'X')) ||
               (current == 'X' && (next == 'L' || next == 'C')) ||
               (current == 'C' && (next == 'D' || next == 'M'));
    }
}
