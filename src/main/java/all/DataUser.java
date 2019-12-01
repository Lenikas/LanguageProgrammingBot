package all;

public class DataUser {
    public int index;
    public Question[] currentData;
    public int countRight;
    public String userName;

    public DataUser(int ind, Question[] data) {
        this.index = ind;
        this.currentData = data;
        this.countRight = 0;
    }

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
}
