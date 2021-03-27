package JikJik.Pages;

import JikJik.Models.User;
import JikJik.Models.Users;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MainMenu extends Page {
    Users users;
    User currentUser;

    public MainMenu(Users users, User currentUser) {
        super();
        this.users = users;
        this.currentUser = currentUser;
    }

    public void privacy() {
        System.out.println("\t\t\t\tPrivacy\n");
        System.out.println("[1] Change to " + (currentUser.isPrivate() ? "Public" : "Private"));
        System.out.println("[2] Change last seen");
        System.out.println("[3] Deactivating the account");
        System.out.println("[4] Change password");
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        while (a) {
            String cmd = ss.next();
            switch (cmd) {
                case "back" -> a = false;
                case "1" -> {
                    currentUser.setPrivate(!currentUser.isPrivate());
                    users.log(currentUser.getUsername() + " Changed privacy");
                    System.out.println("Privacy Changed!");
                }
                case "2" -> {
                    currentUser.setLastSeenAvailable(!currentUser.isLastSeenAvailable());
                    users.log(currentUser.getUsername() + " Changed privacy");
                    System.out.println("Privacy Changed!");
                }
                case "3" -> {
                    a = false;
                    currentUser.setActive(false);
                    users.log(currentUser.getUsername() + " Logged out");
                    Welcome welcome = new Welcome(users);
                    welcome.show();
                }
                case "4" -> {
                    currentUser.setPassword(ss.next());
                    users.log(currentUser.getUsername() + " Changed password");
                    System.out.println("Changed Password successfully!");
                }
            }
            if (a) {
                System.out.println("Invalid Command!");
                showSettings();
            }
        }
    }

    public void showSettings() {
        System.out.println("\t\t\t\tSettings\n");
        System.out.println("[1] Privacy Settings");
        System.out.println("[2] Delete Profile");
        System.out.println("[3] Log out");
        Scanner ss = new Scanner(System.in);
        Welcome welcome = new Welcome(users);
        boolean a = true;
        while (a) {
            String cmd = ss.next();
            switch (cmd) {
                case "back" -> a = false;
                case "1" -> {
                    privacy();
                }
                case "2" -> {
                    users.deleteProfile(currentUser);
                    users.log(currentUser.getUsername() + " Wants to delete his account");
                    System.out.println("Account Deleted!");
                    welcome.show();
                }
                case "3" -> {
                    a = false;
                    LocalDateTime now = LocalDateTime.now();
                    currentUser.setLastSeen(now);
                    users.save();
                    welcome.show();
                }
            }
            if (a) {
                showSettings();
            }
        }
    }

    public void template() {
        System.out.println("\t\t\t\tMain Menu\n");
        System.out.println("[1] Your page");
        System.out.println("[2] Timeline");
        System.out.println("[3] Explore");
        System.out.println("[4] Setting");
        System.out.println("[5] Chat");
    }

    @Override
    public void show() {
        super.show();
        users.log("Entered Main menu");
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        boolean b = true;
        while (a) {
            template();
            String cmd = ss.next();
            switch (cmd) {
                case "1" -> {
                    a = false;
                    OwnProfile ownProfile = new OwnProfile(users, currentUser);
                    ownProfile.show();
                }
                case "2" -> {
                    b = false;
                    Timeline timeline = new Timeline(users, currentUser);
                    timeline.showTweets(2);
                }
                case "3" -> {
                    a = false;
                    Explore explore = new Explore(users, currentUser);
                    explore.show();
                }
                case "4" -> showSettings();
                case "5" -> {
                    b = false;
                    ChatPage chatPage = new ChatPage(users, currentUser);
                    chatPage.showRoom();
                }
            }
            if (a && b) {
                System.out.println("Invalid Command!");
            }
        }
    }
}

