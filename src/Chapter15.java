import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
interface Calculate{
    int operation(int a, int b);
}

class StudentDTO{
    String name;
    int age;
    int scoreMath;
    int scoreEnglish;
    public StudentDTO(String name, int age, int scoreMath, int scoreEnglish) {
        this.name = name;
        this.age = age;
        this.scoreMath = scoreMath;
        this.scoreEnglish = scoreEnglish;
    }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public int getScoreMath() {return scoreMath;}
    public void setScoreMath(int scoreMath) {this.scoreMath = scoreMath;}
    public int getScoreEnglish() {return scoreEnglish;}
    public void setScoreEnglish(int scoreEnglish) {this.scoreEnglish = scoreEnglish;}
}

public class Chapter15 {
    public static void main(String[] args) {
        /*
        람다 표현식
        (매개변수) -> 리턴값.
        메서드가 하나인 인터페이스를 Functional Interface라고 하며, 어노테이션을 사용하면 편하다.
        Funcitonal Interface를 구현할때 람다 표현식을 사용하면 유용하다.
         */
        Calculate add = (a,b) -> a+b;
        System.out.println(add.operation(1,2));
        new Thread(()->System.out.println(Thread.currentThread().getName())).start();
        /*
        Stream 클래스 - Collection 클래스의 List에 대한 정보를 뽑아내기 위해 사용함.
         */
        Integer[] values = {1,3,5};
        int[] val = {1,3,5}; //타입 변수는 for문으로 직접 넣어줘야 한다.
        List<Integer> list2 = new ArrayList<>();
        for(int value:val) list2.add(value);
        System.out.println("list2.add(value)"+list2);
        List<Integer> list = new ArrayList<>(Arrays.asList(values));
        System.out.println("Arrays.asList(values)"+list);
        //List.addAll()은 Collection만 매개변수로 받을 수 있다.
        List<Integer> listStream = Arrays.stream(values).toList();//.toList();
        System.out.println("Arrays.stream(values).toList()"+listStream);
        /*
        list.stream().filter(x->x>10).count()
        .stream() 메서드를 통해 스트림 객체로 변환한 뒤, 중개 연산을 통해서 연산을 처리한다.
        이후, 종단 연산에서 그 결과를 바탕으로 결과를 리턴해준다.
        stream()은 인덱스 0부터 차례로 결과를 처리한다. 빠르게 처리하기 위해서는 parallelStream()을 사용할 수 있으나,
        일반적인 웹 프로그래밍에서는 사용하지 않는다.
        여러 메서드를 제공하지만, 가장 많이 사용하는 것은 forEach(), map(), filter() 정도이다.
         */

        /*
        stream.forEach() 예제 - StudentDTO 클래스의 List에 담긴 정보를 출력하기.
        map에서 학생의 정보가 담긴 DTO를 이름으로 변환했기 때문에,
        map 메서드 이후로는 List<String>을 처리하듯이 할 수 있다.
        이후 forEach메서드는 우리가 아는 범위기반 for문과 동일하게 작동한다.
         */
        List<StudentDTO> studentDTOList=List.of(new StudentDTO("Baek",26,100,100),
                new StudentDTO("Park",23,70,80),
                new StudentDTO("Jo",23,80,50),
                new StudentDTO("Jeon",29,92,81));
        studentDTOList.stream().map(student->student.getName()).forEach(name->System.out.println(name));
        studentDTOList.stream().map(StudentDTO::getScoreMath).forEach(System.out::println);
        studentDTOList.stream().map(StudentDTO::getScoreEnglish).forEach(System.out::println);
        //studentDTOList.stream().map(StudentDTO::getName).forEach(System.out::println);
        /*
        메서드 안에 쓰인 람다함수는 위와같이 메서드 참조로 변경할 수 있다. 이것도 JAVA 8에서 추가되었다.
        메서드 참조의 종류는 총 4가지이다.
        1. static 메서드 참조.
        2. 특정 객체의 인스턴스 메서드 참조. SYstem.out::println과 같은 경우다.
        3. 특정 유형의 임의의 객체에 대한 인스턴스 메서드 참조. -> static 메서드가 아니어도 참조변수를 사용할 수 있다.
        4. String::new와 같은 방식으로 생성자에도 사용할 수도 있다. 물론 이렇게 사용하는 사람은 별로 없을 수 있다.

        map()함수 예제. 학생들의 수학 점수를 0.3배 해서 계산해보기. 소수점 둘째자리에서 반올림하기
        가장 많이 사용되는 연산이므로 무조건 알아둬야 한다.
         */
        studentDTOList.stream().map(StudentDTO::getScoreMath).map(math->math*0.3).forEach(System.out::println);
        studentDTOList.stream().map(StudentDTO::getScoreMath).map(math->Math.round(math*30.0)/100.0).forEach(System.out::println);
        List<String> studentNames = studentDTOList.stream().map(StudentDTO::getName).toList();
        //studentDTO에서 이름만 추출해내는 방법. .toList() 메서드가 유용하게 사용된다.
        System.out.println(studentNames);
        /*
        filter() - 매개변수로 조건문을 넣어서 조건에 맞는 사람만 필터링하고 이후에 연산을 수행할 수 있도록 한다.
        SQL의 where절과 같은 역할을 한다.
        나이가 24살 이상인 사람의 이름과 그 사람의 영어 점수를 출력해보자.
         */
        studentDTOList.stream().filter(student->student.getAge()>=24)
                .forEach(student->System.out.println(student.getName()+":"+student.getScoreEnglish()));
        //스트림은 코드가 간결해지는 분명한 장점이 있으므로 알아두면 좋다.
    }
}
