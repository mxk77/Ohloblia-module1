public class CaesarCipher {
    private CaesarCipher() {
        throw new IllegalStateException("Utility class");
    }

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.,«»\"':!? ";
    private static final int ALPHABET_SIZE = ALPHABET.length();

    public static String encrypt(String text, int key) {
        return shiftText(text, key);
    }

    public static String decrypt(String text, int key) {
        return shiftText(text, -key);
    }

    //Метод для зсуву символів рядку по алфавіту за визначеним значенням
    private static String shiftText(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            int index = ALPHABET.indexOf(character);

            if (index != -1) {  //Якщо символ є у вказаному алфавіті - зсуваємо
                int shiftedIndex = (index + shift + ALPHABET_SIZE) % ALPHABET_SIZE;
                result.append(ALPHABET.charAt(shiftedIndex));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }
}
