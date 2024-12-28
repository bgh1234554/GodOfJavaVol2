
public class Chapter2 {
    public static long parseLong(String data){
        long rtlong = -1;
        try{
            rtlong = Long.parseLong(data);
            System.out.println(rtlong);
        }catch(NumberFormatException e){
            System.out.println(data+" is not a number");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rtlong;
    }
    public static void printOtherBase(long val){
        System.out.println(val);
        System.out.println(Long.toBinaryString(val));
        System.out.println(Long.toHexString(val));System.out.println(Long.toOctalString(val));
    }
    public static void main(String[] args) {
        //Wrapper 클래스 복습
        //parse타입이름() 기본 자료형 리턴, valueOf() 참조 자료형 리턴
        //참조 자료형은 원칙적으로는 값을 더할 수 없다. 기본 자료형을 받을 수 없는 제네릭을 사용하기 위해서 존재한다.
        Integer refint1 = 100;
        System.out.println(refint1.doubleValue());

        //시스템 클래스 - 시스템에 대한 정보를 확인한다.
        //시스템 속성값 관리, 환경값 조회 등을 수행한다.
        //Property는 Hashtable의 상속을 받았다. Properties는 변경 가능하지만, env는 변경할 수 없고 읽기만 가능하다.
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getenv("JAVA_HOME"));
        //System.exit(0); 절대 사용하면 안됨.
        //currentTimeMillis() -> 현재시간을 알기 위한 용도
        //nanoTime() -> 시간 측정을 위한 용도
        //객체를 출력할 때에는 toString()보다 valueOf() 메소드가 더 안전하다.
        //printf()는 C언어에서 사용하는 그것과 동일함.
        printOtherBase(1024);
    }
}