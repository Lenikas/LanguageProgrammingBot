import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public static void processAnswer(String answer) {
        //здесь должна быть проверка на соответствие ответа введенного пользователем и правильного из Json/XML
        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        String num = in.nextLine();
        if (answer.equals(num)) {
            System.out.println("Yes, you right!");
        }
        else {
            System.out.println("No, it is not right");
        }
    }

    public static void readJson() {
        JSONParser parser = new JSONParser();
        try(FileReader reader = new FileReader("C:\\Users\\Дмитрий\\LanguageProgrammingBot\\src\\main\\java\\data.json"))
        {
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            Random random = new Random();
            int number = random.nextInt(4);
            JSONObject name = (JSONObject)jsonObject.get(Integer.toString(number));
            String question = (String)name.get("question");
            createQuestion(question);
            JSONArray variants = (JSONArray)name.get("variants");
            createAnswers(variants);
            String correct = (String)name.get("correct");
            processAnswer(correct);
        }catch (FileNotFoundException e) {
           e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
