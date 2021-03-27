package JikJik.Models;

public class Room {
    private final String roomID;
    private final String date;
    private final String owner1;
    private final String owner2;
    protected int unread1;
    protected int unread2;

    public Room(String roomID, String date, String owner1,String owner2,int unread1,int unread2) {
        this.roomID = roomID;
        this.date = date;
        this.owner1 = owner1;
        this.owner2 = owner2;
        this.unread1 = unread1;
        this.unread2 = unread2;

    }

    public String getRoomID() {
        return roomID;
    }

    public String getOwner1() {
        return owner1;
    }

    public int getUnread1() {
        return unread1;
    }

    public void setUnread1(int unread1) {
        this.unread1 = unread1;
    }

    public int getUnread2() {
        return unread2;
    }

    public void setUnread2(int unread2) {
        this.unread2 = unread2;
    }

    public String getOwner2() {
        return owner2;
    }

    public String getDate() {
        return date;
    }
}
