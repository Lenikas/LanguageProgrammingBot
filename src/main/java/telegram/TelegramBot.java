package telegram;

import all.DataUser;
import all.Question;
import all.WorkWithJson;
import org.apache.logging.log4j.LogManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class TelegramBot extends TelegramLongPollingBot {

    private static final String TOKEN = "902378125:AAFC9rH07vmI4r6yGokf_Wrk3gMVZBKdEa0";
    private static final String USERNAME = "@language_programming_bot";
    private static final Question[] data_py = WorkWithJson.readJson("question.json");
    private static final Question[] data_sharp = WorkWithJson.readJson("question_sharp.json");
    private static Map<Long, DataUser> map = new HashMap<>();
    private static ArrayList<String> buttonsAnswerQuestion = new ArrayList<>(Arrays.asList("1", "2", "3", "4"));
    private static ArrayList<String> buttonsChoseTheme = new ArrayList<>(Arrays.asList("Python", "C#"));



    @Override
    public void onUpdateReceived(Update update) {

        long chatId = -1;
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/help")) {
                    try {
                        SendMessage message = Commands.processHelpCommand(update);
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                if (update.getMessage().getText().equals("/start")) {
                    try {
                        chatId = update.getMessage().getChatId();
                        map.remove(chatId);
                        DataUser newUser = new DataUser(-1);
                        map.put(chatId, newUser);
                        execute(Buttons.sendInlineKeyboardMessage(chatId,"По какому языку вы хотите тест?", buttonsChoseTheme));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                if (update.getMessage().getText().equals("/stop")) {
                    try {
                        chatId = update.getMessage().getChatId();
                        map.remove(chatId);
                        execute(new SendMessage().setText("Вы прервали работу бота, чтобы начать снова, введите команду /start").setChatId(chatId));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            //в чем тут смысл я не догнал
            if (map.get(chatId).index == -1) {
                map.get(chatId).currentData = WorkWithQuestions.answerOfCheckLanguage(update, data_py, data_sharp);
                //WorkWithQuestions.answerOfCheckLanguage(update, chatId, map, data_py, data_sharp);
            }
            else {
                try {
                    execute(WorkWithQuestions.processAnswerQuestion(update, chatId, map));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            execute(Buttons.sendInlineKeyboardMessage(chatId, WorkWithQuestions.getRandomQuestionWithAnswers(chatId, map), buttonsAnswerQuestion));
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

}
