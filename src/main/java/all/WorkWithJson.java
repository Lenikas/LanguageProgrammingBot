package all;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WorkWithJson {

    public static Question[] readJson() {
        /* сейчас этот метод возвращает список экззэмпляров класса Question */
        JSONParser parser = new JSONParser();
        try(BufferedReader bReader = new BufferedReader(new InputStreamReader(
                WorkWithJson.class.getResourceAsStream("/" + "question.json"))))
        {
            Object obj = parser.parse(bReader);
            JSONObject jsonObject = (JSONObject) obj;
            Question[] listOfQuestionTo = new Question[jsonObject.size()];
            for (int i = 0; i < jsonObject.size(); i++){
                Question ques = new Question();
                JSONObject name = (JSONObject) jsonObject.get(Integer.toString(i));
                ques.createQuestion((String) name.get("question"), (JSONArray) name.get("variants"),  (String) name.get("correct"));
                listOfQuestionTo[i] = ques;
            }
            return listOfQuestionTo;
        }
        catch (ParseException | IOException e) {
            e.printStackTrace();
            return new Question[0];
        }
    }

    //public static void doLoopOneQuestion(Question[] listOfQuestionTo) {
    //    Random random = new Random();
    //   int number = random.nextInt(listOfQuestionTo.length);
    //   Question question = listOfQuestionTo[number];
    //    question.withdrawTheQuestion();
    //}
}
