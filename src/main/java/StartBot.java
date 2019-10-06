import interactive.console.ConsoleInteractive;
import interactive.Interactive;
import org.json.simple.JSONArray;

public class StartBot {

    public static void main(String[] args) {
        Bot newBot = new Bot();
        while (true) {
            JSONArray array = newBot.readJson();
            newBot.doLoopOneQuestion(array);
        }
    }

    private void foo() {
        Interactive interactive = new ConsoleInteractive();
        Bot newBot = new Bot();
        while (true) {
            String command = interactive.readCommand();
//            String result = newBot.sendCommand();
//            interactive.send(result);
        }
    }
}
