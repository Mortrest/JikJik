package JikJik;

import JikJik.Models.*;
import JikJik.Pages.SignIn;
import JikJik.Pages.SignUp;
import JikJik.Pages.Welcome;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        ModelLoader m = new ModelLoader();
        Tweets tweets = new Tweets(m);
        Notifs notifs = new Notifs(m);
        Chats chats = new Chats(m);
        Users users = new Users(m,tweets,chats,notifs);
        Welcome welcome = new Welcome(users);
        System.out.println("\n\n\n\n\n\n");
        welcome.show();

    }
}
