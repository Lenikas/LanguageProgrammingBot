package telegram;

import all.DataUser;
import all.Question;
import all.WorkWithJson;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TelegramBot extends TelegramLongPollingBot {

    private static final Question[] data_py = WorkWithJson.readJson("question.json");
    private static final Question[] data_sharp = WorkWithJson.readJson("question_sharp.json");
    private static Map<Long, DataUser> map = new HashMap<>();
    private static List<String> buttonsAnswerQuestion = List.of("1", "2", "3", "4");
    private static List<String> buttonsChoseTheme = List.of("Python", "C#");



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
            if (map.get(chatId).getIndex() == -1 || update.getCallbackQuery().getData().equals("Python") ||  update.getCallbackQuery().getData()
                    .equals("C#")) {
                map.get(chatId).setCurrentData(WorkWithQuestions.answerOfCheckLanguage(update, data_py, data_sharp));
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
                user.setIndex(random.nextInt(user.getCurrentData().length));
                map.replace(chatId, user);
                InlineKeyboardMarkup keyboard = Buttons.sendInlineKeyboardMessage(buttonsAnswerQuestion);
                execute(new SendMessage().setChatId(chatId).setText(WorkWithQuestions.getQuestionWithAnswers(chatId, map)).setReplyMarkup(keyboard));
            }catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return Config.getTokenUsername().get("Name");
    }

    @Override
    public String getBotToken() {
       return Config.getTokenUsername().get("Token");
    }

    private static SendMessage processCommand(Update update) {
        if (update.getMessage().getText().equals("/help")) {
            return Commands.processHelpCommand(update.getMessage().getChatId());
        }
        if (update.getMessage().getText().equals("/start")) {
            Long chatId = update.getMessage().getChatId();
            if (!map.containsKey(chatId)) {
                String name = update.getMessage().getChat().getUserName();
                DataUser newUser = new DataUser(-1, 0, name);
                map.put(chatId, newUser);
            }
            InlineKeyboardMarkup keyboardTheme = Buttons.sendInlineKeyboardMessage(buttonsChoseTheme);
            return new SendMessage().setChatId(chatId).setText("По какому языку вы хотите тест?").setReplyMarkup(keyboardTheme);
        }
        if (update.getMessage().getText().equals("/switch")) {
            Long chatId = update.getMessage().getChatId();
            InlineKeyboardMarkup keyboardTheme = Buttons.sendInlineKeyboardMessage(buttonsChoseTheme);
            return new SendMessage().setChatId(chatId).setText("По какому языку вы хотите тест?").setReplyMarkup(keyboardTheme);
        }
        if (update.getMessage().getText().equals("/stop")) {
            Long chatId = update.getMessage().getChatId();
            map.remove(chatId);
            return Commands.processStopCommand(update.getMessage().getChatId());
        }
        if (update.getMessage().getText().equals("/ballnow")) {
            return Commands.processBallsCommand(update.getMessage().getChatId(), map);
        }
        if (update.getMessage().getText().equals("/rating")) {
            return Commands.processRatingCommand(update.getMessage().getChatId(), map);
        }
        return new SendMessage().setText("Неопознанная команда! Используйте /help для просмотра доступнных комманд.").setChatId(update.getMessage().getChatId());
    }

}
