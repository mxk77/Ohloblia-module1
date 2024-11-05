import java.nio.file.Files;
import java.nio.file.Path;

public class Runner {
    public void run(final String[] args){
        final Command command=checkCommand(args);
        final Path filePath=checkFile(args);
        final Integer key=checkKey(args, command);
        if (command==null || filePath==null || key==null){
            return;
        }

        switch (command) {
            case ENCRYPT -> encryptFile(filePath, key);
            case DECRYPT -> decryptFile(filePath, key);
            case BRUTE_FORCE -> bruteForce(filePath);
        }
    }

    private void encryptFile(Path filePath, int key){

    }
    private void decryptFile(Path filePath, int key){

    }
    private void bruteForce(Path filePath){

    }

    private Command checkCommand(final String [] args){
        Command command=null;
        try {
            command = Command.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Помилка: Невірна команда! Доступні команди: ENCRYPT, DECRYPT, BRUTE_FORCE.");
        }
        return command;
    }

    private Path checkFile(final String [] args){
        Path filePath=Path.of(args[1]);
        if (!Files.exists(filePath)) {
            System.err.println("Помилка: Вказаного файлу не існує!");
            return null;
        }
        return filePath;
    }

    private Integer checkKey(final String [] args,Command command){
        Integer key=null;

        if (command == Command.ENCRYPT || command == Command.DECRYPT) {
            if (args.length < 3) {
                System.err.println("Помилка: Аргумент <Key> обов'язковий!");
                return key;
            }
            try {
                key = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("Помилка: Ключ має бути числом!");
            }
        }
        return key;
    }
}
