package test_j.all;

import all.Question;
import all.WorkWithJson;
import org.junit.Assert;
import org.junit.Test;


public class WorkWithJsonTest {

    @Test
    public void readJson() {
        Question[] questions = WorkWithJson.readJson("question.json");
        Assert.assertEquals("Что можно добавить в set()?", questions[2].getQuestion());
    }
}