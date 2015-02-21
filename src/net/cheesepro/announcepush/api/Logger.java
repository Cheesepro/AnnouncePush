package net.cheesepro.announcepush.api;

/**
 * Created by Mark on 2015-02-20.
 */
public class Logger {

    public static void write(String msg){
        System.out.println("[AnnouncePushServer] " + msg);
    }

    public static void writenospace(String msg){
        System.out.print("[AnnouncePushServer] " + msg);
    }

}
