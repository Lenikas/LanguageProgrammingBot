package telegram;

import all.DataUser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;


/**
 * Класс с методами, обрабатывающими существубщие команды бота.
 */
public class Commands {

    public static SendMessage processRatingCommand(Long chatId, Map<Long, DataUser> map) {
        StringBuilder table = new StringBuilder();
        table.append("Рейтинг пользователей:").append("\n");
        for (Long key : map.keySet()) {
            table.append(map.get(key).getUserName()).append(":").append(map.get(key).getCountRight()).append("\n");
        }
        return new SendMessage().setText(table.toString()).setChatId(chatId);

    }

    public static SendMessage processBallsCommand(Long chatId, Map<Long, DataUser> map) {
        return new SendMessage().setText("Количество баллов за правильные ответы в текущую сессию:" + " " + map.get(chatId).getCountRight()).setChatId(chatId);
    }

    public static SendMessage processHelpCommand(Long chatId) {
        SendMessage message = getCommandsForHelp();
        message.setChatId(chatId);
        return message;
    }

    public static SendMessage processStopCommand(Long chatId) {
        return new SendMessage().setText("Вы прервали работу бота, чтобы начать снова, введите команду /start").setChatId(chatId);
    }

    private static SendMessage getCommandsForHelp() {
        String text = "This is a list of commands bot:" + "\n" +
                "/start - запускает цикл вопросов-ответов по языкам программирования" +
                "\n" + "/stop - останавливает цикл вопросов-ответов бота" + "\n" +
                "/rating - показывает таблицу с количеством правильныз ответов текущих пользователей" + "\n" +
                "/ballnow - показывет балл пользователя" + "\n" +
                "/switch - дает возможность поменять тему вопросов";
        return new SendMessage().setText(text);
    }

}
