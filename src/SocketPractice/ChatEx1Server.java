package SocketPractice;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatEx1Server {
    public static void main(String[] args) {
        //서버를 만들고,  키보드로부터 입력을 받아서
        //고객에게 메시지를 보내고, 다시 받고, 반복한다.
        ServerSocket server = null;
        Socket client = null;
        Scanner scanner = new Scanner(System.in);
        //입력을 키보드로 받으니까 Scanner 클래스가 필요하다.
        try{
            server = new ServerSocket(9999);
            System.out.println("Waiting Client");
            client = server.accept();
            System.out.println("Connected!");
            //고객의 메시지를 받기 위한 stream
            InputStream stream = client.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            //출력을 위한 스트림.
            OutputStream streamout = client.getOutputStream();
            //데이터를 서버에 전달하기 위해서 OutputStream 생성.
            PrintWriter out = new PrintWriter(streamout);
            //BufferedOutputStream buff = new BufferedOutputStream(streamout);
            while(true){
                //고객의 메시지를 먼저 읽는다.
                String input = in.readLine();
                if(input.equals("quit")||input.equals("exit")) break;
                System.out.print("Send: ");
                //내 메시지를 그 후에 보낸다.
                String chat = scanner.nextLine();
                //BufferedOutputStream으로 하니까 안되고, PrintWriter로 하니까 된다...
                //왜 그렇지...?
                out.println(chat);
                out.flush();
                //quit이나 exit를 입력하면 채팅이 종료된다.
                if(chat.equals("quit")||chat.equals("exit")) break;
            }
            //고객이 먼저 입력한다.
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally{
            try {
                scanner.close();
                if (client != null) {
                    client.close();
                }
                if (server != null) {
                    server.close(); //무조건 실행되게 finally로 감싸기.
                    System.out.println("Server closed");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
