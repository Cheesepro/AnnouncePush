package net.cheesepro.announcepush;

import net.cheesepro.announcepush.api.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Main {

    static String inputprefix = "";
    static String outputprefix = "";
    static String exitcmd = "";
    static boolean passwd = false;
    static String usernameprefix = "";
    static String passwordprefix = "";

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
                inputprefix = buf.readLine();
                outputprefix = buf.readLine();
                exitcmd = buf.readLine();
                passwd = Boolean.valueOf(buf.readLine());
                if(passwd){
                    usernameprefix = buf.readLine();
                    passwordprefix = buf.readLine();
                    Logger.writenospace(usernameprefix);
                    String username = input.readLine();
                    Logger.writenospace(passwordprefix);
                    String passwd = input.readLine();
                    out.println(username + " " + passwd);
                    String echo = buf.readLine() ;	// 接收返回结果
                    Logger.write(outputprefix + echo);
                }
                count++;
            }
            Logger.writenospace(inputprefix);
            String str = input.readLine() ;	// 接收键盘的输入信息
            out.println(str) ;
            if(exitcmd.equals(str)){
                flag = false ;
            }else{
                String echo = buf.readLine() ;	// 接收返回结果
                Logger.write(outputprefix + echo);	// 输出回应信息
            }
        }
        buf.close() ;
        client.close() ;
    }
}
