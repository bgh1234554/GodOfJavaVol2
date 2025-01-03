import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Supplier;

public class Chapter14 {
    public static void main(String[] args) throws Exception {
        /*
        Optional 클래스 - null 처리를 보다 간편하게 하기 위해서 등장한 클래스.
        new 연산자로 객체를 생성하지 않는다.
         */
        Optional<String> emptyString = Optional.empty();
        String content = null; //데이터 없는 빈 객체 생성
        Optional<String> nullableString = Optional.ofNullable(null);
        //안에 null 값이 들어갈 수 있는 경우에 생성.
        content="String";
        Optional<String> notNullableString = Optional.of(content);
        //isPresent() 값이 존재하는지 확인.
        System.out.println(Optional.of("present").isPresent());
        System.out.println(Optional.ofNullable(null).isPresent());
        //데이터를 얻는 방법.
        String result = notNullableString.get(); //가장 일반적임.
        String result2 = nullableString.orElse("ZERO"); //값이 null일 경우 대체 값을 지정해준다.
        //안에 함수를 넣게할 수도 있는데 이때는 Supplier<T> 인터페이스와 orElseGet을 사용한다.
        String result3 = notNullableString.orElseGet(new Supplier<String>() {
            @Override
            public String get() {
                return "SupplierTest";
            }
        });
        //함수가 예외를 발생시키게 하고 싶을 때는 orElseThrow 메서드를 사용하면 된다.
//        String result4 = nullableString.orElseThrow(new Supplier<Exception>() {
//            @Override
//            public Exception get() {
//                return new NullPointerException();
//            }
//        });
        /*
        Default method - JAVA 8부터 인터페이스에 default 키워드를 메서드 앞에 붙여 미리 구현할 수 있다.
        사용자는 implements만 쓰면 override하지 않아도 그 메서드를 사용할 수 있다.
        하위 호환성을 위해서 등장했다.
         */
        /*
        Arrays 클래스 - 배열과관련된 연산을 사용할때 편함.
        binarySearch() -> 배열의 이진탐색
        copyOf() -> 배열 복사
        equals() -> 배열의 비교
        fill() -> 배열 채우기
        hashCode() -> 배열의 해시코드
        sort() -> 배열 정렬하기. Fork/Join을 사용하는 parallelSort()도 있지만 크기가 크지 않으면 사용할 필요 없다.
        toString() -> 배열 내용 출력하기
         */
        int[] intValues = {1,2,3,4,10,9,8,7,6,5};
        Arrays.parallelSort(intValues);
        /*
        StringJoiner 클래스 - 문자열의 배열 안에 있는 문자열을 붙일때 사용할 수 있다.
         */
        String[] strings = new String[]{"This book","is","too long","to finish"};
        StringJoiner stringJoiner = new StringJoiner(",","(",")");
        //어떻게 붙일 건지, 맨 앞에 뭐 놔두고, 맨 뒤에 뭐 놔둘건지 정하는 것.
        for(String string:strings){
            stringJoiner.add(string);
        }
        System.out.println(stringJoiner);
    }
}
