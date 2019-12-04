package java.all;

import all.DataUser;
import all.Question;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataUserTest {

    @Test
    void setIndex() {
        DataUser user = new DataUser();
        user.setIndex(5);
        assertEquals(5, user.getIndex());
    }

    @Test
    void setCurrentData() {
        DataUser user = new DataUser();
        Question[] currentData = new Question[]{new Question("1", new JSONArray(), "2",
                "Link"), new Question("3", new JSONArray(), "4", "NewLink"),};
        user.setCurrentData(currentData);
        assertEquals("4", user.getCurrentData()[1].getCorrect());
    }
}