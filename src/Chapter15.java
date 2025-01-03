@FunctionalInterface
interface Calculate{
    int operation(int a, int b);
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
    }
}
