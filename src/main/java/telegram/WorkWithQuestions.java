package telegram;

import all.DataUser;
import all.Question;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class WorkWithQuestions {

     static Question[] answerOfCheckLanguage(Update update, Question[] data_py, Question[] data_sharp) {
        SendMessage userAnswer = new SendMessage().setText(update.getCallbackQuery().getData()).setChatId(update.getCallbackQuery().getMessage().getChatId());
        if (userAnswer.getText().equals("Python")) {
            return data_py;
        }
//        } else if (userAnswer.getText().equals("C#")) {
//             return data_sharp;
//        }
        return data_sharp;
    }

     static String getRandomQuestionWithAnswers(Long chatId, Map<Long, DataUser> map) {
        Random random = new Random();
        DataUser newUser = new DataUser();
        newUser.setCurrentData(map.get(chatId).currentData);
        newUser.setIndex(random.nextInt(map.get(chatId).currentData.length));
        map.remove(chatId);
        map.put(chatId, newUser);
        return map.get(chatId).currentData[map.get(chatId).index].question + "\n"
                + map.get(chatId).currentData[map.get(chatId).index].formatAnswers();
    }


    static SendMessage processAnswerQuestion(Update update, Long chatId, Map<Long, DataUser> map) {
        SendMessage userAnswer =
                new SendMessage().setText(update.getCallbackQuery().getData()).setChatId(update.getCallbackQuery().getMessage().getChatId());
        if (userAnswer.getText().equals(map.get(chatId).currentData[map.get(chatId).index].correct)) {
            return (new SendMessage().setText("Да, ваш ответ верный!").setChatId(update.getCallbackQuery().getMessage().getChatId()));
        } //else if (!userAnswer.getText().equals(map.get(chatId).currentData[map.get(chatId).index].correct));{
        else {
            String str = "Нет, ваш ответ неверный, дополнительная информация на эту тему:" + " "
                    + map.get(chatId).currentData[map.get(chatId).index].link;
            return (new SendMessage().setText(str).setChatId(update.getCallbackQuery().getMessage().getChatId()));
        }
    }
}
