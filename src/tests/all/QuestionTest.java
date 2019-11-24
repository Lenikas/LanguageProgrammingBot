package all;

import org.json.simple.JSONArray;
import org.junit.jupiter.api.Test;
import all.Question;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    @Test
    void formatAnswers() {
        Question a = new Question();
        a.variants = new JSONArray();
        a.variants.add(0, 3);
        a.variants.add(1, 1);
        a.variants.add(2, 2);
        assertEquals(new StringBuilder("3"+"\n"+"1"+"\n"+"2"+"\n").toString(), a.formatAnswers().toString());
    }
    @Test
    void createQuestion() {
        Question question = new Question();
        JSONArray variants = new JSONArray();
        variants.add(0, 0);
        variants.add(1, 1);
        variants.add(2, 2);
        question.createQuestion("1",variants, "2", "inLink");
        assertEquals(new StringBuilder("0"+"\n"+"1"+"\n"+"2"+"\n").toString(), question.formatAnswers().toString());
    }
}