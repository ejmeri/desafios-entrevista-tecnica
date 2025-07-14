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
            switch (s.charAt(i)) {
                case 'I':
                    if (i + 1 < s.length() && (s.charAt(i + 1) == 'V' || s.charAt(i + 1) == 'X')) {
                        resultado -= 1;
                    } else {
                        resultado += 1;
                    }
                    break;
                case 'V':
                    resultado += 5;
                    break;
                case 'X':
                    if (i + 1 < s.length() && (s.charAt(i + 1) == 'L' || s.charAt(i + 1) == 'C')) {
                        resultado -= 10;
                    } else {
                        resultado += 10;
                    }
                    break;
                case 'L':
                    resultado += 50;
                    break;
                case 'C':
                    if (i + 1 < s.length() && (s.charAt(i + 1) == 'D' || s.charAt(i + 1) == 'M')) {
                        resultado -= 100;
                    } else {
                        resultado += 100;
                    }
                    break;
                case 'D':
                    resultado += 500;
                    break;
                case 'M':
                    resultado += 1000;
                    break;
                default:
                    break;
            }
        }

        return resultado;
    }
}
