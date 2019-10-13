package all;

import org.json.simple.JSONArray;



public class Question {
    public String question;
    public JSONArray variants;
    public String correct;
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

    public  StringBuilder formatAnswers() {
        StringBuilder result = new StringBuilder();
        for (Object answer : variants) {
            result.append(answer).append("\n");
        }
        return result;
    }

}
