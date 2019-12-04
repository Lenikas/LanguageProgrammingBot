package java.telegram;

import all.DataUser;
import org.junit.jupiter.api.Test;
import telegram.Commands;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandsTest {

    @Test
    void processRatingCommand() {
        Map<Long, DataUser > map = new HashMap<>();
        DataUser data = new DataUser();
        data.setUserName("I");
        data.setCountRight(6);
        map.put(101L, data);
        assertEquals("Рейтинг пользователей:"+"\n"+"I:6"+"\n", Commands.processRatingCommand(101L, map).getText());
    }

    @Test
    void processBallsCommand() {
        Map<Long, DataUser > map = new HashMap<>();
        DataUser data = new DataUser();
        data.setUserName("I");
        data.setCountRight(6);
        map.put(101L, data);
        assertEquals("Количество баллов за правильные ответы в текущую сессию:" + " " + "6", Commands.processBallsCommand(101L, map).getText());
    }

    @Test
    void processHelpCommand() {
        assertEquals("101", Commands.processHelpCommand(101L).getChatId().toString());
    }

    @Test
    void processStopCommand() {
        assertEquals("Вы прервали работу бота, чтобы начать снова, введите команду /start", Commands.processStopCommand(101L).getText());
    }
}
