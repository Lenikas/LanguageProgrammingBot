package test_j.all;

import all.Question;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.Test;


public class QuestionTest {
    @Test
    public void formatAnswers() {
        Question a = new Question();
        JSONArray array = new JSONArray();
        array.add(0, 3);
        array.add(1, 1);
        array.add(2, 2);
        a.setVariants(array);
        Assert.assertEquals("3" + "\n" + "1" + "\n" + "2" + "\n", a.formatAnswers().toString());
    }
    @Test
    public void createQuestion() {
        Question question = new Question();
        JSONArray variants = new JSONArray();
        variants.add(0, 0);
        variants.add(1, 1);
        variants.add(2, 2);
        question.createQuestion("1",variants, "2", "inLink");
        Assert.assertEquals("0" + "\n" + "1" + "\n" + "2" + "\n", question.formatAnswers().toString());
    }
}