import org.json.simple.JSONArray;

import java.util.Random;

public class Question {
    private String question;
    private JSONArray variants;
    private String correct;
    public void createQuestion(String inQuestion, JSONArray inVariants, String inCorrect)
    {
        question = inQuestion;
        variants = inVariants;
        correct = inCorrect;
    }
    public void printQuestion()
    {
        System.out.println(question);
        for (Object answer : variants) System.out.println(answer);
        User.responseVerification(correct);
    }
}
