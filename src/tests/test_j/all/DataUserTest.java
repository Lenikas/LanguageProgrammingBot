package test_j.all;

import all.DataUser;
import all.Question;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.Test;

public class DataUserTest {

    @Test
    public void setIndex() {
        DataUser user = new DataUser();
        user.setIndex(5);
        Assert.assertEquals(5, user.getIndex());

    }

    @Test
    public void setCurrentData() {
        DataUser user = new DataUser();
        Question[] currentData = new Question[]{new Question("1", new JSONArray(), "2",
                "Link"), new Question("3", new JSONArray(), "4", "NewLink"),};
        user.setCurrentData(currentData);
        Assert.assertEquals("4", user.getCurrentData()[1].getCorrect());
    }
}