package telegram;

import all.DataUser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Map;


class Commands {

    static SendMessage processRatingCommand(Update update, Map<Long, DataUser> map) {
        Long chatId = update.getMessage().getChatId();
        StringBuilder table = new StringBuilder();
        table.append("Рейтинг пользователей:").append("\n");
        for (Long key : map.keySet()) {
            table.append(map.get(key).userName).append(":").append(map.get(key).countRight).append("\n");
        }
        return new SendMessage().setText(table.toString()).setChatId(chatId);

    }

    static SendMessage processBallsCommand(Update update, Map<Long, DataUser> map) {
        Long chatId = update.getMessage().getChatId();
        return new SendMessage().setText("Количество баллов за правильные ответы в текущую сессию:" + " " + map.get(chatId).countRight).setChatId(chatId);
    }

    static SendMessage processHelpCommand(Update update) {
        SendMessage message = getCommandsForHelp();
        Long chatId = update.getMessage().getChatId();
        message.setChatId(chatId);
        return message;
    }

    static SendMessage processStopCommand(Update update) {
        Long chatId = update.getMessage().getChatId();
        return new SendMessage().setText("Вы прервали работу бота, чтобы начать снова, введите команду /start").setChatId(chatId);
    }

    private static SendMessage getCommandsForHelp() {
        StringBuilder text = new StringBuilder();
        text.append("This is a list of commands bot:").append("\n")
                .append("/start - запускает цикл вопросов-ответов по языкам программирования")
                .append("\n").append("/stop - останавливает цикл вопросов-ответов бота").append("\n")
                .append("/rating - показывает таблицу с количеством правильныз ответов текущих пользователей").append("\n")
                .append("/ballnow - показывет балл пользователя").append("\n")
                .append("/switch - дает возможность поменять тему вопросов");
        return new SendMessage().setText(text.toString());
    }

}
