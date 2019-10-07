package all;

import interactive.console.ConsoleInteractive;
import interactive.Interactive;
import java.util.Random;


public class StartBot {

    public static void main(String[] args) {
        Bot newBot = new Bot();
        String command = Bot.readMessage();
        Boolean IsStart = Bot.processCommand(command);
        WorkWithJson readJson = new WorkWithJson();
        Question[] array = readJson.readJson();
        while (true & IsStart) {
            Random random = new Random();
            int number = random.nextInt(array.length);
            Question question = array[number];
            question.printQuestion();
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
