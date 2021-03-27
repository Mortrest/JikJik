package JikJik.Models;

import java.util.LinkedList;

public class Tweet {
    private final String text;
    private final String ID;
    private final String parent;
    String owner;
    LinkedList<String> users;
//    Date date;
    private final String date;
    LinkedList<String> likes;
    boolean isRet;

    @Override
    public String toString() {
        return "{" +
                "ID='" + ID + '\'' +
                ", owner='" + owner + '\'' + ", text='" + text +
                '}';
    }

    public Tweet(String ID, String text,String parent, LinkedList<String > users, String date, LinkedList<String> likes, String owner,boolean isRet) {
        this.ID = ID;
        this.text = text;
        this.parent = parent;
        this.users = users;
        this.date = date;
        this.owner = owner;
        this.likes = likes;
        this.isRet = isRet;
    }

    public String getParent() {
        return parent;
    }

    public String getText() {
        return text;
    }

    public String getID() {
        return ID;
    }

    public String getOwner() {
        return owner;
    }

    public String getDate() {
        return date;
    }

    public boolean isRet() {
        return isRet;
    }

    public LinkedList<String> getLikes() {
        return likes;
    }

    public LinkedList<String> getUsers() {
        return users;
    }
}
