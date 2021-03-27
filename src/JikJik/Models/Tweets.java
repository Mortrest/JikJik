package JikJik.Models;

import JikJik.ModelLoader;

import java.util.Date;
import java.util.LinkedList;
import java.util.Random;


public class Tweets {
    public LinkedList<Tweet> getTweets() {
        return tweets;
    }

    LinkedList<Tweet> tweets;
    ModelLoader ml;

    public Tweets(ModelLoader modelLoader) {
        tweets = modelLoader.loadTweets();

        this.ml = modelLoader;
    }

    public LinkedList<Tweet> getComments(String tweetID,Users users) {
        LinkedList<Tweet> comments = new LinkedList<>();
        for (Tweet tw : tweets){
            if (tw.getParent().equals(tweetID) && users.searchUsername(tw.getOwner()).isActive()){
                comments.add(tw);
            }
        }
        return comments;
    }

    // Searching by ID
    public Tweet search(String ID) {
        for (Tweet tw : tweets) {
            if (tw.getID().equals(ID)) {
                return tw;
            }
        }
        return null;
    }

    // Making Tweets
    public void makeTweet(String text, String parent, String owner, LinkedList<String> followers) {
        Date date = new Date();
        LinkedList<String> str2 = new LinkedList<>();
        Random random = new Random();
        Tweet tweet = new Tweet(Integer.toString(random.nextInt(100000)), text,parent, followers, Long.toString(date.getTime()), str2, owner, false);
        tweets.add(tweet);
        ml.log("Tweet Created " + text);
        ml.saveTweet(tweets);
    }

    // ُShowing Tweets
    public LinkedList<Tweet> showTweetOwnPage(Users users, String username, int type) {
        LinkedList<Tweet> tw = new LinkedList<>();
        if (type == 3) {
            for (Tweet t : tweets) {
                if (users.searchUsername(t.owner).isActive) {
                    if (!users.searchUsername(username).getMuted().contains(t.owner) && t.getParent().equals("0")) {
                        if (users.searchUsername(t.owner).isPrivate()) {
                            if (users.searchUsername(username).getFollowing().contains(t.owner)) {
                                tw.add(t);
                            }
                        } else {
                            tw.add(t);
                        }
                    }
                }
            }
            tw = sortByLike(tw);
        }
        else {
            for (Tweet t : tweets) {
                if (users.searchUsername(t.owner).isActive && t.getParent().equals("0") && !users.searchUsername(username).getMuted().contains(t.owner)) {
                    if (t.owner.equals(username)) {
                        tw.add(t);
                    }
                    if (type == 2) {
                        if (t.getUsers() != null) {
                            for (String str : t.users) {
                                if (str.equals(username) || t.getParent().equals("0")) {
                                    if (!tw.contains(t)) {
                                        tw.add(t);
                                    }
                                }
                            }
                        }
                    }
                }
                if (type == 1 || type == 2) {
                    tw = sortByDate(tw);
                }
            }
        }
        return tw;

    }

    // Liking Tweets
    public void likeTweet(User user, Tweet tweet) {
        if (tweet.getLikes().contains(user.getUsername())) {
            tweet.getLikes().remove(user.getUsername());
            ml.log(user.getUsername() + " Liked Tweet ID " + tweet.getID());
        } else {
            tweet.getLikes().add(user.getUsername());
            ml.log(user.getUsername() + " Unliked Tweet ID " + tweet.getID());
        }
        ml.saveTweet(tweets);
    }

    // Retweeting
    public void reTweet(Tweet tweet, User user) {
        Date date = new Date();
        LinkedList<String> str2 = new LinkedList<>();
        Tweet tw = new Tweet(Integer.toString(tweets.size() + 1), tweet.getText(),tweet.getParent(), user.getFollowers(), Long.toString(date.getTime()), str2, user.getUsername(), true);
        tweets.add(tw);
        ml.log(user.getUsername() + " Retweeted Tweet ID " + tweet.getID());
        ml.saveTweet(tweets);
    }

    // Adding tweets to newly followed
    public void follow(String current, String target) {
        for (Tweet tw : tweets) {
            if (tw.owner.equals(target)) {
                tw.users.add(current);
            }
        }
        ml.log(current + " Followed " + target);
        ml.saveTweet(tweets);
    }

    // Removing dependents with unfollow
    public void unfollow(String current, String target) {
        for (Tweet tw : tweets) {
            if (tw.owner.equals(target)) {
                tw.users.remove(current);
            }
        }
        ml.log(current + " Unfollowed " + target);
        ml.saveTweet(tweets);
    }

    // Sorting by date
    public LinkedList<Tweet> sortByDate(LinkedList<Tweet> tweet) {
        for (int i = 0; i < tweet.size(); i++) {
            if (i != 0) {
                while (Long.parseLong(tweet.get(i).getDate()) < Long.parseLong(tweet.get(i - 1).getDate())) {
                    Tweet a = tweet.get(i - 1);
                    Tweet b = tweet.get(i);
                    tweet.remove(i - 1);
                    tweet.remove(i - 1);
                    tweet.add(i - 1, b);
                    tweet.add(i, a);
                    if (i != 1) {
                        i--;
                    } else {
                        break;
                    }
                }
            }
        }
        return tweet;
    }

    // Sorting by likes
    public LinkedList<Tweet> sortByLike(LinkedList<Tweet> tweet) {
        for (int i = 0; i < tweet.size(); i++) {
            if (i != 0) {
                while (tweet.get(i).getLikes().size() > (tweet.get(i - 1).getLikes().size())) {
                    Tweet a = tweet.get(i - 1);
                    Tweet b = tweet.get(i);
                    tweet.remove(i - 1);
                    tweet.remove(i - 1);
                    tweet.add(i - 1, b);
                    tweet.add(i, a);
                    if (i != 1) {
                        i--;
                    } else {
                        break;
                    }
                }
            }
        }
        return tweet;

    }

    public void deleteProfile(User user) {
        tweets.removeIf(tw -> tw.owner.equals(user.getUsername()));
        for(Tweet tw : tweets){
            tw.getLikes().removeIf(str -> str.equals(user.getUsername()));
        }
        ml.log(user.getUsername() + " Tweets Deleted!");
        ml.saveTweet(tweets);

    }

}
