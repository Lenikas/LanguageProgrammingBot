package telegram;

import all.Question;
import all.WorkWithJson;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class BotT extends TelegramLongPollingBot {
    //private long chat_id;
    @Override
    public void onUpdateReceived(Update update) {
        WorkWithJson readJson = new WorkWithJson();
        Question[] array = readJson.readJson();
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        //chat_id = update.getMessage().getChatId();
        //sendMessage.setText(input(update.getMessage().getText()));
        if (update.getMessage().getText().equals("Hello")) {
            sendMessage.setText("ABC");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "@language_programming_bot";
    }

    @Override
    public String getBotToken() {
        return "902378125:AAFC9rH07vmI4r6yGokf_Wrk3gMVZBKdEa0";
    }

    public String input(String s) {
        if (s.contains("hi")) {
            return "avksnv";
        }
        return s;
    }
}
