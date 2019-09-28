import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Bot {

    public static String readMessage() {
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    public static void processCommand(String command) {
        switch (command)
        {
            case "/help" :
                System.out.println("Hello, i am Language Programming Bot, it is my commands");
                break;
            case "/start" :
                System.out.println("Are you ready to start?");
                break;
            case "/stop" :
                System.out.println("Ok, continue later");
                break;
            default :
                System.out.println("It is not a command");
                break;
        }
    }

    public static void createQuestion(String question) {
        //сюда парсить вопрос из Json/XML
        System.out.println(question);
    }

    public static void createAnswers(String[] answers) {
        //сюда парсить массив вариантов ответов из Json/XML
        for (String answer : answers) System.out.println(answer);
    }

    public static void processAnswer(String answer) {
        //здесь должна быть проверка на соответствие ответа введенного пользователем и правильного из Json/XML

        if (answer.equals(new String("b"))) {
            System.out.println("Yes, you right!");
        }
        else {
            System.out.println("No, it is not right");
        }
    }

    public static void readJson() {
        //метод чтения текста из json файла,осталось его распарсить
        //этот метод читает файл и выводит на консоль, можно сделать чтобы он возвращал строку json и ее уже парсить
        //этот метод можно перенести в класс,где будут методы работы с json
        try(FileReader reader = new FileReader("data.json"))
        {
            int c;
            while((c=reader.read())!=-1){
                System.out.print((char)c);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
