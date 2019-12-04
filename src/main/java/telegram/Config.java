package telegram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


/**
 * Класс с методом, извлекающим токен и юсернейм бота из файла, лежащего в папке ресурсов.
 */
class Config {

    static Map<String, String> getTokenUsername() {
        String token = "";
        String name = "";
        Map<String, String> map = new HashMap<>();
        try (BufferedReader bReader = new BufferedReader(new InputStreamReader(
                        Config.class.getResourceAsStream("/" + "tokenusername.txt")))) {
            token = bReader.readLine();
            name = bReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("Token", token);
        map.put("Name", name);
        return map;
    }
}

