import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Runner {

    public void run(final String[] args){
        try {
            final Command command=checkCommand(args[0]);
            final Path filePath=checkFile(args[1]);
            final int key=checkKey(args, command);
            final Path filePathForStaticAnalysis = checkFilePathForStaticAnalysis(args, command);

            switch (command) {
                case ENCRYPT -> encryptFile(filePath, key);
                case DECRYPT -> decryptFile(filePath, key);
                case BRUTE_FORCE -> bruteForceFile(filePath,filePathForStaticAnalysis);
            }
        } catch (RuntimeException e){
            System.err.println(e.getMessage());
        }
    }

    private void encryptFile(Path filePath, int key){
        try {
            String content = FileService.readFile(filePath);
            String encryptedContent = CaesarCipher.encrypt(content, key);
            FileService.writeWithSuffix(filePath, encryptedContent, "[ENCRYPTED]");
            System.out.println("Файл зашифровано успішно!");
        } catch (IOException e) {
            throw new RuntimeException("Помилка при шифруванні файлу: " + e.getMessage(), e);
        }
    }
    private void decryptFile(Path filePath, int key){
        try {
            String content = FileService.readFile(filePath);
            String decryptedContent = CaesarCipher.decrypt(content, key);
            FileService.writeWithSuffix(filePath, decryptedContent, "[DECRYPTED]");
            System.out.println("Файл дешифровано успішно!");
        } catch (IOException e) {
            throw new RuntimeException("Помилка при дешифрації файлу: " + e.getMessage(), e);
        }
    }
    private void bruteForceFile(Path filePath, Path filePathForStaticAnalysis) {
        try {
            String content = FileService.readFile(filePath);
            String textForStaticAnalysis = null;
            if (filePathForStaticAnalysis != null) {
                textForStaticAnalysis = FileService.readFile(filePathForStaticAnalysis);
            }

            BruteForcer bruteForcer = new BruteForcer(textForStaticAnalysis, content);
            bruteForcer.bruteForce(content);

            int mostLikelyKey = bruteForcer.getMostLikelyKey();
            String mostLikelyText = bruteForcer.getMostLikelyText();

            FileService.writeWithSuffix(filePath, mostLikelyText, "[BRUTE_FORCE " + mostLikelyKey + "]");

            System.out.println("Файл дешифровано успішно з ключем: " + mostLikelyKey);
        } catch (IOException e) {
            throw new RuntimeException("Помилка при дешифрації файлу: " + e.getMessage(), e);
        }
    }


    Command checkCommand(String commandString) {
        try {
            return Command.valueOf(commandString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Помилка: Невірна команда! Доступні команди: ENCRYPT, DECRYPT, BRUTE_FORCE.");
        }
    }

    Path checkFile(String filePathString) {
        Path filePath = sanitizePath(filePathString);
        if (!Files.exists(filePath)) {
            throw new RuntimeException("Помилка: Вказаного файлу не існує!");
        }
        return filePath;
    }

    private Path sanitizePath(String inputPath) {
        if (inputPath.startsWith("\"") && inputPath.endsWith("\"")) {
            inputPath = inputPath.substring(1, inputPath.length() - 1);
        }
        return Path.of(inputPath);
    }

    int checkKey(String[] args, Command command) {
        if (command == Command.ENCRYPT || command == Command.DECRYPT) {
            if (args.length < 3) {
                throw new RuntimeException("Помилка: Аргумент <Key> обов'язковий!");
            }
            try {
                return Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Помилка: Ключ має бути числом!");
            }
        }
        return -1;
    }

    Path checkFilePathForStaticAnalysis(String[] args, Command command) {
        if (command == Command.BRUTE_FORCE && args.length >= 3) {
            Path filePathForStaticAnalysis = sanitizePath(args[2]);
            if (!Files.exists(filePathForStaticAnalysis)) {
                throw new RuntimeException("Помилка: Файлу для частотного аналізу не існує!");
            }
            return filePathForStaticAnalysis;
        }
        return null;
    }
}
