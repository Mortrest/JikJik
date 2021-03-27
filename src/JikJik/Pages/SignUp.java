package JikJik.Pages;

import JikJik.Models.User;
import JikJik.Models.Users;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class SignUp extends Page{
    Users users;
    public SignUp(Users users){
        super();
        this.users = users;
    }
    @Override
    public void show() {
        users.log("Sign up page");
        super.show();
        System.out.println("\t\t\t\tSignUp\n");
        boolean a = false;
        Scanner ss = new Scanner(System.in);
        String username;
        String email;
        while (true){
            System.out.println("Enter your username: ");
            username = ss.next();
            System.out.println("Enter your email: ");
            email = ss.next();
            if (users.isViable(username,email)){
                break;
            }
            System.out.println("Username or Email already taken!");
        }
        System.out.println("Enter your password: ");
        String password = ss.next();
        System.out.println("Enter your first name: ");
        String firstName = ss.next();
        System.out.println("Enter your last name: ");
        String lastName = ss.next();
        System.out.println("Enter your phone number: ");
        String phoneNumber = ss.next();
        System.out.println("Enter your birth date: ");
        String birtDate = ss.next();

        // Inja bayad email ina ham begiri!
        users.log(username + " Signed up");
        System.out.println("You're now registered!");
        Random random = new Random();
        int rand = random.nextInt(100000);
        User user = new User(Integer.toString(rand),firstName,lastName,username,password,"",phoneNumber,email,birtDate);
        users.signUp(user);
        SignIn signin = new SignIn(users);
        signin.show();
    }
}
