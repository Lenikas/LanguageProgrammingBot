public class StartBot {

    public static void main(String[] args) {
        System.out.println("AAA");
        Bot newBot = new Bot();
//        Bot.createQuestion("What this code do:");
//        Bot.createAnswers(new String[]{"a", "b"});
//        String answer = User.chooseAnswer();
//        Bot.processAnswer(answer);
        //String message = Bot.readMessage();
        //Bot.processCommand(message);
        //Bot.parseJson();
        while(true) {
            newBot.readJson();
        }
    }
}
