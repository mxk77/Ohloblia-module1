import java.nio.file.Path;
import java.util.Scanner;

public class CLI {
    private final Scanner scanner;
    private final Runner runner;

    public CLI() {
        this.scanner = new Scanner(System.in);
        this.runner = new Runner();
    }

    public void start() {
        Command command = promptCommand();
        Path filePath = promptFilePath();
        String key = null;
        Path filePathForStaticAnalysis = null;

        if (command == Command.BRUTE_FORCE) {
            filePathForStaticAnalysis = promptOptionalFilePathForStaticAnalysis();
        } else {
            key = promptKey(command).toString();
        }

        if (command == Command.BRUTE_FORCE && filePathForStaticAnalysis != null) {
            runner.run(new String[]{command.name(), filePath.toString(), filePathForStaticAnalysis.toString()});
        } else if (command == Command.BRUTE_FORCE) {
            runner.run(new String[]{command.name(), filePath.toString()});
        } else {
            runner.run(new String[]{command.name(), filePath.toString(), key});
        }
    }

    private Command promptCommand() {
        Command command = null;
        while (command == null) {
            System.out.println("Введіть команду (ENCRYPT, DECRYPT, або BRUTE_FORCE):");
            String input = scanner.nextLine().trim();

            try {
                command = runner.checkCommand(input);
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
        return command;
    }

    private Path promptFilePath() {
        Path filePath = null;
        while (filePath == null) {
            System.out.println("Введіть повний шлях до файлу:");
            String pathInput = scanner.nextLine();

            try {
                filePath = runner.checkFile(pathInput);
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
        return filePath;
    }

    private Integer promptKey(Command command) {
        Integer key = null;
        while (key == null) {
            System.out.println("Введіть ключ (ціле число):");
            String keyInput = scanner.nextLine().trim();

            try {
                key = runner.checkKey(new String[]{"", "", keyInput}, command);
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
        return key;
    }

    private Path promptOptionalFilePathForStaticAnalysis() {
        System.out.println("Введіть повний шлях до файлу для частотного аналізу (або залиште порожнім, щоб пропустити):");
        String pathInput = scanner.nextLine().trim();

        if (!pathInput.isEmpty()) {
            try {
                return runner.checkFile(pathInput);
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}