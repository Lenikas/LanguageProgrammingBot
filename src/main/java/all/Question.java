package all;

import org.json.simple.JSONArray;


/**
 * Класс для объекта, включающего в себя конкретный вопрос, варианты ответов для него, правильный ответ, ссылку на информацию
 * по этому вопросу. Из методов геттеры/сеттеры и форматированный вывод.
 */
public class Question {

    private String question;
    private JSONArray variants;
    private String correct;
    private String link;

    public String getQuestion() {
        return question;
    }

    public String getCorrect() {
        return correct;
    }

    public String getLink() {
        return link;
    }

    public JSONArray getVariants() {
        return variants;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setVariants(JSONArray variants) {
        this.variants = variants;
    }

    public void createQuestion(String inQuestion, JSONArray inVariants, String inCorrect, String inLink) {
        question = inQuestion;
        variants = inVariants;
        correct = inCorrect;
        link = inLink;
    }

    public Question(String inQuestion, JSONArray inVariants, String inCorrect, String inLink) {
        this.question = inQuestion;
        this.variants = inVariants;
        this.correct = inCorrect;
        this.link = inLink;
    }

    public Question() {}

    public StringBuilder formatAnswers() {
        StringBuilder result = new StringBuilder();
        for (Object answer : variants) {
            result.append(answer).append("\n");
        }
        return result;
    }

}
