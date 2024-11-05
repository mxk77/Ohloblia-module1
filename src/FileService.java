import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {
    private FileService() {
        throw new IllegalStateException("Utility class");
    }

    //Зчитуємо вміст файлу у рядок
    public static String readFile(Path filePath) throws IOException {
        return Files.readString(filePath);
    }

    //Записуємо новий вміст у файл за вказаним шляхом
    private static void writeFile(Path filePath, String content) throws IOException {
        Files.writeString(filePath, content);
    }

    //Записуємо наданий вміст у файл в одній і тій самій директорії, що й оригінальний файл, але до його імені додаємо суфікс
    public static void writeWithSuffix(Path originalFilePath, String content, String suffix) throws IOException {
        Path newFilePath = generateFileNameWithSuffix(originalFilePath, suffix);
        writeFile(newFilePath, content);
    }

    //Додаємо суфікс до імені файлу
    private static Path generateFileNameWithSuffix(Path originalFilePath, String suffix) {
        String fileName = originalFilePath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf(".");
        String newFileName;

        if (dotIndex == -1) {
            newFileName = fileName + suffix;
        } else {
            newFileName = fileName.substring(0, dotIndex) + suffix + fileName.substring(dotIndex);
        }

        return originalFilePath.getParent().resolve(newFileName);
    }
}
