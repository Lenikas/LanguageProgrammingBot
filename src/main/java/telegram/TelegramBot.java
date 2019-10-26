package telegram;

import all.DataUser;
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


public class TelegramBot extends TelegramLongPollingBot {

    private static final String TOKEN = "902378125:AAFC9rH07vmI4r6yGokf_Wrk3gMVZBKdEa0";
    private static final String USERNAME = "@language_programming_bot";
    private static final Question[] data_py = WorkWithJson.readJson("question.json");
    private static final Question[] data_sharp = WorkWithJson.readJson("question_sharp.json");
    private static Map<Long, DataUser> map = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = -1;
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                processCommand(update);
            }
        }
        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            if (map.get(chatId).index == -1) {
                answerOfCheckLanguage(update, chatId);
            }
            else {
                processCallBack(update, chatId);
            }
        }
        try {
            execute(sendInlineKeyboardMessage(chatId, getRandomQuestionWithAnswers(chatId)));
        }catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public SendMessage getCommands() {
        String text = "This is a list of commands bot:" + "\n" +
                "/start - запускает цикл вопросов-ответов по языкам программирования" + "\n" +
                "/stop - останавливает цикл вопросов-ответов бота";
        return new SendMessage().setText(text);
    }

    public void processCommand(Update update) {
        if (update.getMessage().getText().equals("/help")) {
            try {
                SendMessage message = getCommands();
                Long chatId = update.getMessage().getChatId();
                message.setChatId(chatId);
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (update.getMessage().getText().equals("/start")) {
            Long chatId = update.getMessage().getChatId();
            try {
                execute(checkLanguage(chatId, "По какому языку вы хотите тест?"));
                map.remove(chatId);
                DataUser newUser = new DataUser();
                newUser.index = -1;
                map.put(chatId, newUser);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (update.getMessage().getText().equals("/stop")) {
            try {
                Long chatId = update.getMessage().getChatId();
                map.remove(chatId);
                execute(new SendMessage().setText("Вы прервали работу бота, чтобы начать снова, введите команду /start").setChatId(chatId));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private static SendMessage checkLanguage(long chatId, String question) {
        InlineKeyboardMarkup inlineKM = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton().setText("Python").setCallbackData("Python");
        keyboardButtonsRow.add(button);
        InlineKeyboardButton buttonTwo = new InlineKeyboardButton().setText("C#").setCallbackData("C#");
        keyboardButtonsRow.add(buttonTwo);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKM.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText(question).setReplyMarkup(inlineKM);
    }

    private void answerOfCheckLanguage(Update update, Long chatId) {
        SendMessage userAnswer = new SendMessage().setText(update.getCallbackQuery().getData()).setChatId(update.getCallbackQuery().getMessage().getChatId());
        if (userAnswer.getText().equals("Python")) {
            map.get(chatId).currentData = data_py;
        } else if (userAnswer.getText().equals("C#")){
            map.get(chatId).currentData  = data_sharp;
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
        DataUser newUser = new DataUser();
        newUser.index = random.nextInt(map.get(chatId).currentData.length);
        newUser.currentData = map.get(chatId).currentData;
        map.remove(chatId);
        map.put(chatId, newUser);
        return map.get(chatId).currentData[map.get(chatId).index].question + "\n"
                + map.get(chatId).currentData[map.get(chatId).index].formatAnswers();
    }

    private void processCallBack(Update update, Long chatId) {
        SendMessage userAnswer =
                new SendMessage().setText(update.getCallbackQuery().getData()).setChatId(update.getCallbackQuery().getMessage().getChatId());
        if (userAnswer.getText().equals(map.get(chatId).currentData[map.get(chatId).index].correct)) {
            try {
                execute(new SendMessage().setText("Да, ваш ответ верный!").setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (!userAnswer.getText().equals(map.get(chatId).currentData[map.get(chatId).index].correct)){
            try {
                String str = "Нет, ваш ответ неверный, дополнительная информация на эту тему:" + " "
                        + map.get(chatId).currentData[map.get(chatId).index].link;
                execute(new SendMessage().setText(str).setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}
