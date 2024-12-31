package Chapter10_Network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_Client {
    public static void main(String[] args) {
        sendDataExample();
    }
    public static void sendDataExample(){
        for(int i=0;i<3;i++){
            sendUDPData("I like UDP");
        }
        sendUDPData("EXIT");
    }
    public static void sendUDPData(String data){
        try{
            DatagramSocket client = new DatagramSocket();
            InetAddress address = InetAddress.getByName("127.0.0.1");
            //InetAddress 클래스로 IP를 설정할 수 있다.
            byte[] buffer = data.getBytes(); //문자열을 byte 데이터로 바꾼다.
            DatagramPacket packet = new DatagramPacket(buffer,0,buffer.length,address,9999);
            client.send(packet);
            System.out.println("Sent Data");
            client.close();
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
