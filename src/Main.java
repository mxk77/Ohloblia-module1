
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Помилка: Недостатня кількість аргументів! " +
                    "Використання: java -jar myApp.jar <command> <filePath> <key>");
            return;
        }

        new Runner().run(args);
    }

}