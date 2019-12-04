package telegram;

import all.DataUser;
import all.Question;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;


/**
 * Класс для обработки действий пользователя при ответе на вопросы, нажатия кнопок.
 */
class WorkWithQuestions {

     static Question[] answerOfCheckLanguage(Update update, Question[] data_py, Question[] data_sharp) {
        if (update.getCallbackQuery().getData().equals("Python")) {
            return data_py;
        }
        return data_sharp;
    }


     static String getQuestionWithAnswers(Long chatId, Map<Long, DataUser> map) {
        return map.get(chatId).getCurrentData()[map.get(chatId).getIndex()].getQuestion() + "\n"
                + map.get(chatId).getCurrentData()[map.get(chatId).getIndex()].formatAnswers();
    }


    static SendMessage processAnswerQuestion(Update update, Long chatId, Map<Long, DataUser> map) {
        if (update.getCallbackQuery().getData().equals(map.get(chatId).getCurrentData()[map.get(chatId).getIndex()].getCorrect())) {
            //сделать изменение одного пользователя, а не всей мапки
            map.get(chatId).changeCount(1);
            return (new SendMessage().setText("Да, ваш ответ верный!").setChatId(update.getCallbackQuery().getMessage().getChatId()));
        }
        else {
            map.get(chatId).changeCount(-1);
            String str = "Нет, ваш ответ неверный, дополнительная информация на эту тему:" + " "
                    + map.get(chatId).getCurrentData()[map.get(chatId).getIndex()].getLink();
            return (new SendMessage().setText(str).setChatId(update.getCallbackQuery().getMessage().getChatId()));
        }
    }
}
