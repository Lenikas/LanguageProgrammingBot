package all;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorkWithJsonTest {

    @Test
    void readJson() {
        Question[] questions = WorkWithJson.readJson("question.json");
        assertEquals("Что можно добавить в set()?", questions[2].question);
    }
}