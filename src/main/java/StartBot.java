public class StartBot {

    public static void main(String[] args) {
        Bot newBot = new Bot();
        String message = Bot.readMessage();
        Bot.processCommand(message);
    }
}
