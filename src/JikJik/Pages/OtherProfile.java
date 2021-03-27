package JikJik.Pages;

import JikJik.Models.*;

import java.time.LocalDateTime;
import java.util.Scanner;

public class OtherProfile extends Page {
    Users users;
    User currentUser;
    User targetUser;

    public OtherProfile(Users users, User currentUser, User targetUser) {
        super();
        this.users = users;
        this.currentUser = currentUser;
        this.targetUser = targetUser;
    }

    public void template() {
        System.out.println("\t\t\t\t" + targetUser.getUsername() + "\n");
        System.out.println(targetUser.getFirstName() + " " + targetUser.getLastName() + "\n" + (targetUser.getInfo().equals("") ? "No Bio!" : targetUser.getInfo()));
        if (targetUser.isPrivate()) {
            System.out.println("Account is private!");
        }
        System.out.println("Followers: " + (targetUser.getFollowers() == null ? "0" : targetUser.getFollowers().size()) + "\tFollowings: " + (targetUser.getFollowing() == null ? "0" : targetUser.getFollowing().size()));
        if (targetUser.isLastSeenAvailable()) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lastSeen = targetUser.getLastSeen();
            if (now.getDayOfYear() == lastSeen.getDayOfYear()){
                System.out.println(Math.abs(now.getHour()-lastSeen.getHour()) + " Hours ago");
            } else {
                System.out.println(Math.abs(now.getDayOfYear() - lastSeen.getDayOfYear()) + " Days ago");
            }
        } else {
            System.out.println("Last seen recently");
        }
    }

    @Override
    public void show() {
        super.show();
        Scanner ss = new Scanner(System.in);
        template();
        boolean flw = false;
        boolean blckd = false;
        boolean pending = false;
        for (Notif nf : users.getNotifs().showNotif(targetUser.getUsername())) {
            if (nf.getType().equals("2")) {
                if (nf.getOwner().equals(targetUser.getUsername()) && nf.getOwner2().equals(currentUser.getUsername())) {
                    pending = true;
                    break;
                }
            }
        }
        if (!pending) {
            for (String str : currentUser.getFollowing()) {
                if (str.equals(targetUser.getUsername())) {
                    flw = true;
                    break;
                }
            }
        }
        if (pending) {
            System.out.println("Request pending!");
        } else if (flw) {
            System.out.println("You're following " + targetUser.getUsername());
            System.out.println("[1] Message");
        } else if (!flw) {
            System.out.println("[1] Follow");
        } else if (currentUser.getBlackList().contains(targetUser.getUsername())) {
            System.out.println("You're Blocked!");
            blckd = true;
        }
        boolean blocked = false;
        if (currentUser.getBlackList().contains(targetUser.getUsername())) {
            System.out.println("[2] Unblock");
            blocked = true;
        } else {
            System.out.println("[2] Block");
        }
        System.out.println("[3] Report");
        if (flw) {
            System.out.println("[4] Unfollow");
        }
        boolean a = true;
        boolean b = true;
        while (a) {
            String str = ss.next();
            switch (str) {
                case "back":
                    a = false;
                    b = false;
                    break;
                case "1":
                    if (!blckd) {
                        a = false;
                        if (flw) {
                            ChatPage chatPage = new ChatPage(users, currentUser);
                            Room r = users.getChats().searchRoom(currentUser.getUsername(), targetUser.getUsername());
                            if (r != null) {
                                chatPage.eachRoom(r.getRoomID());
                            } else {
                                System.out.println("[1] If you want to start a chat");
                                if (ss.next().equals("1")) {
                                    String s = users.getChats().makeRoom(currentUser.getUsername(), targetUser.getUsername());
                                    chatPage.eachRoom(s);
                                }
                            }
                        } else {
                            if (targetUser.isPrivate()) {
                                String text = currentUser.getUsername() + " Requested to follow you!";
                                users.getNotifs().makeRequest(text, targetUser.getUsername(), "2", currentUser.getUsername());
                                System.out.println("Requested!");
                            } else {
                                users.followProfile(currentUser, targetUser.getUsername());
                                System.out.println("User Followed!");
                            }
                        }
                    }
                    break;
                case "2":
                    a = false;
                    System.out.println("User blocked!");
                    users.blockProfile(currentUser, targetUser.getUsername());
                    b = false;
                    break;
                case "3":
                    System.out.println("Thank's for your report!");
                    a = false;
                    break;
                case "4":
                    if (flw) {
                        a = false;
                        System.out.println("User unfollowed!");
                        users.unFollowProfile(currentUser, targetUser.getUsername());
                        break;
                    }
            }
            if (a) {
                System.out.println("Invalid Command!");
            }
        }
        if (b) {
            show();
        }
    }
}
