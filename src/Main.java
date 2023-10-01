import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int fileCount = 0;

        while (true) {
            System.out.print("Введите путь до файлв: ");
            String path = new Scanner(System.in).nextLine();

            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();

            if (!fileExists || isDirectory) {
                System.out.println("Указанный путь не существует или является папкой.");
                continue;
            }

            fileCount++;
            System.out.println("Путь указан верно.");
            System.out.println("Это файл номер " + fileCount);
        }
    }
}