import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int fileCount = 0;

        while (true) {
            System.out.print("Введите путь до файла: ");
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

            try {
                int linesCount = 0;
                int longestLength = 0;
                int shortestLength = Integer.MAX_VALUE;

                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    linesCount++;
                    longestLength = Math.max(longestLength, length);
                    shortestLength = Math.min(shortestLength, length);

                    if (length > 1024) {
                        throw new Main.LineTooLongException("Строка содержит более 1024 символов.");
                    }
                }
                reader.close();

                System.out.println("Общее количество строк в файле: " + linesCount);
                System.out.println("Длина самой длинной строки в файле: " + longestLength);
                System.out.println("Длина самой короткой строки в файле: " + shortestLength);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Main.LineTooLongException e) {
                e.printStackTrace();
            }
        }
    }
    static class LineTooLongException extends RuntimeException {
        public LineTooLongException(String message) {
            super(message);
        }
    }
}
