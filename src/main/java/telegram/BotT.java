package telegram;

import all.Question;
import all.User;
import all.WorkWithJson;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;


public class BotT extends TelegramLongPollingBot {

    private static final String TOKEN = "902378125:AAFC9rH07vmI4r6yGokf_Wrk3gMVZBKdEa0";
    private static final String USERNAME = "@language_programming_bot";
    private static final Question[] data = WorkWithJson.readJson();
    public static Map<Long, Integer> map = new HashMap<Long, Integer>();
    @Override
    public void onUpdateReceived(Update update) {
        long chatId = -1;
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/start")) {
                    try {
                        chatId = update.getMessage().getChatId();
                        execute(sendInlineKeyboardMessage(chatId, getRandomQuestionWithAnswers(chatId)));
                    }catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                else if (update.getMessage().getText().equals("/stop")) {
                    map.remove(chatId);
                    return;
                }
            }
        }
        try {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            execute(new SendMessage().setText("").setChatId(chatId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        if (update.hasCallbackQuery()) {
            processCallBack(update, chatId);
        }
        try {
            execute(sendInlineKeyboardMessage(chatId, getRandomQuestionWithAnswers(chatId)));
        }catch (TelegramApiException e) {
            e.printStackTrace();
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

    private static String getRandomQuestionWithAnswers(Long chatId) {
        Random random = new Random();
        map.remove(chatId);
        map.put(chatId, random.nextInt(data.length));
        return data[map.get(chatId)].question + "\n" + data[map.get(chatId)].formatAnswers();
    }

    private void processCallBack(Update update, Long chatId) {
        SendMessage userAnswer = new SendMessage().setText(update.getCallbackQuery().getData()).setChatId(update.getCallbackQuery().getMessage().getChatId());
        if (userAnswer.getText().equals(data[map.get(chatId)].correct)) {
            try {
                execute(new SendMessage().setText("Yes, you right").setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (!userAnswer.getText().equals(data[map.get(chatId)].correct)){
            try {
                execute(new SendMessage().setText("No, you not right").setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}
