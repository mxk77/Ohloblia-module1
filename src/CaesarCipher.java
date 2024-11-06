import java.util.HashMap;
import java.util.Map;

public class CaesarCipher {
    private CaesarCipher() {
        throw new IllegalStateException("Utility class");
    }

    private static final String ENGLISH_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String UKRAINIAN_ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюя";

    private static String currentAlphabet = ENGLISH_ALPHABET;
    private static int alphabetSize = currentAlphabet.length();

    public static String getUkrainianAlphabet(){
        return UKRAINIAN_ALPHABET;
    }

    public static String encrypt(String text, int key) {
        selectAlphabet(text);
        return shiftText(text, key);
    }

    public static String decrypt(String text, int key) {
        selectAlphabet(text);
        return shiftText(text, -key);
    }

    //Метод для зсуву символів рядка по алфавіту за визначеним значенням
    private static String shiftText(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            int index = currentAlphabet.indexOf(character);

            if (index != -1) {  // Character is in the selected alphabet
                int shiftedIndex = (index + shift + alphabetSize) % alphabetSize;
                result.append(currentAlphabet.charAt(shiftedIndex));
            } else {  // Character is not in the alphabet, add it as-is
                result.append(character);
            }
        }

        return result.toString();
    }

    private static void selectAlphabet(String text) {
        //Перевіряємо чи символ належить українському алфавіту
        for (char character : text.toCharArray()) {
            if (UKRAINIAN_ALPHABET.indexOf(character) != -1 && Character.isLetter(character)) {
                currentAlphabet = UKRAINIAN_ALPHABET;
                alphabetSize = currentAlphabet.length();
                return;
            }
        }
        currentAlphabet = ENGLISH_ALPHABET;
        alphabetSize = currentAlphabet.length();
    }
}
