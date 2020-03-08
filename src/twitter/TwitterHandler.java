package twitter;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;

/**
 * Gets the tweets of the users following, displaying them in a non-editable text area
 *
 * @since 3/5/2018
 */
public class TwitterHandler {
    private static TwitterHandler instance = null;

    private Status status;
    private Twitter twitter;
    private TwitterFactory tf;

    private final String EMPTYTWEETERROR_STR= "Error: No image or text.";
    private static final String SUCCESS_TEXT = "Tweet Successful!";

    public static TwitterHandler getInstance() {
        if(instance == null) {
            instance = new TwitterHandler();
        }
        return instance;
    }

    private TwitterHandler() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("nsZh46T3yet4mhjYf6ZUCHDGn")
                .setOAuthConsumerSecret("KAiqgOkY3iD4ADz1HYFfaFgRRhDgC5cNwoeZlJML1SJWnJYjoD")
                .setOAuthAccessToken("970740689213337600-iw9HyVKXbrL9bdbbJrdAuv3UqbZ0ogM")
                .setOAuthAccessTokenSecret("c8H7WeSgA1rofNQZHs7sfih9EKGGTl1fQxXUkzQGOolYU");
        tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    /** Posts passed string to twitter
     *
     * @param tweet Message to post on twitter
     * @return  True if tweet was successful
     */
    public String createTweet(String tweet) {
        try {
            status = twitter.updateStatus(tweet);
            return SUCCESS_TEXT;
        } catch (TwitterException e) {
            System.out.println("tweet failed: " + e.getErrorMessage() + "\nAnd \n" + e.toString());
            if (e.getErrorMessage().equals("null"))
                return "Tweet failed. Couldn't connect twitter.";
            if(e.getErrorMessage().equals("Missing required parameter: status."))
                return EMPTYTWEETERROR_STR;
            return "Tweet failed. " + e.getErrorMessage();
        }
    }


    /** Posts passed string to twitter
     *
     * @param tweet Message to post on twitter, optional
     * @param image The image to upload
     * @return  True if tweet was successful
     */

    public String createTweet(String tweet, File image) {
        try {
            //if(tweet.equals(""))
            //    tweet = " ";
            StatusUpdate statusUpdate = new StatusUpdate(tweet); //create a new update to send with passed text
            statusUpdate.setMedia(image); // set the image to be uploaded
            status = twitter.updateStatus(statusUpdate); //send tweet and get the status off what was passed

            return SUCCESS_TEXT;
        } catch (TwitterException e) {
            System.out.println("tweet failed: " + e.getErrorMessage() + "\nAnd \n" + e.toString());
            if (e.getErrorMessage().equals("null"))
                return "Tweet failed. Couldn't connect twitter.";
            return "Tweet failed. " + e.getErrorMessage();
        }
    }

    public Status getStatus(){
        return status;
    }

    public static String getSuccessMessage(){
        return SUCCESS_TEXT;
    }

    public TwitterFactory getTwitterFactory(){ return tf; }
}
