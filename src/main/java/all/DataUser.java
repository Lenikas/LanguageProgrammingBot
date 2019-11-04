package all;

public class DataUser {
    public int index;
    public Question[] currentData;

    public DataUser(int ind, Question[] data) {
        this.index = ind;
        this.currentData = data;
    }

    public DataUser(int ind) {
        this.index = ind;
    }

    public DataUser() {}

    public void setIndex(int index) {
        this.index = index;
    }

    public void setCurrentData(Question[] currentData) {
        this.currentData = currentData;
    }
}
