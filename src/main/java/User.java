import java.util.Scanner;

public class User {

    public static String chooseAnswer() {
        //ввод пользователем варианта ответа из предложенных
        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        String answer = in.nextLine();
        return answer;
    }

    public static void responseVerification(String correct){
        String actualAnswer = User.chooseAnswer();
        if (correct.equals(actualAnswer)) {
            System.out.println("Yes, you right!");
        }
        else {
            System.out.println("No, it is not right");
        }
    }
}
//тестим ветки
