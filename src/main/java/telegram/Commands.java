package telegram;

import all.DataUser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Map;

class Commands {

    static SendMessage processCommand(Update update, Map<Long, DataUser> map, ArrayList<String> buttonsChoseTheme ) {
        if (update.getMessage().getText().equals("/help")) {
            return processHelpCommand(update);
        }
        if (update.getMessage().getText().equals("/start")) {
            return processStartCommand(update, map,buttonsChoseTheme);
        }
        if (update.getMessage().getText().equals("/stop")) {
            return processStopCommand(update, map);
        }
        return new SendMessage().setText("Неопознанная команда! Используйте /help для просмотра доступнных комманд.").setChatId(update.getMessage().getChatId());
    }

    private static SendMessage processHelpCommand(Update update) {
        SendMessage message = getCommandsForHelp();
        Long chatId = update.getMessage().getChatId();
        message.setChatId(chatId);
        return message;
    }

    private static SendMessage processStopCommand(Update update, Map<Long, DataUser> map) {
        Long chatId = update.getMessage().getChatId();
        map.remove(chatId);
        return new SendMessage().setText("Вы прервали работу бота, чтобы начать снова, введите команду /start").setChatId(chatId);
    }

    private static SendMessage processStartCommand(Update update, Map<Long, DataUser> map, ArrayList<String> buttonsChoseTheme) {
        Long chatId = update.getMessage().getChatId();
        map.remove(chatId);
        DataUser newUser = new DataUser();
        newUser.setIndex(-1);
        map.put(chatId, newUser);
        //плохо, что тут используется метод из другого класса
        return Buttons.sendInlineKeyboardMessage(chatId,"По какому языку вы хотите тест?", buttonsChoseTheme);
    }

    private static SendMessage getCommandsForHelp() {
        String text = "This is a list of commands bot:" + "\n" +
                "/start - запускает цикл вопросов-ответов по языкам программирования" + "\n" +
                "/stop - останавливает цикл вопросов-ответов бота";
        return new SendMessage().setText(text);
    }

}
