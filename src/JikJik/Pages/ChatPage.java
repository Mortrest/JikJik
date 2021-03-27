package JikJik.Pages;

import JikJik.Models.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class ChatPage extends Page {
    Users users;
    User currentUser;

    public ChatPage(Users users, User currentUser) {
        super();
        this.users = users;
        this.currentUser = currentUser;
    }

    public void eachRoom(String roomID){
        LinkedList<Chat> chats = users.getChats().showChats(roomID);
        users.getChats().seen(currentUser.getUsername(),roomID);
        if (chats == null){
            System.out.println("No Chats!");
        } else {
            int i = 0;
            for (Chat chat : chats) {
                System.out.println("["+ (i+1) + "] "+chat.getOwner() + ":  " + chat.getText());
                i++;
            }

        }
        System.out.println("[0] For chat mode");
        System.out.println("* Double back for return");
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        while (a) {
            boolean b = true;
            String str = ss.next();
            if (!str.equals("0")) {
                try {
                    int n = Integer.parseInt(str);
                    assert chats != null;
                    if (n <= chats.size() && n >= 1) {
                        users.getChats().makeChat(chats.get(n-1).getText(), chats.get(n-1).getOwner(), users.getChats().searchRoom(currentUser.getUsername(), "Saved Messages").getRoomID());
                        System.out.println("Forwarded!");
                        b = false;
                    }
                } catch (Exception e) {
                }
            }
            switch (str) {
                case "back" -> {
                    a = false;
                    break;
                }
                case "0" -> {
                    a = false;
                    Scanner sss = new Scanner(System.in);
                    String text = sss.nextLine();
                    users.getChats().makeChat(text, currentUser.getUsername(), roomID);
                    eachRoom(roomID);
                    break;
                }
            }
            if (a && b) {
                System.out.println("Invalid command!");
            }
        }
    }

    public void showRoom() {
        users.log("Chat page");
        LinkedList<Room> tw = users.getChats().userRoom(currentUser.getUsername());
        System.out.println("\t\t\tYour Chats:\n");
        if (tw.isEmpty()) {
            System.out.println("No Chats");
        } else {
            int i = 0;
            for (Room room : tw) {
                try {
                    if (users.searchUsername(room.getOwner1()).isActive() && users.searchUsername(room.getOwner2()).isActive()) {
                        if (!users.searchUsername(room.getOwner2()).getBlackList().contains(room.getOwner1()) && !users.searchUsername(room.getOwner1()).getBlackList().contains(room.getOwner2())) {
                            System.out.println("\n" + "[" + (i + 1) + "] " + room.getOwner1() + "-" + room.getOwner2() + " " + (room.getOwner1().equals(currentUser.getUsername()) ? room.getUnread1() : room.getUnread2()));
                            i++;
                        }
                    }
                }catch (Exception e){
                }
            }
        }
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        boolean b = false;
        while (a) {
            String str = ss.next();
            try {
                int n = Integer.parseInt(str);
                if (n <= tw.size() && n >= 1) {
                    eachRoom(tw.get(n-1).getRoomID());
                    b = true;
                }
            } catch (Exception e) {
            }
            if (str.equals("back")) {
                a = false;
//                b = false;
                break;
            }
            if (a && !b) {
                System.out.println("Invalid command!");
            } else if (b){
                showRoom();
            }
        }
    }
}

