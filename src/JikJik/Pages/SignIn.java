package JikJik.Pages;

import JikJik.Models.User;
import JikJik.Models.Users;

import java.time.LocalDateTime;
import java.util.Scanner;

public class SignIn extends Page{
    Users users;
    public SignIn(Users users){
        super();
        this.users = users;
    }

    public void template(){
        System.out.println("\t\t\t\tSign in");
        System.out.println();

    }
    @Override
    public void show() {
        users.log("Sign in page");
        super.show();
        Scanner ss = new Scanner(System.in);
        template();
        while (true){
            System.out.println("Enter your username: ");
            String username = ss.next();
            System.out.println("Enter your password: ");
            String password = ss.next();
            try {
                if (users.signIn(username,password)){
                    users.log(username+" Successfully Logged in");
                    System.out.println("Successfully Logged in!\n\n");
//                    OwnProfile ownProfile = new OwnProfile(users,users.searchUsername(username));
//                    ownProfile.show();
                    User user = users.searchUsername(username);
                    LocalDateTime now = LocalDateTime.now();
                    user.setLastSeen(now);
                    users.save();
                    if (!user.isActive()){
                        users.log("Activated the account again");
                        System.out.println("You Activated your account again!");
                        user.setActive(true);
                    }
                    MainMenu mainMenu = new MainMenu(users,users.searchUsername(username));
                    mainMenu.show();
                    break;
                } else {
                    users.log("Password doesn't match");
                    System.out.println("Password doesn't match!");
                }
            } catch (Exception e){
                users.log("ERROR "+e.toString());
            }

        }
    }
}
