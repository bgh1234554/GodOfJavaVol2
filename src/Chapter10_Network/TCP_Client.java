package Chapter10_Network;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class TCP_Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        sendSocketExample();
        //sendAndReceiveSocketData();
    }
    public static void sendSocketExample() throws IOException, InterruptedException {
        //데이터를 전송하는 클라이언트의 코드.
        for(int i=0;i<3;i++){
            sendSocketData("I liked Java at" + new Date());
        }
        sendSocketData("EXIT");
    }

    public static void sendSocketData(String s) throws IOException, InterruptedException {
        Socket socket = null;
        System.out.println("Client:Connecting");
        socket = new Socket("127.0.0.1",9999);
        //IP -> 같은 장비라는 것을 의미.
        System.out.println("Connect Status : "+socket.isConnected());
        Thread.sleep(1000);
        OutputStream stream = socket.getOutputStream();
        //데이터를 서버에 전달하기 위해서 OutputStream 생성.
        BufferedOutputStream out = new BufferedOutputStream(stream);
        byte[] bytes = s.getBytes();
        out.write(bytes);
        out.flush(); //쓰는 거니까 flush 해주면 좋음.
        System.out.println("Sent Data");
        out.close();
        socket.close();
    }
    public static void sendAndReceiveSocketData() throws IOException, InterruptedException {
        Socket socket = null;
        System.out.println("Client:Connecting");
        socket = new Socket("127.0.0.1",9999);
        //IP -> 같은 장비라는 것을 의미.
        System.out.println("Connect Status : "+socket.isConnected());
        Thread.sleep(1000);
        InputStream stream = socket.getInputStream();
        //데이터를 서버에 전달하기 위해서 OutputStream 생성.
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String data=null;
        StringBuilder receivedData=new StringBuilder();
        while((data=in.readLine())!=null){
            receivedData.append(data);
        }
        System.out.println(receivedData);
        in.close();
        socket.close();
    }
}