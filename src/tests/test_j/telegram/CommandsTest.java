package test_j.telegram;

import all.DataUser;
import org.junit.Assert;
import org.junit.Test;
import telegram.Commands;

import java.util.HashMap;
import java.util.Map;


public class CommandsTest {

    @Test
    public void processRatingCommand() {
        Map<Long, DataUser > map = new HashMap<>();
        DataUser data = new DataUser();
        data.setUserName("I");
        data.setCountRight(6);
        map.put(101L, data);
        Assert.assertEquals("Рейтинг пользователей:"+"\n"+"I:6"+"\n", Commands.processRatingCommand(101L, map).getText());
    }

    @Test
    public void processBallsCommand() {
        Map<Long, DataUser > map = new HashMap<>();
        DataUser data = new DataUser();
        data.setUserName("I");
        data.setCountRight(6);
        map.put(101L, data);
        Assert.assertEquals("Количество баллов за правильные ответы в текущую сессию:" + " " + "6", Commands.processBallsCommand(101L, map).getText());
    }

    @Test
    public void processHelpCommand() {
        Assert.assertEquals("101", Commands.processHelpCommand(101L).getChatId().toString());
    }

    @Test
    public void processStopCommand() {
        Assert.assertEquals("Вы прервали работу бота, чтобы начать снова, введите команду /start", Commands.processStopCommand(101L).getText());
    }
}
