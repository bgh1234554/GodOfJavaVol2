import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

class SerialDTO implements Serializable{
    private String Bookname;
    transient private int bookOrder;
    private boolean bestSeller;
    private long soldPerDay;

    public SerialDTO(String bookname, int bookOrder, boolean bestSeller, long soldPerDay) {
        this.bookOrder = bookOrder;
        Bookname = bookname;
        this.bestSeller = bestSeller;
        this.soldPerDay = soldPerDay;
    }

    @Override
    public String toString() {
        return "SerialDTO [" +
                "Bookname='" + Bookname + '\'' +
                ", bookOrder=" + bookOrder +
                ", bestSeller=" + bestSeller +
                ", soldPerDay=" + soldPerDay +
                ']';
    }
}

public class Chapter9 {
    public static void main(String[] args) throws IOException {
        /*
        Serializable 만들 때
        static final int serialVersionUID라는 값을 지정해주는 것을 권장함.
        각 서버가 쉽게 해당 객체가 같은지 다른지를 확인할 수 있도록 도와준다.
        UID가 같아도 클래스의 변수의 개수나 타입이 다르면 다른 클래스로 인식한다.
         */
        SerialDTO dto = new SerialDTO("GodOfJavaBook",1,true,100);
        FileOutputStream fos = new FileOutputStream("\\GodofJava\\text\\serial.obj");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(dto); //Object 말고 다른 메서드도 가능하다.
        oos.close(); fos.close();
        FileInputStream fis = new FileInputStream("\\GodofJava\\text\\serial.obj");
        ObjectInputStream ois = new ObjectInputStream(fis);
        SerialDTO dto2;
        try {
            dto2 = (SerialDTO)ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(dto2); //toString() 메서드를 구현해놔야 잘 찍힌다.
        /*
        이후 DTO의 내용이 바뀌면 class의 내용이 바뀌어서 출력이 안되는데,
        static final long serialVersionUID를 설정하면, 예외 없이 실행이 가능하다.
        다만, 권장하는 방법은 아니고, 내용이 바뀔때마다 UID가 바뀌는 것이 좋다.

        객체를 저장하거나 다른 객체로 보낼때 transient 키워드를 사용하면 Serializable의 대상에서 제외된다.
        보안상 중요한 변수나 저장해야할 필요가 없는 변수를 대상으로 사용한다.
         */

        /*
        JAVA의 NIO - JDK 1.4부터 추가된 것.
        스트림 대신에 Channel과 Buffer을 사용한다. NIO에서 데이터를 주고 받을 때에는 Buffer을 이용해서
        Channel을 처리한다.
         */
        //데이터가 담긴 buffer을 전달해 Channel을 통해 쓰기.
        String filename = "\\GodofJava\\text\\nio.txt";
        String data="My First NIO text file";
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        FileChannel channel = fileOutputStream.getChannel(); //Channel 객체 생성
        byte[] byteData=data.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(byteData); //ByteBuffer 객체 생성. wrap에 저장할 byte의 배열을 넘겨준다.
        channel.write(buffer); channel.close();
        //데이터를 담을 buffer를 알려줘서 Channel을 통해 읽어오기.

        FileChannel channel2 = new FileInputStream(filename).getChannel();
        ByteBuffer buffero = ByteBuffer.allocate(1024); //데이터를 저장할 공간의 크기.
        //IntBuffer, CharBuffer, DoubleBuffer 등이 존재한다.
        channel2.read(buffero); //데이터를 이 버퍼에다 담으라고 알려준다.
        buffero.flip(); //다 담고 시작 위치로 이동.
        while(buffer.hasRemaining()) System.out.println((char)buffero.get());
        //남은 데이터가 있을 동안 계속해서 데이터를 찍어낸다.
        channel2.close();
        /*
        Channel - getChannel로 객체 생성후 read와 write메서드에 버퍼만 담아서 주면 된다.
        Buffer가 좀 복잡하다. - CD처럼 위치가 있어서 버퍼에 읽거나 쓰는 작업을 수행하면 위치가 이동된다.
        capacity() - 담을 수 있는 크기, limit() - 읽거나 쓸 수 없는 위치, position() - 현재 위치
        put(data) - 데이터를 버퍼에 추가한다.
        flip() - limit의 값을 현재 위치로 변경 후 시작 위치로 이동. 뒤에 이상한 더미 데이터 값 못 읽게.
        rewind() - limit값 변경 없이 시작 위치로 이동.
        hasremaining()도 capacity까지가 아니라 limit까지만 계산한다.
        mark()-reset() -> flip의 커스텀 버전. 직접 위치 지정 후 그 위치로 되돌아가는 메서드.
         */
    }
}
