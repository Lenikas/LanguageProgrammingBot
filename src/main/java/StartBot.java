import org.json.simple.JSONArray;

public class StartBot {

    public static void main(String[] args) {
        Bot newBot = new Bot();
        while(true) {
            JSONArray array = newBot.readJson();
            newBot.doLoopOneQuestion(array);
        }
    }
}
