class WildCardTest<W>{
    private W Wildcard;
    public W getWildcard() {
        return Wildcard;
    }
    public void setWildcard(W wildcard) {
        this.Wildcard = wildcard;
    }
}

public class Chapter3 {
    public static void main(String[] args) {
        //JAVA 제네릭에 ?가 있는 것 -> 와일드카드
        //어떤 타입을 넣은 매개변수가 올지 모를 때 사용할 수 있으나, 이러면 함수 내부에서는 타입을 정확하게 모르기 때문에
        //오브젝트로 처리해야한다. 매개변수로만 사용하는 것을 추천한다.
        WildCardTest<String> sample = new WildCardTest<>();
        sample.setWildcard("WildCardTest");
        wildCardStringMethod(sample);
        /*
        Bounded Wildcards - <? extends Car>와 같이 특정 클래스와 관련이 있는 클래스만 실행할 수 있도록 제한할 수도 있다.
        메서드를 제네릭하게 선언하려면 리턴 타입 앞에 <T>와 같은 기호를 사용하면 된다.
        이러면 와일드카드를 사용했을 때와 달리 매개변수로 사용된 객체에 값을 추가할 수 있다.
         */
    }

    public static void wildCardStringMethod(WildCardTest<?> w){
        Object val = w.getWildcard();
        if(val instanceof String){
            System.out.println(val);
        }
    }
}
