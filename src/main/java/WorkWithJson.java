import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class WorkWithJson {
    public static Question[] readJson() {
        /* сейчас этот метод возвращает список экззэмпляров класса Question */
        JSONParser parser = new JSONParser();
        //try(FileReader reader = new FileReader("C:\\Users\\Леня\\Desktop\\lenika\\JavaBot\\LanguageProgrammingBot\\src\\main\\java\\data.json"))
        try(FileReader reader = new FileReader("C:\\Users\\Дмитрий\\LanguageProgrammingBot\\src\\main\\java\\data.json"))
        {
            Object obj = parser.parse(reader);
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
