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
        Socket client = null ;	// ��ʾ�� ����
        client = new Socket("localhost",2000) ;
        BufferedReader buf = null ;	// һ���Խ������
        PrintStream out = null ;	// ��������
        BufferedReader input = null ;	// ���ռ�������
        input = new BufferedReader(new InputStreamReader(System.in)) ;
        buf = new BufferedReader(new InputStreamReader(client.getInputStream())) ;
        out = new PrintStream(client.getOutputStream()) ;
        boolean flag = true ;		// �����־λ
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
                    String echo = buf.readLine() ;	// ���շ��ؽ��
                    Logger.write(outputprefix + echo);
                }
                count++;
            }
            Logger.writenospace(inputprefix);
            String str = input.readLine() ;	// ���ռ��̵�������Ϣ
            out.println(str) ;
            if(exitcmd.equals(str)){
                flag = false ;
            }else{
                String echo = buf.readLine() ;	// ���շ��ؽ��
                Logger.write(outputprefix + echo);	// �����Ӧ��Ϣ
            }
        }
        buf.close() ;
        client.close() ;
    }
}
