package JikJik.Models;

import JikJik.ModelLoader;

import java.util.Date;
import java.util.LinkedList;

public class Notifs {
    LinkedList<Notif> notifs;
    ModelLoader ml;

    public Notifs(ModelLoader ml){
        this.ml = ml;
        this.notifs = ml.loadNotifs();
    }

    // Making notifs
    public void makeNotif(String text,String owner,String type){
        Date date = new Date();
        Notif notif = new Notif(owner,text,Long.toString(date.getTime()),false,type);
        notifs.add(notif);
        ml.log("Notification " + text +" Created");
        ml.saveNotif(notifs);
    }

    public void deleteNotif(Notif notif){
        notifs.remove(notif);
        ml.log("Notification " + notif.getText() +" Deleted");
        ml.saveNotif(notifs);
    }

    public void makeRequest(String text,String owner,String type,String owner2){
        Date date = new Date();
        Notif notif = new Notif(owner,text,Long.toString(date.getTime()),false,type,owner2);
        notifs.add(notif);
        ml.log(owner + " Requested to " + owner2);
        ml.saveNotif(notifs);
    }

    // Showing notifs
    public LinkedList<Notif> showNotif(String username){
        LinkedList<Notif> n = new LinkedList<>();
        for (Notif notif : notifs){
            if (notif.getOwner().equals(username)){
                n.add(notif);
            }
        }
        return n;
    }

    public LinkedList<Notif> showMyReqs(String username){
        LinkedList<Notif> n = new LinkedList<>();
        for (Notif notif : notifs){
            if (notif.getType().equals("2")) {
                if (notif.getOwner2().equals(username)) {
                    n.add(notif);
                }
            }
        }
        return n;
    }
}
