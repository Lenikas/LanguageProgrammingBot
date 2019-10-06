package interactive.console;

import interactive.Interactive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInteractive implements Interactive {
    @Override
    public String readCommand() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "AAA";
    }

    @Override
    public void send(final String message) {
        System.out.println(message);
    }
}
