package telegram;

import all.DataUser;
import all.Question;
import all.WorkWithJson;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.*;

public class TelegramBot extends TelegramLongPollingBot {

    private static final Question[] data_py = WorkWithJson.readJson("question.json");
    private static final Question[] data_sharp = WorkWithJson.readJson("question_sharp.json");
    private static Map<Long, DataUser> map = new HashMap<>();
    private static List<String> buttonsAnswerQuestion = new ArrayList<>(Arrays.asList("1", "2", "3", "4"));
    private static List<String> buttonsChoseTheme = new ArrayList<>(Arrays.asList("Python", "C#"));



    @Override
    public void onUpdateReceived(Update update) {
        long chatId = -1;
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                try {
                    execute(processCommand(update));
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
                InlineKeyboardMarkup keyboar = Buttons.sendInlineKeyboardMessage(buttonsAnswerQuestion);
                //execute(Buttons.sendInlineKeyboardMessage(chatId, WorkWithQuestions.getRandomQuestionWithAnswers(chatId, map), buttonsAnswerQuestion));
                execute(new SendMessage().setChatId(chatId).setText(WorkWithQuestions.getQuestionWithAnswers(chatId, map)).setReplyMarkup(keyboar));
            }catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return Config.Token;
    }

    @Override
    public String getBotToken() {
       return Config.Name;
    }

    private static SendMessage processCommand(Update update) {
        if (update.getMessage().getText().equals("/help")) {
            return Commands.processHelpCommand(update);
        }
        if (update.getMessage().getText().equals("/start")) {
            DataUser newUser = new DataUser(-1);
            Long chatId = update.getMessage().getChatId();
            map.put(chatId, newUser);
            InlineKeyboardMarkup keyboardTheme = Buttons.sendInlineKeyboardMessage(buttonsChoseTheme);
            return new SendMessage().setChatId(chatId).setText("По какому языку вы хотите тест?").setReplyMarkup(keyboardTheme);
            //return Commands.processStartCommand(update,buttonsChoseTheme);
        }
        if (update.getMessage().getText().equals("/stop")) {
            Long chatId = update.getMessage().getChatId();
            map.remove(chatId);
            return Commands.processStopCommand(update);
        }
        return new SendMessage().setText("Неопознанная команда! Используйте /help для просмотра доступнных комманд.").setChatId(update.getMessage().getChatId());
    }

}
