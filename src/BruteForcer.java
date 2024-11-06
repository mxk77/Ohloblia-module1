import java.util.HashMap;
import java.util.Map;

public class BruteForcer {
    private final Map<Character, Double> letterFrequencies;
    private String mostLikelyText;
    private int mostLikelyKey;

    private static final Map<Character, Double> DEFAULT_ENGLISH_FREQUENCIES = createEnglishFrequencyMap();


    private static Map<Character, Double> createEnglishFrequencyMap() {
        Map<Character, Double> map = new HashMap<>();
        char[] letters = {'e', 'a', 'r', 'i', 'o', 't', 'n', 's', 'l', 'c', 'u', 'd', 'p', 'm', 'h', 'g', 'b', 'f',
                'y', 'w', 'k', 'v', 'x', 'z', 'j', 'q'};
        double[] frequencies = {11.1607, 8.4966, 7.5809, 7.5448, 7.1635, 6.9509, 6.6544, 5.7351, 5.4893, 4.5388,
                3.6308, 3.3844, 3.1671, 3.0129, 3.0034, 2.4705, 2.0720, 1.8121, 1.7779, 1.2899, 1.1016, 1.0074, 0.2902,
                0.2722, 0.1965, 0.1962}; for (int i = 0; i < letters.length; i++) { map.put(letters[i], frequencies[i]);
                }
                return map;
    }

    public BruteForcer(String staticAnalysisText) {
        if (staticAnalysisText != null) {
            this.letterFrequencies = calculateLetterFrequency(staticAnalysisText);
        } else {
            this.letterFrequencies = DEFAULT_ENGLISH_FREQUENCIES;
        }
    }

    public BruteForcer() {
        this(null);
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
