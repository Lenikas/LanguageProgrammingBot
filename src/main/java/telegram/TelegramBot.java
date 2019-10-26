package telegram;

import all.Question;
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
    //создал второй джсон для вопросов по другой теме, переменная ниже будет получать значение либо парса вопросов по питону,либо по шарпу, в зависимости от того,какую тему мы выбрали,
    //сейчас я не доделал эту фичу и по умолчанию стоит джсон с питоном
    //я хочу прикрутить это к команде старт,чтобы на нее он давал выбор темы а потом уже в основном методе крутились вопросы
    private static Question[] currentData = data_py;
    public static Map<Long, Integer> map = new HashMap<Long, Integer>();

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = -1;
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                processCommand(update);
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

    public SendMessage getCommands() {
        String text = "This is a list of commands bot:" + "\n" +
                "/start - запускает цикл вопросов-ответов по языкам программирования" + "\n" +
                "/stop - останавливает цикл вопросов-ответов бота";
        return new SendMessage().setText(text);
    }

    public void processCommand(Update update) {
        //я не догоняю как тут надо сделать с чат айди, если неверно, то фикси
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
                //хочу чтобы тут выдавались кнопки для выбора темы,а дальше все работало,но не могу сделать,получается либо кнопки сделать а потом ничего не работает,либо сразу идут вопросы,как сейчас
                execute(sendInlineKeyboardMessage(chatId, getRandomQuestionWithAnswers(chatId)));
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

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

//    private static SendMessage chooseTheme(long chatId) {
//        //копирует метод ниже, но я не придумал,как сделать общий, здесь создаются кнопки для выбора темы
//        InlineKeyboardMarkup inlineKM = new InlineKeyboardMarkup();
//        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
//        InlineKeyboardButton button1 = new InlineKeyboardButton().setText("Python").setCallbackData("Python");
//        keyboardButtonsRow.add(button1);
//        InlineKeyboardButton button2 = new InlineKeyboardButton().setText("C#").setCallbackData("C#");
//        keyboardButtonsRow.add(button2);
//        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        rowList.add(keyboardButtonsRow);
//        inlineKM.setKeyboard(rowList);
//        return new SendMessage().setChatId(chatId).setText("Выберите: ").setReplyMarkup(inlineKM);
//    }

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
        map.put(chatId, random.nextInt(currentData.length));
        return currentData[map.get(chatId)].question + "\n" + currentData[map.get(chatId)].formatAnswers();
    }

//    private void processChooseTheme(Update update, Long chatId) {
//        SendMessage userAnswer = new SendMessage().setText(update.getCallbackQuery().getData()).setChatId(update.getCallbackQuery().getMessage().getChatId());
//        if (userAnswer.getText().equals("Python")) {
//            currentData = data_py;
//            try {
//                execute(sendInlineKeyboardMessage(chatId, getRandomQuestionWithAnswers(chatId)));
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//        if (userAnswer.getText().equals("C#")) {
//            currentData = data_sharp;
//            try {
//                execute(sendInlineKeyboardMessage(chatId, getRandomQuestionWithAnswers(chatId)));
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private void processCallBack(Update update, Long chatId) {
        SendMessage userAnswer = new SendMessage().setText(update.getCallbackQuery().getData()).setChatId(update.getCallbackQuery().getMessage().getChatId());
        if (userAnswer.getText().equals(currentData[map.get(chatId)].correct)) {
            try {
                execute(new SendMessage().setText("Да, ваш ответ верный!").setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (!userAnswer.getText().equals(currentData[map.get(chatId)].correct)){
            try {
                String str = "Нет, ваш ответ неверный, дополнительная информация на эту тему:" + " " + currentData[map.get(chatId)].link;
                execute(new SendMessage().setText(str).setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}
