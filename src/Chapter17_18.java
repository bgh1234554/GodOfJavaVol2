import java.util.*;
import java.util.function.BinaryOperator;

public class Chapter17_18 {
    public static void main(String[] args) {
        /*
        JAVA 10 - var의 등장. 파이썬처럼 변수 타입을 명시적으로 알 수 있는 경우에는 타입을 쓸 필요가 없다.
         */
        var hello = "Hello World!";
        var list = new ArrayList<>();
        list.add("Hello World");
        list.add(1);
        var obj = list.get(0);
        //ArrayList<String>이었으면 이렇게 사용할 수 없다.
        var fruits = List.of("Apple","Pear","Strawberry");
        //fruits.add("Grape"); //불변 객체에 객체를 추가하려고 해서 이렇게는 할 수 없고, ArrayList로 만들어야 함.
        /*
        JAVA 9부터 추가된 불변 데이터 리스트를 만드는 방법 - List.of()
        Map.of(K k1, V v1, K k2, V v2...)와 같은 방식으로 가능하다.
        데이터를 몇개만 간단히 넣을때 사용하기 편하다.
        */
        ArrayList<String> fruits2 = new ArrayList<>();
        fruits2.addAll(fruits);
        //for(var fruit : fruits) fruits2.add(fruit);
        //ArrayList<String> fruits2 = new ArrayList<>(fruits);

        //이 외에도 Stream.of(....).toList()와 같은 방식으로 JAVA 16부터 만들 수 있고,
        //그 전 버전에서도 fruits2.stream().collect(toUnmodifiableList());를 사용하여 만들 수 있다.
        /*
        JAVA 11
        1. 컴파일이 필요없이 .java 파일 실행이 가능해졌다.
        명령어는 그냥 java <파일 이름.java> [args에 들어갈 내용] 으로 가능하다.
        2. 람다 함수 안에서도 var 키워드를 사용하는 것이 가능해졌다.
         */
        if(args.length>0){
            Arrays.stream(args).forEach(System.out::println);
        }
        else{
            System.out.println("No args!");
        }
        BinaryOperator<Integer> sumNum = (var x, var y)->x+y;
        BinaryOperator<Integer> sumNum2 = (var x, var y)->x*y;//Integer 형식일 것이 당연하니까.
        List<Integer> numbers = Arrays.asList(1,2,3,4,5); //List 타입이라 add가 구현이 안되어있다.
        //numbers.add(6);
        int sum = numbers.stream().reduce(0,sumNum);
        int sum2 = numbers.stream().reduce(1,sumNum2);
        //.reduce() BinaryOperator에 구현된 함수를 사용해서 주어진 배열에서 값을 만들어냄. SQL의 집계함수처럼.
        System.out.println(sum);
        System.out.println(sum2); //0을 제일 처음으로 두고 y에 index 0부터 차례로 대입하는 것 같다.

    }
}
