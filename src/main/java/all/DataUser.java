package all;

/**
 * Класс пользователя, точнее данных,которые мы храним о каждом пользователе, содержит поля, нужные для генерации вопросов
 * для пользователя и оценки его ответов, из методов только конструкторы и геттеры/сеттеры.
 */
public class DataUser {
    private int index;
    private Question[] currentData;
    private int countRight;
    private String userName;


    public DataUser(int ind, int count, String name) {
        this.index = ind;
        this.countRight = count;
        this.userName = name;
    }

    public DataUser() {}

    public void setIndex(int index) {
        this.index = index;
    }

    public void setCurrentData(Question[] currentData) {
        this.currentData = currentData;
    }

    public int getIndex() {
        return index;
    }

    public int getCountRight() {
        return countRight;
    }

    public String getUserName() {
        return userName;
    }

    public Question[] getCurrentData() {
        return currentData;
    }

    public void setCountRight(int countRight) {
        this.countRight = countRight;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void changeCount(int a) {
        this.countRight = this.countRight + a;
    }
}
