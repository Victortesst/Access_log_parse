import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число и нажмите Enter");
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число и нажмите Enter");
        int secondNumber = new Scanner(System.in).nextInt();

        int sum = firstNumber + secondNumber;
        System.out.println("Сумма введенных чисел равна " + sum);

        int difference = firstNumber - secondNumber;
        System.out.println("Разность между " + firstNumber + " и " + secondNumber + " равна " + difference);

        int multiplication = firstNumber * secondNumber;
        System.out.println("Произведение введенных чисел равно " + multiplication);

        double quotient = (double) firstNumber / secondNumber;
        System.out.println("Результат деления " + firstNumber + " на " + secondNumber + " равен " + quotient);

    }
}
