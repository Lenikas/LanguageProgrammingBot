package telegram;

import all.DataUser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Map;

class Commands {



    static SendMessage processHelpCommand(Update update) {
        SendMessage message = getCommandsForHelp();
        Long chatId = update.getMessage().getChatId();
        message.setChatId(chatId);
        return message;
    }

    static SendMessage processStopCommand(Update update) {
        Long chatId = update.getMessage().getChatId();
//        map.remove(chatId);
        return new SendMessage().setText("Вы прервали работу бота, чтобы начать снова, введите команду /start").setChatId(chatId);
    }

    static SendMessage processStartCommand(Update update, ArrayList<String> buttonsChoseTheme) {
        Long chatId = update.getMessage().getChatId();
//        map.remove(chatId);
//        DataUser newUser = new DataUser(-1);
//        map.put(chatId, newUser);
        return Buttons.sendInlineKeyboardMessage(chatId,"По какому языку вы хотите тест?", buttonsChoseTheme);
    }

    private static SendMessage getCommandsForHelp() {
        String text = "This is a list of commands bot:" + "\n" +
                "/start - запускает цикл вопросов-ответов по языкам программирования" + "\n" +
                "/stop - останавливает цикл вопросов-ответов бота";
        return new SendMessage().setText(text);
    }

}
