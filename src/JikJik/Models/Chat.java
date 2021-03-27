package JikJik.Models;

public class Chat {
    private final String roomID;
    private final String text;
    private final String date;
    private final String owner;

    public Chat(String roomID, String text, String date, String owner) {
        this.roomID = roomID;
        this.text = text;
        this.date = date;
        this.owner = owner;
    }

    public String getText() {
        return text;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getOwner() {
        return owner;
    }


    public String getDate() {
        return date;
    }
}
