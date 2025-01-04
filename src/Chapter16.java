import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Random;

interface RandomValueGenerater{
    int getRandomNumber();
    default int generateIntegerRandomNumber(int bound){
        return generateRandomNumber(bound);
    }
    private int generateRandomNumber(int bound){
        Random random = new Random();
        return random.nextInt(bound);
    }
}

public class Chapter16 {
    public static void main(String[] args) {
        /*
        JAVA 업데이트 문서 직접 확인하는 방법 - https://openjdk.org/projects/jdk/23 과 같이 확인할 수 있다.
        9 이하 버전에서는 projects/jdk9와 같은 주소를 사용한다.
        JAVA 9에서 변경된 것들
        1. String 클래스의 내부 구조가 변경되면서 성능이 향상되었다.
        2. interface에 private 메서드를 추가할 수 있다.
         */
        RandomValueGenerater rvg = new RandomValueGenerater() {
            @Override
            public int getRandomNumber() {
                return generateIntegerRandomNumber(100);
            }
        };
        for(int i=0;i<10;i++){
            rvg.getRandomNumber();
            //private 메서드를 인터페이스 내에 만들면 해당 인터페이스를 구현하는 클래스에서는
            //메서드 구현이 어떻게 되어있는지 살펴볼 필요가 없어진다.
        }
        /*
        새로운 HTTP 클라이언트
        웹사이트에 연결 - HttpClient 클래스 사용
        웹사이트에 데이터를 보낼 때 = HttpRequest 클래스 사용
        timeout -> 응답이 없을 시 얼마나 기다릴지에 대한 기간을 설정할 수 있다.
         */
        try (HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()) {
            //uri안에는 URI 클래스만 들어갈 수 있어서 create 메서드를 이용해 생성해줘야 한다.
            HttpRequest httpRequest = HttpRequest.newBuilder().GET().timeout(Duration.ofSeconds(10)).uri(URI.create("https://www.google.com")).build();
            HttpResponse<String> response = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
