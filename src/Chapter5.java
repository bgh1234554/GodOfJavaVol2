import java.util.*;
public class Chapter5 {
    /*
    여러 중복되는 값들을 걸러낼 때 Set을 사용하는 것이 유용하다. Set의 메서드는 ArrayList와 거의 동일하다.
    순서를 찾을 수 있는 메서드가 없다는 것만 다르다.

    Linked List - Stack이면서 Queue면서 Deque이다. 그래서 모든 메서드를 다 가지고 있다.
    배열보다, 인덱스 중간에서 추가와 삭제가 많이 발생할 때 더 유리하다.
    이때문에 똑같은 기능을 하는 메서드가 엄청 많은데, 결국에 뜯어보면
    추가하는 건 addFirst, addLast 사용하는게 제일 낫고, 데이터 뽑는것도 getFirst, getLast가 낫다.
    위치 찾기는 contains, indexOf, lastIndexOf로 동일
    제거하는 건 removeFirst, removeLast를 사용하는 것이 제일 낫다.
    Linked List의 Iterator는 특이한데, ListIterator 인터페이스가 구현되어 있어,
    next()외에도 previous()로 앞뒤를 모두 왔다갔다 할 수 있다.
     */
    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            System.out.println(getSixNumber());
        }
    }
    public static HashSet<Integer> getSixNumber(){
        Random random = new Random();
        HashSet<Integer> hashSet = new HashSet<>();
        while(hashSet.size()!=6){
            int tmpNum = random.nextInt(45)+1;
            hashSet.add(tmpNum);
        }
        return hashSet;
    }
}
