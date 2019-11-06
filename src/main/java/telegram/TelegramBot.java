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
                try {
                    execute(processCommand(update, map, buttonsChoseTheme));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            if (map.get(chatId).index == -1) {
                map.get(chatId).currentData = WorkWithQuestions.answerOfCheckLanguage(update, data_py, data_sharp);
            }
            else {
                try {
                    execute(WorkWithQuestions.processAnswerQuestion(update, chatId, map));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            try {
                Random random = new Random();
                DataUser user = map.get(chatId);
                user.setIndex(random.nextInt(user.currentData.length));
                map.replace(chatId, user);
                execute(Buttons.sendInlineKeyboardMessage(chatId, WorkWithQuestions.getRandomQuestionWithAnswers(chatId, map), buttonsAnswerQuestion));
            }catch (TelegramApiException e) {
                e.printStackTrace();
            }
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

    private static SendMessage processCommand(Update update, Map<Long, DataUser> map, ArrayList<String> buttonsChoseTheme) {
        if (update.getMessage().getText().equals("/help")) {
            return Commands.processHelpCommand(update);
        }
        if (update.getMessage().getText().equals("/start")) {
            DataUser newUser = new DataUser(-1);
            Long chatId = update.getMessage().getChatId();
            map.put(chatId, newUser);
            return Commands.processStartCommand(update,buttonsChoseTheme);
        }
        if (update.getMessage().getText().equals("/stop")) {
            Long chatId = update.getMessage().getChatId();
            map.remove(chatId);
            return Commands.processStopCommand(update);
        }
        return new SendMessage().setText("Неопознанная команда! Используйте /help для просмотра доступнных комманд.").setChatId(update.getMessage().getChatId());
    }

}
