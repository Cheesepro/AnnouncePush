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

    public static void main(String[] args) throws Exception{
        int count = 0;
        Socket client = null ;	// 表示客 户端
        client = new Socket("localhost",2000) ;
        BufferedReader buf = null ;	// 一次性接收完成
        PrintStream out = null ;	// 发送数据
        BufferedReader input = null ;	// 接收键盘数据
        input = new BufferedReader(new InputStreamReader(System.in)) ;
        buf = new BufferedReader(new InputStreamReader(client.getInputStream())) ;
        out = new PrintStream(client.getOutputStream()) ;
        boolean flag = true ;		// 定义标志位
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
            out.println(str) ;
            if(info.get("exitcommand").equals(str)){
                flag = false ;
            }else{
                String echo = buf.readLine() ;
                Logger.write(info.get("outputprefix") + echo);
            }
        }
        buf.close() ;
        client.close() ;
    }
}
