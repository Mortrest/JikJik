package JikJik;

import JikJik.Models.*;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Scanner;

public class ModelLoader {
    private final File userDirectory;
    /**
     * DO NOT CHANGE ANYTHING IN CONSTRUCTOR.
     */
    public ModelLoader() {
        userDirectory = Config.getConfig("mainConfig").getProperty(File.class, "userDirectory");
        if (!userDirectory.exists()) userDirectory.mkdirs();
    }



    public void saveUser(LinkedList<User> users) {
        try {
            File file = getUserFile("Users"+".txt");
            assert file != null;
            PrintStream printStream = new PrintStream(file);
            Gson gson = new Gson();
            for (User us : users) {
                String jsonInString = gson.toJson(us);
                printStream.print(jsonInString);
                printStream.println();
            }

            printStream.close();
            log("User saved");
        } catch (FileNotFoundException e) {
            log("ERROR Occurred while saving user");
        }
    }

    public void saveRoom(LinkedList<Room> rooms) {
        try {
            File file = getUserFile("Rooms"+".txt");
            assert file != null;
            PrintStream printStream = new PrintStream(file);
            Gson gson = new Gson();
            for (Room us : rooms) {
                String jsonInString = gson.toJson(us);
                printStream.print(jsonInString);
                printStream.println();
            }
            printStream.close();
            log("Room saved");
        } catch (FileNotFoundException e) {
            log("ERROR Occurred while saving room");
        }
    }

    public void saveTweet(LinkedList<Tweet> tweets) {
        try {
            File file = getUserFile("Tweets"+".txt");
            assert file != null;
            PrintStream printStream = new PrintStream(file);
            Gson gson = new Gson();
            for (Tweet us : tweets) {
                String jsonInString = gson.toJson(us);
                printStream.print(jsonInString);
                printStream.println();
            }
            log("Tweet saved");

            printStream.close();
        } catch (FileNotFoundException e) {
            log("ERROR Occurred while saving tweet");
        }
    }
    static LinkedList<String> logs = new LinkedList<>();
    public void log(String msg) {
        try {

            File file = getUserFile("Log"+".txt");
            assert file != null;
            PrintStream printStream = new PrintStream(file);
            logs.add(LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+":"+LocalTime.now().getSecond()+" - "+msg);
            for (String str : logs){
                printStream.println(str);
            }
            printStream.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);

        }
    }

    public void saveChat(LinkedList<Chat> chats) {
        try {
            File file = getUserFile("Chats"+".txt");
            assert file != null;
            PrintStream printStream = new PrintStream(file);
            Gson gson = new Gson();
            for (Chat us : chats) {
                String jsonInString = gson.toJson(us);
                printStream.print(jsonInString);
                printStream.println();
            }
            printStream.close();
            log("Chat saved");

        } catch (FileNotFoundException e) {
            log("ERROR Occurred while saving chats");
        }
    }

    public void saveNotif(LinkedList<Notif> notifs) {
        try {
            File file = getUserFile("Notifs"+".txt");
            assert file != null;
            PrintStream printStream = new PrintStream(file);
            Gson gson = new Gson();
            for (Notif us : notifs) {
                String jsonInString = gson.toJson(us);
                printStream.print(jsonInString);
                printStream.println();
            }
            log("Notif saved");

            printStream.close();
        } catch (FileNotFoundException e) {
            log("ERROR Occurred while saving notifs");
        }
    }

    public LinkedList<Tweet> loadTweets() {
        try {
            File userFile = getUserFile("Tweets.txt");
            if (userFile == null){
                System.out.println("not found");
            } else {
                Gson gson = new Gson();
                Scanner ss = new Scanner(userFile);
                LinkedList<Tweet> tweets = new LinkedList<>();
                while (ss.hasNextLine()) {
                    Tweet tweet = gson.fromJson(ss.nextLine(), Tweet.class);
                    tweets.add(tweet);
                }
                log("Tweets Loaded");
                return tweets;
            }
        } catch (Exception e) {
            log("ERROR Occurred when loading tweets");
            System.out.println(e);
        }
        return null;
    }

    public LinkedList<User> loadUsers() {
        try {
            File userFile = getUserFile("Users.txt");
            if (userFile == null){
                System.out.println("not found");
            } else {
                Gson gson = new Gson();
                Scanner ss = new Scanner(userFile);
                LinkedList<User> users = new LinkedList<>();
                while (ss.hasNextLine()) {
                    User user = gson.fromJson(ss.nextLine(), User.class);
                    users.add(user);
                }
                log("Users Loaded");
                return users;
            }
        } catch (Exception e) {
            log("ERROR Occurred when loading users");
            System.out.println(e);
        }
        return null;
    }

    public LinkedList<Chat> loadChats() {
        try {
            File userFile = getUserFile("Chats.txt");
            if (userFile == null){
                System.out.println("not found");
            } else {
                Gson gson = new Gson();
                Scanner ss = new Scanner(userFile);
                LinkedList<Chat> chats = new LinkedList<>();
                while (ss.hasNextLine()) {
                    Chat chat = gson.fromJson(ss.nextLine(), Chat.class);
                    chats.add(chat);
                }
                log("Chats Loaded");
                return chats;
            }
        } catch (Exception e) {
            log("ERROR Occurred when loading chats");
            System.out.println(e);
        }
        return null;
    }

    public LinkedList<Room> loadRooms() {
        try {
            File userFile = getUserFile("Rooms.txt");
            if (userFile == null){
                System.out.println("not found");
            } else {
                Gson gson = new Gson();
                Scanner ss = new Scanner(userFile);
                LinkedList<Room> rooms = new LinkedList<>();
                while (ss.hasNextLine()) {
                    Room room = gson.fromJson(ss.nextLine(), Room.class);
                    rooms.add(room);
                }
                log("Rooms Loaded");
                return rooms;
            }
        } catch (Exception e) {
            System.out.println(e);
            log("ERROR Occurred when loading rooms");
        }
        return null;
    }

    public LinkedList<Notif> loadNotifs(){
        try {
            File userFile = getUserFile("Notifs.txt");
            if (userFile == null){
                System.out.println("not found");
            } else {
                Gson gson = new Gson();
                Scanner ss = new Scanner(userFile);
                LinkedList<Notif> notifs = new LinkedList<>();
                while (ss.hasNextLine()) {
                    Notif notif = gson.fromJson(ss.nextLine(), Notif.class);
                    notifs.add(notif);
                }
                log("Notifs Loaded");
                return notifs;
            }
        } catch (Exception e) {
            System.out.println(e);
            log("ERROR Occurred when loading notifs");

        }
        return null;
    }

    private File getUserFile(String name) {
        File directory = new File(userDirectory.getAbsolutePath());
        File[] d = directory.listFiles();
        if (d != null) {
            for (File dd : d) {
                if (dd.getName().equals(name)) {
                    return dd;
                }
            }
            return null;
        } else {
            return null;

        }
    }

}
