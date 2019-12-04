package java.all;

import all.Question;
import all.WorkWithJson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkWithJsonTest {

    @Test
    void readJson() {
        Question[] questions = WorkWithJson.readJson("question.json");
        assertEquals("Что можно добавить в set()?", questions[2].getQuestion());
    }
}