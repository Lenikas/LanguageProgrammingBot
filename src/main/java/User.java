import java.util.Scanner;

public class User {

    public static String chooseAnswer() {
        //ввод пользователем варианта ответа из предложенных
        Scanner sc = new Scanner(System.in);
        String answer = sc.next();
        return answer;
    }
}
