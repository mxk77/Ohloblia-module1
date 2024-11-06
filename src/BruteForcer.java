import java.util.HashMap;
import java.util.Map;

public class BruteForcer {
    private final Map<Character, Double> letterFrequencies;
    private String mostLikelyText;
    private int mostLikelyKey;

    public BruteForcer(String staticAnalysisText, String encryptedText) {
        if (staticAnalysisText != null) {
            this.letterFrequencies = calculateLetterFrequency(staticAnalysisText);
        } else {
            if (isUkrainianText(encryptedText)) {
                this.letterFrequencies = createUkrainianFrequencyMap();
            } else {
                this.letterFrequencies = createEnglishFrequencyMap();
            }
        }
    }

    private static Map<Character, Double> createEnglishFrequencyMap() {
        Map<Character, Double> map = new HashMap<>();
        char[] letters = {'e', 'a', 'r', 'i', 'o', 't', 'n', 's', 'l', 'c', 'u', 'd', 'p', 'm', 'h', 'g', 'b', 'f',
                'y', 'w', 'k', 'v', 'x', 'z', 'j', 'q'};
        double[] frequencies = {11.1607, 8.4966, 7.5809, 7.5448, 7.1635, 6.9509, 6.6544, 5.7351, 5.4893, 4.5388,
                3.6308, 3.3844, 3.1671, 3.0129, 3.0034, 2.4705, 2.0720, 1.8121, 1.7779, 1.2899, 1.1016, 1.0074, 0.2902,
                0.2722, 0.1965, 0.1962};
        for (int i = 0; i < letters.length; i++) {
            map.put(letters[i], frequencies[i]);
        }
        return map;
    }

    private static Map<Character, Double> createUkrainianFrequencyMap() {
        Map<Character, Double> map = new HashMap<>();
        char[] letters = {'о', 'а', 'н', 'і', 'и', 'в', 'р', 'т', 'е', 'с', 'к', 'л', 'у', 'д', 'м', 'п', 'я', 'з',
                'ь', 'г', 'б', 'й', 'х', 'ч', 'ц', 'ї', 'ж', 'ш', 'ю', 'є', 'ф', 'щ', 'ґ'};
        double[] frequencies = {9.28, 8.34, 7.10, 6.23, 6.00, 5.50, 5.48, 4.77, 4.59, 4.57, 4.00, 3.93, 3.38, 3.06,
                3.02, 2.84, 2.16, 2.10, 1.83, 1.59, 1.53, 1.24, 1.17, 1.15, 1.02, 0.84, 0.71, 0.71, 0.70, 0.39, 0.35,
                0.32, 0.01};
        for (int i = 0; i < letters.length; i++) {
            map.put(letters[i], frequencies[i]);
        }
        return map;
    }

    private static boolean isUkrainianText(String text) {
        for (char c : text.toCharArray()) {
            if (CaesarCipher.getUkrainianAlphabet().indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }

    public void bruteForce(String encryptedText) {
        int likelyKey = 0;
        String likelyText = encryptedText;
        double bestMatchScore = Double.MAX_VALUE;

        for (int key = 0; key < letterFrequencies.size(); key++) {
            String decryptedText = CaesarCipher.decrypt(encryptedText, key);
            double score = calculateMatchScore(decryptedText);

            if (score < bestMatchScore) {
                bestMatchScore = score;
                likelyKey = key;
                likelyText = decryptedText;
            }
        }
        this.mostLikelyKey = likelyKey;
        this.mostLikelyText = likelyText;
    }

    private Map<Character, Double> calculateLetterFrequency(String text) {
        Map<Character, Integer> counts = new HashMap<>();
        int totalLetters = 0;

        for (char c : text.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
                totalLetters++;
            }
        }

        Map<Character, Double> frequencies = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            frequencies.put(entry.getKey(), (entry.getValue() * 100.0) / totalLetters);
        }
        return frequencies;
    }

    private double calculateMatchScore(String text) {
        Map<Character, Double> textFrequency = calculateLetterFrequency(text);
        double score = 0.0;

        for (Map.Entry<Character, Double> entry : this.letterFrequencies.entrySet()) {
            char letter = entry.getKey();
            double expectedFrequency = entry.getValue();
            double actualFrequency = textFrequency.getOrDefault(letter, 0.0);

            score += Math.pow(actualFrequency - expectedFrequency, 2);
        }
        return score;
    }

    public String getMostLikelyText() {
        return mostLikelyText;
    }

    public int getMostLikelyKey() {
        return mostLikelyKey;
    }
}
