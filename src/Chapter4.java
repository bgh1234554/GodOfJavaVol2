import java.util.*;

public class Chapter4 {
    /*
    크기가 정해지지 않은 데이터를 저장할 때 사용할 수 있는 자료구조들이 있다.
    Collection 인터페이스를 상속받은 - ArrayList (Vector), Linked List
    Map 인터페이스를 상속받은 - HashMap

    Collection 인터페이스에 있는 메서드 복습
    add - 그냥 추가, addAll(Collection) - ArrayList 같은걸 Deep Copy할 때 사용할 수 있다.
    contains - 있는지 없는지 확인 containsAll(Collection)
    iterator() - Iterator<String> itr = stack.iterator()와 같이 사용 가능
    remove, removeAll(Collection)
    retainAll(Collection) - Collection에 있는 내용만 남기기

    ArrayList 생성할때 ()안에 숫자 넣어서 최초 크기 지정 가능함. 안하면 기본으로는 10개임.

    데이터 꺼낼 때 - get() 인덱스 찾을때 - indexOf(), lastIndexOf() 배열에 담긴 원소 수 알아낼 때 - size()
    데이터 지울 때 - clear() 모든 데이터 지움, remove(index) - 위치 지정
    remove(Object) - 그 자리에 있던 데이터를 리턴한다
    removeAll(Collection) - 내용 지정, All 쓸때 ArrayList에 담아서 써야하고,
    remove는 제일 처음 것만 지우지만, removeAll은 내용이 똑같은건 모두 지움.

    set(index, E element) - 위치를 지정해 값을 변경하고, 원래 있던 값을 리턴한다.

    STACK - vector을 상속받음.
    ArrayDeque가 성능이 더 빠르지만, Thread에 안전하지 못하다.
    empty(), peek() 비었는지 확인, 나중에 들어간 값 리턴
    pop(), push() 빼고 넣는거
    search() 데이터의 위치를 리턴함.
     */
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("A"); list.add("B"); list.add("C"); list.add("D"); list.add("E");
        ArrayList<String> list2 = new ArrayList<>();
        list2.addAll(list);
        for(String tmp:list2){
            System.out.println(tmp);
        }
        //객체 안에 있는 데이터를 배열로 뽑아낼 때 - toArray() 메서드 사용.
        //arint.remove(Integer.valueOf(32)); ArrayList가 Integer일때, 값을 지정해서 빼고 싶을땐 valueOf 사용.
        String[] strList = list.toArray(new String[0]); //매개 변수 안에도 데이터가 저장되기 때문에 크기를 0으로 호출하는 것이 좋음.
        for(String tmp:strList){
            System.out.println(tmp);
        }
    }
}
