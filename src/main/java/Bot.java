import java.util.Scanner;

public class Bot {

    public static String readMessage() {
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    public static void processCommand(String command) {
        switch (command)
        {
            case "/help" :
                System.out.println("Hello, i am Language Programming Bot, it is my commands");
                break;
            case "/start" :
                System.out.println("Are you ready to start?");
                break;
            case "/stop" :
                System.out.println("Ok, continue later");
                break;
            default :
                System.out.println("It is not a command");
                break;
        }
    }
}
