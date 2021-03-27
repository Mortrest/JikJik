package JikJik.Models;

import JikJik.ModelLoader;

import java.util.Date;
import java.util.LinkedList;
import java.util.Random;


public class Chats{
    LinkedList<Room> rooms;
    LinkedList<Chat> chats;
    ModelLoader ml;
    public Chats(ModelLoader modelLoader){
        this.ml = modelLoader;
        rooms = modelLoader.loadRooms();
        chats = modelLoader.loadChats();
    }

    public void createSavedMsg(String username){
        Date date = new Date();
        Room room = new Room(Integer.toString(rooms.size()+1),Long.toString(date.getTime()),username,username,0,0);
        rooms.add(room);
        System.out.println(rooms.get(0).getOwner1());
        ml.saveRoom(rooms);
    }

    public LinkedList<Room> userRoom(String username){
        LinkedList<Room> userRooms = new LinkedList<>();
        for (Room room : rooms){
            if (room.getOwner1().equals(username) || room.getOwner2().equals(username)){
                userRooms.add(room);
            }
        }
        return userRooms;
    }



    public LinkedList<Chat> getChats() {
        return chats;
    }

    // Searching rooms
    public Room searchRoom(String o1,String o2){
        for (Room room : rooms){
            if (room.getOwner1().equals(o1) && room.getOwner2().equals(o2)){
                return room;
            } else if (room.getOwner2().equals(o1) && room.getOwner1().equals(o2)){
                return room;
            }
        }
        return null;
    }

    public Room searchRoomID(String roomID){
        for (Room room : rooms){
            if (room.getRoomID().equals(roomID)){
                return room;
            }
        }
        return null;
    }

    // Making Comments
    public void makeChat(String text,String owner,String roomID){
        Date date = new Date();
        Chat chat = new Chat(roomID,text,Long.toString(date.getTime()),owner);
        chats.add(chat);
        try {
            Room room = searchRoomID(roomID);
            room.setUnread1(room.getUnread1()+1);
            room.setUnread2(room.getUnread2()+1);
        } catch (Exception e){
            ml.log("Cannot makeChat (makeChat method in Chats)");
        }
        ml.log("Tweet Created " + text);
        ml.saveChat(chats);
    }

    public void seen(String currentUser,String RoomID){
        Room room = searchRoomID(RoomID);
        if (room.getOwner1().equals(currentUser)){
            room.setUnread1(0);
        } else if (room.getOwner2().equals(currentUser)) {
            room.setUnread2(0);
        }
        ml.saveRoom(rooms);
    }

    // Making rooms
    public String makeRoom(String o1,String o2){
        Date date = new Date();
        Random random = new Random();
        Room room = new Room(Integer.toString(random.nextInt(100000)),Long.toString(date.getTime()),o1,o2,0,0);
        rooms.add(room);
        ml.saveRoom(rooms);
        ml.log("Room created " + room.getRoomID());
        return room.getRoomID();
    }

    // ُShowing Comments
    public LinkedList<Chat> showChats(String roomID){
        LinkedList<Chat> tw = new LinkedList<>();
        for (Chat t : chats){
            if (t.getRoomID().equals(roomID)){
                tw.add(t);
            }
        }
        tw = sortByDate(tw);
        return tw;
    }

    // Sorting by date
    public LinkedList<Chat> sortByDate(LinkedList<Chat> chat){
        for (int i = 0; i < chat.size(); i++) {
            if (i != 0) {
                while (Long.parseLong(chat.get(i).getDate()) > Long.parseLong(chat.get(i - 1).getDate())) {
                    Chat a = chat.get(i - 1);
                    Chat b = chat.get(i);
                    chat.remove(i - 1);
                    chat.remove(i - 1);
                    chat.add(i - 1, b);
                    chat.add(i, a);
                    if (i != 1) {
                        i--;
                    } else {
                        break;
                    }
                }
            }
        }
        return chat;
    }

}
