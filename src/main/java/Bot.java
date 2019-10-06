import java.io.*;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.util.Random;
import org.json.simple.parser.ParseException;

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
                //когда вызываются данные команды,наверное должно что-то происходить
                //StartBot.main(new String[] {});
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

    public static void createAnswers(JSONArray answers) {
        //сюда парсить массив вариантов ответов из Json/XML
        for (Object answer : answers) System.out.println(answer);

    }

    public static void processAnswer(String correctAnswer) {
        //здесь должна быть проверка на соответствие ответа введенного пользователем и правильного из Json/XML
        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        String actualAnswer = in.nextLine();
        if (correctAnswer.equals(actualAnswer)) {
            System.out.println("Yes, you right!");
        }
        else {
            System.out.println("No, it is not right");
        }
    }

    public static JSONArray readJson() {
        /* сейчас этот метод возвращает массив из трех элементов, где первый элемент - вопрос, второй - массив из вариантов ответов, третий - верный ответ */
        JSONParser parser = new JSONParser();
        //try(FileReader reader = new FileReader("C:\\Users\\Дмитрий\\LanguageProgrammingBot\\src\\main\\java\\data.json"))
        JSONArray array = new JSONArray();
        try(FileReader reader = new FileReader("C:\\Users\\Леня\\Desktop\\lenika\\JavaBot\\LanguageProgrammingBot\\src\\main\\java\\data.json"))
        {
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            Random random = new Random();
            int number = random.nextInt(jsonObject.size());
            JSONObject name = (JSONObject)jsonObject.get(Integer.toString(number));
            String question = (String)name.get("question");
            array.add(question);
            JSONArray variants = (JSONArray)name.get("variants");
            array.add(variants);
            String correct = (String)name.get("correct");
            array.add(correct);
        }
        catch (ParseException | IOException e) {
           e.printStackTrace();
        }
        return array;
    }

    public static void doLoopOneQuestion(JSONArray array) {
        String question = (String) array.get(0);
        JSONArray variants = (JSONArray) array.get(1);
        String correctAnswer = (String) array.get(2);
        createQuestion(question);
        createAnswers(variants);
        processAnswer(correctAnswer);
    }
}
