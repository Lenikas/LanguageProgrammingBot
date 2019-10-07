package all;

import java.util.Scanner;

public class Bot {

    public static String readMessage() {
        Scanner in = new Scanner(System.in);
        System.out.print("What do you want to do? If you don't know what it is, type /help. ");
        String answer = in.nextLine();
        return answer;
    }

    public static boolean processCommand(String command) {
        switch (command)
        {
            case "/help" :
                System.out.println("Hello, i am Language Programming Bot, it is my commands." +
                        " To start the test, type \\start, to end \\stop.");
                return false;
            case "/start" :
                System.out.println("The test begins.");
                return true;
            case "/stop" :
                System.out.println("Ok, continue later");
                return false;
            default :
                System.out.println("It is not a command");
                return false;
        }
    }
}
