import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
public class Chapter6 {
    /*
    Map - Python의 Dict 같은것. Key와 Value를 통해 저장하는 것.
    Key를 객체로 할 경우에는 HashCode()와 Equals() 메서드를 잘 재정의하는 것이 중요하다.
    값을 넣을 때 add가 아닌 put으로 한다는 것도 중요하다.
    키 값의 모음은 keySet 메서드로 Set<K> 형태로 반환하고, 값의 모음은 Values를 Collection<V> 형태로 반환한다.
    Entry의 모음은 entrySet 메서드로 출력할 수 있다.
    Map에서 get 메서드를 썼는데 존재하지 않을 경우, null을 리턴한다.
    containsKey(), containsValues()도 존재한다. boolean 메서드.

    Hashtable과 Map은 큰 차이는 없지만, Hashtable은 Null값을 키로 저장할 수 없고 쓰레드에 안전하다.
    */
    public static void main(String[] args) throws Exception {
        //Map을 Thread Safe하게 만드는 방법
        Map m = Collections.synchronizedMap(new HashMap<String,String>());

        HashMap<String,String> hashMap = new HashMap<>(); //크기를 지정할 수 있다.
        Set<Map.Entry<String,String>> mapEntries = hashMap.entrySet();
        /*
            정렬된 키의 목록을 원하면 TreeMap을 사용할 수 있다. 정렬 순서는 숫자 > 대문자 > 소문자 > 한글 순이다.
            TreeMap은 SortedMap 인터페이스를 구현했기 때문에, firstKey(), lastKey(),
            특정 키 앞에 있는 higherKey(), 특정 키 뒤에 있는 lowerKey() 등의 메서드도 제공해준다.
         */
        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("A","a");
        treeMap.put("a","A");
        treeMap.put("1","One");
        treeMap.put("가","Ga");
        Set<Map.Entry<String,String>> entries = treeMap.entrySet();
        for(Map.Entry<String,String> tmp:entries){
            System.out.println(tmp.getKey()+" : "+tmp.getValue());
        }
        /*
        Hashtable 클래스를 상속받은 System 클래스의 Properties 클래스
        getProperties() 메서드를 호출하면 나오는 Properties 클래스 객체를 사용하는 방법이 Map과 동일하다.
        user.language,user.dir,file.encoding과 같은 시스템 속성과 관련된 내용이 저장되어 있다.

        Properties 클래스에 있는 메서드
        load(InputStream/Reader) - 파일에서 속성을 읽는다
        store(OutputStream/Writer, comment) - 파일에 속성을 저장한다.
        storeToXML(OutputStream, comment, encoding) - XML로 구성되는 속성 파일을 저장한다.
        comment는 파일에 주석으로 저장된다.

        XML - 태그로 이루어진 텍스트 문서. HTML도 이의 일종이라고 볼 수 있다
            API 서비스는 어떤 데이터를 정해져 있는 XML 형태로 요청하면, 정해져 있는 형태의 XML로 데이터를 받을 수 있는
            기능을 제공하는 역할을 한다. 요즘은 JSON파일 형식도 사용한다.

        따라서, 사용하는 애플리케이션에 사용할 여러 속성값들을 Properties 클래스의 메서드를 통해 넣고 뺄 수 있다.
        Properties 객체에 속성을 넣을 때는 setProperty 메서드를 사용한다.
         */
        Properties properties = System.getProperties();
        Set<Object> keySet=properties.keySet();
        for(Object key:keySet){
            System.out.println(key+"="+properties.get(key));
        }

        //Properties 파일 생성 예시 - .properties라는 확장자로 끝나는 각종 설정 파일은 이와 같은 방식으로 생성된다.
        FileOutputStream fos = new FileOutputStream("test.properties");
        Properties prop = new Properties();
        prop.setProperty("Writer","BaekJiHoon");
        prop.setProperty("BookName","GodOfJavaVol2");
        prop.store(fos,"Basic Properties File Test");
        fos.close();

        FileInputStream fis = new FileInputStream("test.properties");
        Properties prop2 = new Properties();
        prop2.load(fis);
        fis.close();
        System.out.println(prop2); //Hashtable 클래스에 toString 메서드가 구현이 되어있기 때문에 JSON파일처럼 출력된다.

        //XML파일 생성 예시
        FileOutputStream fos2 = new FileOutputStream("test.xml");
        Properties propxml = new Properties();
        propxml.setProperty("Writer","BaekJiHoon");
        propxml.setProperty("BookName","GodOfJavaVol2");
        propxml.storeToXML(fos2,"Basic XML File Test");
        fos2.close();

        FileInputStream fis2 = new FileInputStream("test.xml");
        Properties prop2xml = new Properties();
        prop2xml.loadFromXML(fis2);
        fis2.close();
        System.out.println(prop2xml);
    }
}
