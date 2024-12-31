package Chapter10_Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDP_Server {
    public static void main(String[] args) {
        /*
        UDP 통신 시 데이터를 주고 받을 때에는 DatagramSocket 클래스를 사용하며,
        TCP와 달리 실제 데이터를 담을 때는 DatagramPacket 클래스를 사용한다.
        TCP는 Stream을 사용했다.

        생성자 - Socket과 유사
        DatagramSocket(int port)
        데이터를 받기 위해 대기할 때는 receive(DatagramPacket), 보낼때는 send(DatagramPacket) 메서드를 사용한다.

        DatagramPacket(byte[] buf, int length) - length만큼의 크기를 받기 위한 객체 생성
        DatagramPacket(byte[] buf, inf offset, int length, InetAddress, port)
        - offset이 할당되어 있고, 지정된 address와 port로 데이터를 전송하기 위한 객체 생성.
        .getData() - byte[]로 전송받은 데이터를 리턴한다. getLength() - 전송받은 데이터의 길이를 리턴한다.
         */
        startUDPServer();
        /*
        UDP는 TCP와 달리 데이터가 성공적으로 전달되지 않아도 예외를 발생시키지 않는다.
         */
    }
    public static void startUDPServer(){
        DatagramSocket server=null;
        try{
            server = new DatagramSocket(9999);
            int bufferLength = 256;
            byte[] buf = new byte[bufferLength];
            DatagramPacket packet = new DatagramPacket(buf,bufferLength); //데이터를 받는다.
            while(true){
                System.out.println("Waiting for Request");
                server.receive(packet); //데이터가 넘어오면 packet에 데이터를 담는다.
                int dataLength = packet.getLength();
                System.out.println("Data Length is "+dataLength);
                String data = new String(packet.getData(),0,dataLength);
                //String 객체 생성시 offset과 length를 이용할 수 있다.
                System.out.println("Received : "+data);
                if(data.equals("EXIT")){
                    System.out.println("STOP");
                    break;
                }
                System.out.println("------");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            if(server!=null) server.close();
        }
    }
}
