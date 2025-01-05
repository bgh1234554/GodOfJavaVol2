import java.util.Objects;

record StudentRecordEx(String name, int age, int scoreMath, int scoreEnglish){
    public static String nationality="Russia"; //안에 변수를 선언할 때는 무조건 static으로 해야 한다.
    //모든 생성자가 주어지지 않았을 때에 대한 생성자도 만들 수 있다.
    public StudentRecordEx{
        Objects.requireNonNull(name); Objects.requireNonNull(age);
        //null 여부를 확인하는 메서드. null 값이 들어오면 NullPointerException을 발생시킨다.
        //모든 변수가 있는 생성자에 대한 조건을 만들때는 매개변수를 적어줄 필요가 없다.
    }
    public StudentRecordEx(String name, int age){
        this(name,age,-1,-1);
        //별도의 생성자를 맏늘 수 있다.
    }
}

sealed class SealedFordCar permits SealedMustang, SealedExplorer{
    public static String BRAND_NAME = "FORD";
    public SealedFordCar(){
        System.out.println("BRAND NAME="+BRAND_NAME);
    }
}

sealed class SealedMustang extends SealedFordCar permits SealedMustangShelby{
    public SealedMustang(){System.out.println("SealedExplorer - BRAND NAME="+BRAND_NAME);}
    public boolean isSportsCar(){return true;}
}

final class SealedMustangShelby extends SealedMustang{
    public SealedMustangShelby(){System.out.println("SealedMustangShelby - BRAND NAME="+BRAND_NAME);}
}

non-sealed class SealedExplorer extends SealedFordCar{
    public boolean isSUV(){return true;}
    public SealedExplorer(){System.out.println("SealedExplorer - BRAND NAME="+BRAND_NAME);}
} //다만 이후 이 클래스를 상속받아 이상한 클래스가 만들어질 수도 있으니 유의해야 한다.

public class Chapter19 {
    public static void main(String[] args) {
        /*
        JAVA 12부터 switch-case문이 점점 더 간단하게 변하기 시작했다.
        switch 앞에 값을 할당할 변수를 좌측항에 저어주고, case 뒤에 :가 아닌 -> 을 적어 값을 바로 할당할 수 있다.
        JAVA 14부터는 return 키워드처럼 switch-case문에서 사용할 수 있는 yield 키워드가 추가되었다.
         */
        /*
        JAVA 15부터 텍스트 블럭이 만들어져 더 직관적으로 문자열을 출력할 수 있다.
        맨 앞에 있는 문자열을 기준으로 공백이 만들어진다.
         */
        String stringBlock = """
                Hello.
             This is
                a
                  StringBlock
                """;
        System.out.println("1["+stringBlock+"]");
        /*
        JAVA 16부터 record 클래스가 추가되었다.
        StudentDTO 클래스 참조. 여러값을 한번에 돌려주기 위해서 DTO 클래스를 사용하는데, record가 이를 대신할 수 있다.
        record의 특징
        1. 한번 지정한 값은 수정이 불가능
        2. 생성자 자동 생성
        3. equals(), toString(), Hashcode() 자동 생성.

        - DB에서 데이터를 조회한 결과를 확인하는 경우에 유용하게 사용할 수 있으나,
        DB 데이터의 값을 변경해야하는 상황이 발생할 경우, record보단 DTO 클래스를 사용하는 것이 낫다.
         */
        StudentRecordEx recordEx = new StudentRecordEx("Danil Ryzhakov",24,85,99);
        System.out.println(recordEx); //toString함수가 자동 구현되어있다.
        //getter 함수가 자동으로 만들어지며, get.. 대신에 속성값을 메서드로 만들어놓았다.
        System.out.println(recordEx.age());
        System.out.println(recordEx.name());
        System.out.println(recordEx.scoreEnglish());
        //equals와 hashCode 자동 구현 메서드 테스트
        StudentRecordEx recordEx2 = new StudentRecordEx("Danil Ryzhakov",24,85,99);
        System.out.println(recordEx.equals(recordEx2));
        System.out.println(recordEx.hashCode());
        System.out.println(recordEx2.hashCode());
        /*
        sealed 클래스 - JAVA 17에 추가됨
        영어로 봉인된, 포장된이라는 뜻과 맞게 어떤 클래스에 상속을 허용할지 결정할때 사용한다.
        이후, 상속받은 자식 클래스는
        1. 반드시 같은 모듈에 존재해야 하며,
        2. 명시적을 부모클래스를 확장해야 하고,
        3. final, sealed, non-sealed 클래스중 하나로 선언되어야 한다.
        final - 더 이상 자식 클래스 불가능
        sealed - 특정 클래스에서만 자식 가능.
        non-sealed - sealed 제한 해제.

        JAVA 14에 추가된 instanceof의 개선된 버전에 sealed 클래스를 유용하게 활용할 수 있다.
        instanceof 다음에 변수를 할당해서, 형변환을 별도로 할 필요없이 바로 사용할 수 있다.
         */
        SealedFordCar fordShelby = new SealedMustangShelby();
        if(fordShelby instanceof SealedExplorer explorer) System.out.println("is SUV="+explorer.isSUV());
        else if(fordShelby instanceof SealedMustang mustang) System.out.println("is Sports Car="+mustang.isSportsCar());
        else System.out.println("It's a normal car");
    }
    public static void switchCase(int month){
        int days;
        days=switch(month){
            case 1,3,5,7,8,10,12->31;
            case 4,6,9,11->30;
            default -> 0;
        };
        if(month==2){
            System.out.println(month+" has 28 or 29 days");
        } else if (days==0) System.out.println("Such month does not exist");
        else{
            System.out.println(month+" has"+days+" days");
        }
    }
    public static void switchCase2(int month){
        int days;
        days=switch(month){
            case 1,3,5,7,8,10,12:
                yield 31;
            case 4,6,9,11:
                yield 30;
            default: yield 0; //추가적인 구문을 사용할 수 없다.
        };
        if(month==2){
            System.out.println(month+" has 28 or 29 days");
        } else if (days==0) System.out.println("Such month does not exist");
        else{
            System.out.println(month+" has"+days+" days");
        }
    }
    public static void switchCase3(int month){
        int days;
        days=switch(month){
            case 1,3,5,7,8,10,12->31;
            case 4,6,9,11->30;
            default -> {
                System.out.println("Not assigned");
                yield 0;
            } //->를 사용할때, 두줄 이상으로 구현할 떄에는 yield를 사용하면서 반드시 중괄호로 묶어야 한다.
        };
        if(month==2){
            System.out.println(month+" has 28 or 29 days");
        } else if (days==0) System.out.println("Such month does not exist");
        else{
            System.out.println(month+" has"+days+" days");
        }
    }
}
