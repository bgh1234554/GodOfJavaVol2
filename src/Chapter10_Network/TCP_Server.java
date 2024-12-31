package Chapter10_Network;

import java.io.*;
import java.net.*;

public class TCP_Server {
    public static void main(String[] args) throws IOException {
        /*
        TCP 통신을 자바에서 쓰는 방법 - Socket 클래스 사용.
        데이터를 보내는 쪽에서 Socket 클래스를 생성한다. 원격에 있는 장비의 연결 상태를 보관하고 있음.
        서버에서 데이터를 받을때는 ServerSocket클래스의 메서드를 사용해 Socket을 생성한다.

        ServerSocket(int port, int backlog, InetAddress bindAddr)
        지정된 포트와 backlog를 사용하는 소켓을 생성한다.
        backlog - 객체가 바빠서 연결 요청을 처리하지 못하고 대기시킬때, 최대로 대기시킬 수 있는 작업의 개수
        기본값은 50.
        bindAddr - 특정 주소에서만 접근이 가능할 때 사용
        accept() - 소켓 연결을 대기시키는 메서드. 연결시 Socket 객체를 리턴한다.
        close() - 소켓 연결을 종료시킴. 처리하지 않으면 다른 프로그램이 사용할 수 없다.

        데이터를 보내는 클라이언트에서는 Socket 객체를 직접 생성해야 한다.
        Socket(String host, int port) - 제일 많이 쓰는 Socket 생성자
         */
        startServer();
        //startReplyServer();
        //넘어온 데이터를 출력하고, EXIT가 들어오면 프로그램을 종료시킨다.
        //Server을 먼저 실행시키고, 다른 파일의 Chapter10_1.java를 커버리지로 실행시킬 수 있다.
        //그러면 동시에 두 프로그램이 실행되면서 문자열을 주고 받는다.
    }
    public static void startServer() throws IOException {
        ServerSocket server = new ServerSocket(9999);
        //다른 프로그램에서 이 프로그램에서 띄운 서버를 접속할 때, 9999번 포트 사용 가능.
        Socket client = null;
        while(true){
            System.out.println("Waiting for request");
            client = server.accept(); //client에 Socket 객체를 연결시킨다.
            System.out.println("Server:Accepted");
            InputStream stream = client.getInputStream();
            //데이터를 받기 위해 getInputStream을 호출. 상대에게 Data를 보낼때는 OutputStream을 사용함.
            //Recap의 ConsoleEx.java 참조.
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            String data=null;
            StringBuilder receivedData=new StringBuilder();
            while((data=in.readLine())!=null){
                receivedData.append(data);
            }
            System.out.println(receivedData);
            in.close(); stream.close(); client.close();
            if(receivedData!=null&& "EXIT".equals(receivedData.toString())){
                System.out.println("Stop Socket Server");
                break;
            }
            System.out.println("--------");
        }
        server.close();
    }
    public static void startReplyServer(){
        ServerSocket server = null;
        //다른 프로그램에서 이 프로그램에서 띄운 서버를 접속할 때, 9999번 포트 사용 가능.
        Socket client = null;
        try {
            server = new ServerSocket(9999);
            while(true){
                System.out.println("Waiting for request");
                client = server.accept(); //client에 Socket 객체를 연결시킨다.
                System.out.println("Server:Accepted");
                OutputStream stream = client.getOutputStream();
                //데이터를 받기 위해 getInputStream을 호출. 상대에게 Data를 보낼때는 OutputStream을 사용함.
                //Recap의 ConsoleEx.java 참조.
                BufferedOutputStream out = new BufferedOutputStream(stream);
                byte[] bytes = "OK".getBytes();
                out.write(bytes);
                out.close();
                stream.close();
                client.close();
                System.out.println("--------");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally{
            try {
                server.close(); //무조건 실행되게 finally로 감싸기.
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
