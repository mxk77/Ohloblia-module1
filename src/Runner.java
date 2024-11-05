import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Runner {
    public void run(final String[] args){
        try {
            final Command command=checkCommand(args);
            final Path filePath=checkFile(args);
            final int key=checkKey(args, command);

            switch (command) {
                case ENCRYPT -> encryptFile(filePath, key);
                case DECRYPT -> decryptFile(filePath, key);
                case BRUTE_FORCE -> bruteForceFile(filePath);
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
    private void bruteForceFile(Path filePath){

    }

    private Command checkCommand(final String[] args) {
        try {
            return Command.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Помилка: Невірна команда! Доступні команди: ENCRYPT, DECRYPT, BRUTE_FORCE.");
        }
    }

    private Path checkFile(final String[] args) {
        Path filePath = Path.of(args[1]);
        if (!Files.exists(filePath)) {
            throw new RuntimeException("Помилка: Вказаного файлу не існує!");
        }
        return filePath;
    }

    private int checkKey(final String[] args, Command command) {
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
}
