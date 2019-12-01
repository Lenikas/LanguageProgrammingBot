package telegram;

import all.DataUser;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CommandsTest {

    @Test
    void processRatingCommand() {
        Commands user = new Commands();
        Map<Long, DataUser > map = new HashMap<>();
        DataUser data = new DataUser();
        data.userName = "I";
        data.countRight = 6;
        map.put(101L, data);
        assertEquals("Рейтинг пользователей:"+"\n"+"I:6"+"\n", user.processRatingCommand(101L, map).getText());
    }

    @Test
    void processBallsCommand() {
        Commands user = new Commands();
        Map<Long, DataUser > map = new HashMap<>();
        DataUser data = new DataUser();
        data.userName = "I";
        data.countRight = 6;
        map.put(101L, data);
        assertEquals("Количество баллов за правильные ответы в текущую сессию:" + " " + "6", user.processBallsCommand(101L, map).getText());
    }

    @Test
    void processHelpCommand() {
        Commands user = new Commands();
        assertEquals("101", user.processHelpCommand(101L).getChatId().toString());
    }

    @Test
    void processStopCommand() {
        Commands user = new Commands();
        assertEquals("Вы прервали работу бота, чтобы начать снова, введите команду /start", user.processStopCommand(101L).getText());
    }
}
