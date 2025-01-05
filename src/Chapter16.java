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
        /*
        JAVA 모듈 관련 설명 - 모듈화/직소 프로젝트
        패키지 최상단에 module-info.java가 존재해야한다. 다른 이름으론 사용할 수 없다.
        module Module1{ //여기서의 모듈 이름은 알아서 정할 수 있다.
            exports java9.module1; //외부에서 이 모듈을 가져다 쓸때, java9.module1만 직접적으로 사용할 수 있다.
            //모듈은 인텔리제이 기준 폴더 우하단에 파란색 네모가 있는 것이 모듈. 패키지가 모여있는 것.
        }
        예를들어, java9 패키지 안에 module0와 module1 패키지가 있을 때, module1은 module0을 import해서
        사용할 수 있지만, 다른 모듈에서 이 모듈에 있는 코드를 사용할 때에는 module0에 있는 코드를 직접적으로
        사용할 수 없고, module1이 module0을 import해서 구현한 코드를 이용해 간접적으로만 사용할 수 있다.
        다른 모듈에서 해당 모듈의 중요 로직과 소스 코드를 직접적으로 사용하지 못하게 막을 수 있다는 장점이 있다.
        다른 모듈에서 해당 모듈의 코드를 사용하고 싶을 때는
        module Module2{
            requires Module1;
        }
        과 같은 방식으로 module-info.java를 패키지 최상단 폴더에 구현해야 한다.

        최종적인 구조는 이와 같다.
        ├───Module1
        │   └───src
        │       ├───java9
        │       │   ├───module0 //패키지 명.
        │       │   └───module1
        │       └───module-info.java //exports java9.module1;
        └───Module2
            └───src
                └───java9
                │   ├───module2
                └───module-info.java //requires Module1
         */
    }
}
