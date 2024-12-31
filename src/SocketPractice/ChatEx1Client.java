package SocketPractice;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatEx1Client {
    public static void main(String[] args) {
        Socket server = null;
        Scanner scanner = new Scanner(System.in);
        //서버의 메시지를 받기 위한 stream
        try {
            System.out.println("Client:Connecting");
            server = new Socket("127.0.0.1", 9999);
            //IP -> 같은 장비라는 것을 의미.
            System.out.println("Connect Status : " + server.isConnected());
            Thread.sleep(1000);
            InputStream stream = server.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            //출력을 위한 스트림.
            OutputStream streamout = server.getOutputStream();
            //데이터를 서버에 전달하기 위해서 OutputStream 생성.
            //BufferedOutputStream으로 하니까 안되고, PrintWriter로 하니까 된다...
            //왜 그렇지...?
            PrintWriter out = new PrintWriter(streamout);
            //BufferedOutputStream buff = new BufferedOutputStream(streamout);
            while(true){
                //먼저 채팅을 보낸다.
                System.out.print("Send: ");
                String chat = scanner.nextLine();
                out.println(chat);
                out.flush();
                if(chat.equals("quit")||chat.equals("exit")) break;
                //서버로부터 대답을 받는다.
                String response = in.readLine();
                System.out.println("From Server: "+response);
                if(chat.equals("quit")||chat.equals("exit")) break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally{
            scanner.close();
            try {
                if(server!=null) server.close(); System.out.println("Disconnected");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
