package telegram;

import all.Question;
import all.WorkWithJson;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class BotT extends TelegramLongPollingBot {

    private static final String TOKEN = "902378125:AAFC9rH07vmI4r6yGokf_Wrk3gMVZBKdEa0";
    private static final String USERNAME = "@language_programming_bot";
    private static final Question[] data = WorkWithJson.readJson();
    @Override
    public void onUpdateReceived(Update update) {
        Random random = new Random();
        int index = random.nextInt(data.length);
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/start")) {
                    try {
                        execute(sendInlineKeyboardMessage(update.getMessage().getChatId(), getRandomQuestionWithAnswers(index)));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            processCallBack(update, index);
        }
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    private static SendMessage sendInlineKeyboardMessage(long chatId, String question) {
        InlineKeyboardMarkup inlineKM = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            InlineKeyboardButton button = new InlineKeyboardButton().setText(Integer.toString(i)).setCallbackData(Integer.toString(i));
            keyboardButtonsRow.add(button);
        }
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKM.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText(question).setReplyMarkup(inlineKM);
    }

    private static String getRandomQuestionWithAnswers(Integer index) {
        return data[index].question + "\n" + data[index].formatAnswers();
    }

    private void processCallBack(Update update, Integer index) {
        SendMessage userAnswer = new SendMessage().setText(update.getCallbackQuery().getData()).setChatId(update.getCallbackQuery().getMessage().getChatId());
        if (userAnswer.getText().equals(data[index].correct)) {
            try {
                execute(new SendMessage().setText("Yes, you right").setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (!userAnswer.getText().equals(data[index].correct)){
            try {
                execute(new SendMessage().setText("No, you not right").setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}
