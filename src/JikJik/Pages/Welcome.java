package JikJik.Pages;

import JikJik.Models.User;
import JikJik.Models.Users;

import java.util.Scanner;

public class Welcome extends Page{
    Users users;
    public Welcome(Users users){
        super();
        this.users = users;
    }
    @Override
    public void show() {
        super.show();
        users.log("Welcome Page");
        System.out.println("\t\t\tWelcome to Jik Jik\n");
        System.out.println("Do you have an account? ");
        System.out.println("[1] Sign in");
        System.out.println("[2] Sign up");
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        while (a) {
            String cmd = ss.next();
            switch (cmd) {
                case "1":
                    SignIn signIn = new SignIn(users);
                    signIn.show();
                    a = false;
                    break;
                case "2":
                    SignUp signUp = new SignUp(users);
                    signUp.show();
                    a = false;
                    break;
            }
            if (a) {
                System.out.println("Invalid Command");
            }
        }
    }
}
