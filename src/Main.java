import ru.courses.access_log_parser.LogEntry;
import ru.courses.access_log_parser.Statistics;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int fileCount = 0;
        Statistics statistics = new Statistics();

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
                int googleBotRequests = 0;
                int yandexBotRequests = 0;

                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;

                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    linesCount++;
                    longestLength = Math.max(longestLength, length);
                    shortestLength = Math.min(shortestLength, length);

                    LogEntry logEntry = new LogEntry(line);

                     statistics.addEntry(logEntry);

                        if (!line.contains("(compatible") || !line.contains(")")) {
                            continue;
                        }

                    String firstBrackets = line.substring(line.indexOf("(compatible") + 1);
                    String[] parts = firstBrackets.split(";");

                    if (parts.length >= 2) {
                        String fragment = parts[1];
                        fragment = fragment.replaceAll("\s+", "");
                        if (!fragment.contains("/")) {
                            continue;
                        }
                        String finalFragment = fragment.substring(0, fragment.indexOf("/"));


                        if (finalFragment.equals("Googlebot")) {
                            googleBotRequests++;
                        } else if (finalFragment.equals("YandexBot")) {
                            yandexBotRequests++;
                        }
                    }
                }

                reader.close();
                System.out.println("Колличество трафика в час = " + statistics.getTrafficRate());
                System.out.println("Общее количество строк в файле: " + linesCount);
                System.out.println("Длина самой длинной строки в файле: " + longestLength);
                System.out.println("Длина самой короткой строки в файле: " + shortestLength);
                System.out.println("Количество запросов от YandexBot: " + yandexBotRequests);
                System.out.println("Количество запросов от GoogleBot: " + googleBotRequests);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}