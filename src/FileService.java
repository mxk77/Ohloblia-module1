import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {
    private FileService() {
        throw new IllegalStateException("Utility class");
    }

    //Зчитуємо вміст файлу у рядок
    public static String readFile(String filePath) throws IOException {
        Path path = Path.of(filePath);
        return Files.readString(path);
    }

    //Записуємо новий вміст у файл за вказаним шляхом
    private static void writeFile(String filePath, String content) throws IOException {
        Path path = Path.of(filePath);
        Files.writeString(path, content);
    }

    //Записуємо наданий вміст у файл в одній і тій самій директорії, що й оригінальний файл, але до його імені додаємо суфікс
    public static void writeWithSuffix(String originalFilePath, String content, String suffix) throws IOException {
        String newFilePath = generateFileNameWithSuffix(originalFilePath, suffix);
        writeFile(newFilePath, content);
    }

    //Додаємо суфікс до імені файлу
    private static String generateFileNameWithSuffix(String originalFilePath, String suffix) {
        int dotIndex = originalFilePath.lastIndexOf(".");
        if (dotIndex == -1) {
            return originalFilePath + suffix;
        } else {
            return originalFilePath.substring(0, dotIndex) + suffix + originalFilePath.substring(dotIndex);
        }
    }
}
