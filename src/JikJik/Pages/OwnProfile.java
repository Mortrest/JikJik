package JikJik.Pages;

import JikJik.Models.Notif;
import JikJik.Models.User;
import JikJik.Models.Users;

import java.util.LinkedList;
import java.util.Scanner;

public class OwnProfile extends Page {
    Users users;
    User currentUser;

    public OwnProfile(Users users, User currentUser) {
        super();
        this.users = users;
        this.currentUser = currentUser;
    }

    public void template() {
        System.out.println("\t\t\t\t" + currentUser.getUsername());
        System.out.println();
        System.out.println("[1] Make new tweet ");
        System.out.println("[2] Your tweets ");
        System.out.println("[3] Edit your page");
        System.out.println("[4] Lists ");
        System.out.println("[5] Info ");
        System.out.println("[6] Notifications ");
        System.out.println("[7] Requests");
    }

    public void newTweet() {
        System.out.println("\nWhat's your tweet?");
        Scanner ss = new Scanner(System.in);
        String text = ss.nextLine();
        users.makeTweet(currentUser, text);
        System.out.println("You Tweeted!");
        show();

    }

    public void eachList(int type) {
        LinkedList<String> tw;
        if (type == 1) {
            System.out.println("\t\t\tFollowing List\n");
            tw = currentUser.getFollowing();
        } else if (type == 2) {
            System.out.println("\t\t\tFollowers List\n");
            tw = currentUser.getFollowers();
        } else {
            System.out.println("\t\t\tBlack List\n");
            tw = currentUser.getBlackList();
        }
        if (tw.isEmpty()) {
            System.out.println("List is empty!");
        } else {
            int i = 0;
            for (String flw : tw) {
                if (users.searchUsername(flw).isActive()) {
                    System.out.println("\n" + "[" + (i + 1) + "] " + flw);
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
                    OtherProfile otherProfile = new OtherProfile(users, currentUser, users.searchUsername(tw.get(n - 1)));
                    otherProfile.show();
                    a = false;
                    break;
                }
            } catch (Exception e) {
            }

            a = switch (str) {
                case "back" -> false;
                default -> a;
            };
            if (a) {
                System.out.println("Invalid command!");
            }
        }
        showLists();
    }

    public LinkedList<String> usersForCatg(LinkedList<String> catg){
        int i = 0;
        LinkedList<String> f = new LinkedList<>();
        for (User user : users.getUsers()) {
            if (!user.getUsername().equals(currentUser.getUsername())){
            if (!catg.contains(user.getUsername())) {
                if (user.isPrivate() && user.isActive()) {
                    if (currentUser.getFollowing().contains(user.getUsername())) {
                        f.add(user.getUsername());
                        System.out.println("[" + i + "] " + user.getUsername());
                        i++;
                    }
                } else {
                    if (user.isActive()) {
                        System.out.println("[" + i + "] " + user.getUsername());
                        f.add(user.getUsername());
                        i++;
                    }
                }
            }
            }
        }
        return f;
    }

    public void catgPage(LinkedList<String> catg){
        System.out.println("\t\t\t" + catg.get(0) + "\n");
        for (int i = 1; i < catg.size(); i++) {
            if (users.searchUsername(catg.get(i)).isActive()) {
                System.out.println("[" + (i) + "] " + catg.get(i));
            }
        }
        System.out.println("[A] For Adding people");
        System.out.println("[B] For Removing people");
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        boolean c = true;
        while (a) {
            String cmd = ss.next();
            try {
                int n = Integer.parseInt(cmd);
                if (n <= catg.size() && n >= 0) {
                    a = false;
                    OtherProfile otherProfile = new OtherProfile(users,currentUser,users.searchUsername(catg.get(n)));
                    otherProfile.show();
                    break;
                }
            } catch (Exception e) {
            }

            switch (cmd) {
                case "back":
                    a = false;
                    c = false;
                    break;
                case "A":
                    boolean b = true;
                    LinkedList<String> usersList = usersForCatg(catg);
                    c = false;
                    while (b) {
                        String name = ss.nextLine();
                        try {
                            int n = Integer.parseInt(name);
                            if (n >= 0 && n < usersList.size()) {
                                catg.add(usersList.get(n));
                                System.out.println("Added!");
                                b = false;
                            }
                        } catch (Exception e) {
                        }
                        if (name.equals("back")){
                            b = false;
                        }
                    }
                    break;
                case "B":
                    c = false;
                    System.out.println("Which user?");
                    for (int i = 1; i < catg.size(); i++) {
                        System.out.println("[" + (i) + "] " + catg.get(i));
                    }
                    b = true;
                    while (b) {
                        String name = ss.nextLine();
                        try {
                            int n = Integer.parseInt(name);
                            System.out.println(catg);
                            if (n >= 1 && n < catg.size()) {
                                catg.remove(n);
                                System.out.println(catg);
                                b = false;
                            }
                        } catch (Exception e) {
                        }
                        if (name.equals("back")){
                            b = false;
                        }
                    }
                    break;

            }
            if (a && c) {
                System.out.println("Invalid Command!");
            }
            c = true;
        }
        users.saveCatg(currentUser,catg);

    }

    public void showLists() {
        System.out.println("\t\t\tLists");
        System.out.println("[1] Following List");
        System.out.println("[2] Followers List");
        System.out.println("[3] Black List");
        LinkedList<LinkedList<String>> catg = currentUser.getCategories();
        boolean f = true;
        if (catg!= null) {
            for (int i = 0; i < catg.size(); i++) {
                System.out.println("[" + (i + 4) + "] " + catg.get(i).get(0));
            }
            f = false;
        }
        System.out.println("\n[A] For Creating New Category");
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        boolean b = true;
        while (a) {
            String cmd = ss.next();
            if (!f) {
                try {
                    int n = Integer.parseInt(cmd);
                    if (n <= catg.size()+4 && n >= 4) {
                        catgPage(catg.get(n-4));
                        a = false;
                    }
                } catch (Exception e) {
                }
            }
            switch (cmd) {
                case "back" -> {
                    a = false;
                    b = false;
                }
                case "1" -> {
                    eachList(1);
                    b = false;
                }
                case "2" -> {
                    eachList(2);
                    b = false;
                }
                case "3" -> {
                    eachList(3);
                    b = false;
                }
                case "A" -> {
                    String name;
                    while (true) {
                        name = ss.nextLine();
                        System.out.println("Enter the Category name: ");
                        if (!name.equals("")) {
                            break;
                        }
                    }
                    f = true;
                    users.createCatg(currentUser, name);
                    catgPage(currentUser.getCategories().getLast());
                    b = false;
                }
            }
            if (a && b) {
                System.out.println("Invalid Command!");
            }
            b = true;
        }
        show();
    }

    public void showNotif() {
        System.out.println("\t\t\t\tNotifications\n");
        LinkedList<Notif> notifs;
        try {
            notifs = users.getNotifs().showNotif(currentUser.getUsername());
        } catch (Exception e) {
            notifs = null;
        }
        if (notifs == null) {
            System.out.println("No notifications!");
        } else {
            boolean f = false;
            for (Notif nf : notifs) {
                if (nf.getType().equals("1")) {
                    f = true;
                    System.out.println(nf.getText());
                }
            }
            if (!f) {
                System.out.println("No notifications!");
            }
        }
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        while (a) {
            String cmd = ss.next();
            if ("back".equals(cmd)) {
                a = false;
                show();
            }
            if (a) {
                System.out.println("Invalid Command!");
                showNotif();
            }
        }
    }

    public void reqPage(Notif notif) {
        System.out.println(notif.getText() + "\n");
        System.out.println("[1] For accept");
        System.out.println("[2] For decline");
        System.out.println("[3] For silent decline");
        boolean a = true;
        Scanner ss = new Scanner(System.in);
        while (a) {
            String str = ss.next();
            switch (str) {
                case "back":
                    a = false;
                    showReq();
                    break;
                case "1":
                    a = false;
                    users.followProfile(users.searchUsername(notif.getOwner2()), notif.getOwner());
                    String text = notif.getOwner() + " has accepted your following request!";
                    users.getNotifs().makeNotif(text, notif.getOwner2(), "1");
                    users.getNotifs().deleteNotif(notif);
                    break;
                case "2":
                    a = false;
                    text = notif.getOwner() + " has declined your following request!";
                    users.getNotifs().makeNotif(text, notif.getOwner2(), "1");
                    users.getNotifs().deleteNotif(notif);
                    break;
                case "3":
                    a = false;
                    users.getNotifs().deleteNotif(notif);
                    break;

            }
            if (a) {
                System.out.println("Invalid Command!");
                reqPage(notif);
            }

        }
    }

    public void yourReqs() {
        System.out.println("\t\t\t\tYour requests\n");
        LinkedList<Notif> notifs;
        try {
            notifs = users.getNotifs().showMyReqs(currentUser.getUsername());
        } catch (Exception e) {
            notifs = null;
        }
        if (notifs == null) {
            System.out.println("No Requests!");
        } else {
            int i = 0;
            for (Notif nf : notifs) {
                String text = nf.getText();

                System.out.println((i + 1) + "- " + text.substring(0,text.length()-4) + nf.getOwner());
            }
        }
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        while (a) {
            String str = ss.next();
            if ("back".equals(str)) {
                a = false;
                showReq();
            }
            if (a) {
                System.out.println("Invalid Command!");
                showReq();
            }
        }
    }

    public void showReq() {
        System.out.println("\t\t\t\tRequests\n");
        LinkedList<Notif> notifs = null;
        LinkedList<Notif> reqs = new LinkedList<>();
        try {
            notifs = users.getNotifs().showNotif(currentUser.getUsername());
        } catch (Exception e) {
            notifs = null;
        }
        if (notifs == null) {
            System.out.println("No Requests!");
        } else {
            boolean f = false;
            int i = 0;
            for (Notif nf : notifs) {
                if (nf.getType().equals("2")) {
                    f = true;
                    reqs.add(nf);
                    System.out.println("[" + (i + 1) + "] " + nf.getText());
                }
            }
            if (!f) {
                System.out.println("No Requests");
            }
        }
        System.out.println("\n[A] View your pending Requests");
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        while (a) {
            String str = ss.next();
            try {
                int n = Integer.parseInt(str);
                if (n <= reqs.size() && n >= 1) {
                    reqPage(reqs.get(n - 1));
                    break;
                }
            } catch (Exception e) {
            }

            switch (str) {
                case "back" -> {
                    a = false;
                    show();
                }
                case "A" -> {
                    a = false;
                    yourReqs();
                }
            }
            if (a) {
                System.out.println("Invalid Command!");
                showReq();
            }
        }
    }

    public String editing() {
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        while (a) {
            String str = ss.next();
            if (!str.equals("")){
                return str;
            }
        }
        return null;
    }
    public void editInfo() {
        System.out.println("\t\t\tInfo\n");
        System.out.println("[1] Phone Number: " + currentUser.getPhoneNumber());
        System.out.println("[2] Email: " + currentUser.getEmail());
        System.out.println("[3] Birth date: " + currentUser.getBirthdate());
        System.out.println("[4] Bio: " + currentUser.getInfo());
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        while (a) {
            String str = ss.next();
            switch (str) {
                case "back" -> {
                    a = false;
                    users.save();
                }
                case "1" -> {
                    System.out.println("Enter new Phone Number: ");
                    currentUser.setPhoneNumber(editing());
                }
                case "2" -> {
                    System.out.println("Enter new Email: ");
                    currentUser.setEmail(editing());
                }
                case "3" -> {
                    System.out.println("Enter new Birth Date: ");
                    currentUser.setEmail(editing());
                }
                case "4" -> {
                    System.out.println("Enter new Bio: ");
                    currentUser.setInfo(editing());
                }
            }
            if (a) {
                editInfo();
            }

        }
    }

    @Override
    public void show() {
        super.show();
        users.log("Own profile page");
        Scanner ss = new Scanner(System.in);
        boolean a = true;
        while (a) {
            template();
            String cmd = ss.next();
            switch (cmd) {
                case "back" -> a = false;
                case "1" -> {
                    a = false;
                    newTweet();
                }
                case "2" -> {
                    ShowTweets showTweets = new ShowTweets(users, currentUser);
                    showTweets.showTweets(1);
                    a = false;
                }
                case "4" -> {
                    showLists();
                    a = false;
                }
                case "5" -> {
                    editInfo();
                    a = false;
                }
                case "6" -> {
                    showNotif();
                    a = false;
                }
                case "7" -> {
                    showReq();
                    a = false;
                }
            }
            if (a) {
                System.out.println("Invalid Command!");
            }
        }
        MainMenu mainMenu = new MainMenu(users, currentUser);
        mainMenu.show();
    }
}

