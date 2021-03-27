package JikJik.Pages;

import JikJik.Models.*;

import java.util.LinkedList;
import java.util.Scanner;

public class ShowTweets {
    Users users;
    User currentUser;


    ShowTweets(Users users, User currentUser) {
        this.users = users;
        this.currentUser = currentUser;
    }


    public void forward(Tweet tweet) {
        LinkedList<Room> rm = users.getChats().userRoom(currentUser.getUsername());
        rm.removeIf(room -> !users.searchUsername(room.getOwner1()).isActive() || !users.searchUsername(room.getOwner2()).isActive());
        int i = 0;
        System.out.println("[0] Forward to all available chats");
        System.out.println("Forward to User...\n");
        LinkedList<String> usedChats = new LinkedList<>();
        for (Room r : rm) {
            if (!r.getOwner1().equals(currentUser.getUsername())){
                usedChats.add(r.getOwner1());
            }
            else if (!r.getOwner2().equals(currentUser.getUsername())){
                usedChats.add(r.getOwner2());
            }
            System.out.println("[" + (i + 1) + "] " + r.getOwner1() + " - " + r.getOwner2());
            i++;
        }
        System.out.println("Forward to Categories...");
        int j = 0;
        for (LinkedList<String> str : currentUser.getCategories()) {
            System.out.println("[" + (j + i + 1 ) + "] " + str.get(0));
            j++;
        }


        Scanner ss = new Scanner(System.in);
        boolean a = true;
        while (a) {
            String str = ss.next();
            if (str.equals("0")){
                for (Room room : users.getChats().userRoom(currentUser.getUsername())){
                    users.getChats().makeChat(tweet.getText(),currentUser.getUsername(),room.getRoomID());
                }
                break;
            }else {
                try {
                    int n = Integer.parseInt(str);
                    if (n <= rm.size() && n >= 1) {
                        users.getChats().makeChat(tweet.getText(), currentUser.getUsername(), rm.get(n - 1).getRoomID());
                        System.out.println("Forwarded!");
                        break;
                    } else if (n < j + i + 1) {
                        LinkedList<String> f = currentUser.getCategories().get(n - i - 1);
                        users.log("Tweet forwarded to " + f.get(0));
                        for (int k = 1; k < f.size(); k++) {
                            System.out.println(f.get(k));
                            if (!usedChats.contains(f.get(k))) {
                                String room = users.getChats().makeRoom(currentUser.getUsername(), f.get(k));
                                users.getChats().makeChat(tweet.getText(), currentUser.getUsername(), room);
                                users.log("Tweet " + tweet.getText() + " Forwarded to Category " + f.get(0) + " member " + f.get(k));

                            } else {
                                users.getChats().makeChat(tweet.getText(), currentUser.getUsername(), users.getChats().searchRoom(currentUser.getUsername(), f.get(k)).getRoomID());
                                System.out.println("Forwarded to " + f.get(k));
                                users.log("Tweet " + tweet.getText() + " Forwarded to Category " + f.get(0) + " member " + f.get(k));
                            }
                        }
                        System.out.println("Forwarded!");
                        break;
                    }
                } catch (Exception e) {
                }
                switch (str) {
                    case "back":
                        a = false;
                        break;
                }
            }
            if (a) {
                System.out.println("Invalid command!");
            }
        }
    }


    public void makeComment(Tweet tweet, int type) {
        System.out.println("\t\t\tWhat's your comment ");
        Scanner ss = new Scanner(System.in);
        String text = ss.nextLine();
        users.getTweets().makeTweet(text,tweet.getID(),currentUser.getUsername(), currentUser.getFollowers());
        System.out.println("Successfully made your comment!");
        eachTweet(tweet, type);
    }


    public void eachTweet(Tweet tweet, int type) {
        System.out.println("\t\t\tTweet");
        System.out.println("\n" + tweet.getOwner() + ":\t" + tweet.getText());
        System.out.println("Likes: " + tweet.getLikes().size() + "\n");
        int i = 0;
        LinkedList<Tweet> comments = users.getTweets().getComments(tweet.getID(),users);
        for (Tweet comment : comments) {
            System.out.println("[" + (i + 1) + "] " + comment.getOwner() + ":  " + comment.getText());
            i++;
        }
        System.out.println("\n[A] Make new comment");
        System.out.println("[B] Retweet");
        if (!tweet.getLikes().contains(currentUser.getUsername())) {
            System.out.println("[C] Like");
        } else {
            System.out.println("[C] Unlike");
        }
        System.out.println("[D] Forward");
        System.out.println("[E] Report Spam");
        if (!tweet.getOwner().equals(currentUser.getUsername())) {
            System.out.println("[F] User Page");
            if (currentUser.getBlackList().contains(tweet.getOwner())) {
                System.out.println("[G] Unblock User");
            } else {
                System.out.println("[G] Block User");
            }
            if (!currentUser.getMuted().contains(tweet.getOwner())) {
                System.out.println("[H] Mute User");
            } else {
                System.out.println("[H] Unmute User");
            }
        }
        boolean a = true;
        Scanner ss = new Scanner(System.in);
        while (a) {
            String str = ss.next();
            try {
                int n = Integer.parseInt(str);
                if (n <= comments.size() && n >= 1) {
                    eachTweet(comments.get(n - 1), type);
                    break;
                }
            } catch (Exception e) {
            }
            switch (str) {
                case "back":
                    a = false;
                    break;
                case "A":
                    a = false;
                    makeComment(tweet, type);
                    break;
                case "B":
                    a = false;
                    users.getTweets().reTweet(tweet, currentUser);
                    System.out.println("Retweeted successfully");
                    break;
                case "C":
                    a = false;
                    users.getTweets().likeTweet(currentUser, tweet);
                    System.out.println("Liked!");
                    eachTweet(tweet, type);
                    break;
                case "D":
                    a = false;
                    forward(tweet);
                    break;
                case "E":
                    System.out.println("Reported!");
                    users.log("User reported");
                    break;
                case "F":
                    OtherProfile otherProfile = new OtherProfile(users, currentUser, users.searchUsername(tweet.getOwner()));
                    users.log("Showed other profiles");
                    otherProfile.show();
                    break;
                case "G":
                    a = false;
                    users.blockProfile(currentUser, tweet.getOwner());
                    System.out.println("User Blocked");
                    users.log("User blocked");
                    break;
                case "H":
                    users.muteProfile(currentUser, tweet.getOwner());
                    System.out.println("User Muted!");
                    users.log("User muted");
                    break;
            }
            if (a) {
                eachTweet(tweet, type);
            }
        }
        if (!tweet.getParent().equals("0")){
            eachTweet(users.getTweets().search(tweet.getParent()),type);
        } else {
            showTweets(type);
        }
    }

    public void showTweets(int type) {
        users.log("Tweets showed");
        LinkedList<Tweet> tw = users.getTweets().showTweetOwnPage(users,currentUser.getUsername(), type);
        System.out.println("\t\t\tYour Tweets:\n");
        if (tw.isEmpty()) {
            System.out.println("No Tweets");
        } else {
            int i = 0;
            for (Tweet tweet : tw) {
                if (tweet.getParent().equals("0") && !currentUser.getMuted().contains(tweet.getOwner())) {
                    if (!tweet.isRet()) {
                        System.out.println("\n" + "[" + (i + 1) + "] " + tweet.getOwner() + ":  " + tweet.getText());
                    } else {
                        System.out.println("\n" + "[" + (i + 1) + "] " + "(Retweeted) " + tweet.getOwner() + ":  " + tweet.getText());
                    }
                    i++;
                }
            }
        }
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        while (a) {
            String str = ss.next();
            try {
                int n = Integer.parseInt(str);
                if (n <= tw.size() && n >= 1) {
                    eachTweet(tw.get(n - 1), type);
                    break;
                }
            } catch (Exception e) {
            }
            switch (str) {
                case "back":
                    a = false;
                    break;
            }
            if (a) {
            }
        }
    }
}
