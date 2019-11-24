package telegram;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class BotInitializer {
    private static Logger log = Logger.getLogger(BotInitializer.class.getName());

    public static void main(String[] args) {
//        WorkWithMongo newDatabase = new WorkWithMongo();
        ApiContextInitializer.init();
        TelegramBotsApi botapi = new TelegramBotsApi();
        TelegramBot bot = new TelegramBot();
        try {
            botapi.registerBot(bot);
        } catch (TelegramApiException e) {
            log.info(e);
        }
    }
}




