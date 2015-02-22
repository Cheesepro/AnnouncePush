package net.cheesepro.announcepush;

import net.cheesepro.announcepush.api.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {

    static Map<String, String> info = new HashMap<String, String>();

    public static void main(String[] args){
        try{
            int count = 0;
            Socket client = null ;
            client = new Socket("localhost",2000) ;
            BufferedReader buf = null ;
            PrintStream out = null ;
            BufferedReader input = null ;
            input = new BufferedReader(new InputStreamReader(System.in)) ;
            buf = new BufferedReader(new InputStreamReader(client.getInputStream())) ;
            out = new PrintStream(client.getOutputStream()) ;
            boolean flag = true ;
            while(flag){
                if(count==0){
                    int infocount = Integer.parseInt(buf.readLine());
                    for(int i = 0; i<infocount; i++){
                        String[] get = buf.readLine().split("#");
                        info.put(get[0], get[1]);
                    }
                    if(Boolean.parseBoolean(info.get("requirelogin"))){
                        Logger.write("LOGIN");
                        Logger.writenospace(info.get("usernameprefix"));
                        String username = input.readLine();
                        Logger.writenospace(info.get("passwordprefix"));
                        String passwd = input.readLine();
                        out.println(username + " " + passwd);
                        String echo = buf.readLine() ;
                        Logger.write(info.get("inputprefix") + echo);
                    }
                    count++;
                }
                Logger.writenospace(info.get("inputprefix"));
                String str = input.readLine() ;
                out.println("%"+str) ;
                if(info.get("exitcommand").equals(str)){
                    flag = false ;
                }else{
                    String echo = buf.readLine() ;
                    if(echo!=null){
                        Logger.write(info.get("serverprefix") + echo);
                    }else{
                        Logger.write("###==[Connection Lost]==###");
                        flag = false;
                    }
                }
            }
            buf.close() ;
            client.close() ;
        }catch (Exception e){
            Logger.write("Unexpected error!");
            System.exit(0);
        }
    }
}
