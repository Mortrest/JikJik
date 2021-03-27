package JikJik.Pages;

import JikJik.Models.User;
import JikJik.Models.Users;

import java.util.Scanner;

public class Explore extends ShowTweets{

    public Explore(Users users, User currentUser) {
        super(users, currentUser);
    }

    public void template() {
        System.out.println("\t\t\t\tExplore\n");
        System.out.println("[1] Search ");
        System.out.println("[2] Tweets ");
    }

    public void search(){
        System.out.println("Enter the username: ");
        Scanner ss = new Scanner(System.in);
        String username = ss.nextLine();
        while (true) {
            if (username.equals("back")){
                break;
            }
            if (users.searchUsername(username) != null) {
                if (users.searchUsername(username).isActive()) {
                    users.log(username + " Searched");
                    OtherProfile otherProfile = new OtherProfile(users, currentUser, users.searchUsername(username));
                    otherProfile.show();
                } else {
                    System.out.println("User not found!");
                }
                break;
            } else {
                System.out.println("User not found!");
                username = ss.nextLine();
            }
        }
    }

    public void show(){
        Scanner ss = new Scanner(System.in);
        users.log("Explore page");
        boolean a = true;
        while (a) {
            template();
            String cmd = ss.next();
            switch (cmd) {
                case "back":
                    a = false;
                    break;
                case "1":
                    a = false;
                    search();
                    break;
                case "2":
                    this.showTweets(3);
                    break;
            }
            if (a) {
            }
        }
        MainMenu mainMenu = new MainMenu(users,currentUser);
        mainMenu.show();

    }
}
