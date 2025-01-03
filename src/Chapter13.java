import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class GetSum extends RecursiveTask<Long>{
    long from, to;
    public GetSum(long from, long to){
        this.from = from;
        this.to=to;
    }
    @Override
    public Long compute() {
        long gap = to-from;
        long sum=0;
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log("From : "+from+"~"+to);
        if(gap<=3){
            for(long loop=from;loop<=to;loop++){
                sum+=loop;
            }
            log("Returned : "+from+"~"+to+"="+sum);
            return sum;
        }
        long mid = (from+to)/2;
        GetSum sumPre=new GetSum(from,mid);
        log("sumPre : "+from+"~"+mid);
        sumPre.fork();
        GetSum sumPost=new GetSum(mid+1,to);
        log("sumPost : "+(mid+1)+"~"+to);
        return sumPost.compute()+sumPre.join();
        //fork() - 작업 하나를 수행시킴. 알아서 쓰레드를 만든다.
        //join() - Thread의 join과 같으며 계산이 다 될때까지 기다림.
        //나머지 작업을 compute()로 재귀적으로 호출하여 수행.
    }
    public void log(String message){
        String threadName=Thread.currentThread().getName();
        System.out.println("["+threadName+"]"+message);
    }
}

public class Chapter13 {
    static ForkJoinPool joinPool = new ForkJoinPool();
    public static void main(String[] args) throws IOException {
        /*
        Fork/Join - CPU를 더 쉽게, 효율적으로 사용할 수 있다. 특히 많은 양의 계산을 수행해야 할때,
        작업을 쪼개 나눠서 하게 할 수 있다.

        RecursiveAction과 RecursiveTask<V> 클래스를 상속받아서 구현할 수 있다.
        두 추상 클래스 모두 ForkJoinClass를 상속받았다.
        보통 if-else 조건문을 사용해 재귀적으로 호출되는 방법으로 주로 구현하며,
        compute() 메서드가 재귀호출되고 연산을 수행한다.
         */
        calculate();
        /*
        Paths 클래스와 Files 클래스 - Java 7에서 추가된 NIO2의 일종
        Paths.get(경로) 메소드를 통해 Path 객체를 얻을 수 있으며, 이 인터페이스는 파일과 경로에 대한 정보를 갖고 있다.
         */
        String dir = "C:\\GodofJava\\nio\\nio2";
        checkPath(dir);
        String dir1= "C:\\GodofJava";
        String dir2= "C:\\Windows";
        checkPath2(dir1,dir2);
        /*
        Files 클래스 - 기존의 File 클래스보다 훨씬 더 많은 메서드를 제공하며, static 메서드라 객체 생성 필요 없음.
        copy(), move(), delete(), createFile(), createDirectories(), readAllLines(), write(), exists() 등등이 존재한다.
         */
        Path path = Paths.get("C:\\GodofJava\\FilesTest.txt");
        writeFile(path); readFile(path);
        copyMoveDelete(path);
    }

    private static void copyMoveDelete(Path path) throws IOException {
        Path toPath = path.getParent(); //부모 폴더의 경로
        Path copyPath = toPath.resolve("copied"); //이렇게 해도 생성 안됨.
        if(!Files.exists(copyPath)){
            Files.createDirectories(copyPath); //폴더 생성
        }

        Path copiedFile = copyPath.resolve("copied.txt");
        StandardCopyOption standardCopyOption = StandardCopyOption.REPLACE_EXISTING;
        //기존에 파일이 있으면 덮어쓰기
        Files.copy(path,copiedFile,standardCopyOption);

        System.out.println("COPIED SUCCESSFULLY");
        readFile(copiedFile);

        // Move File. Files 클래스의 move 메서드는 파일을 옮기고 새 파일 경로를 리턴한다.
        //move(소스 파일, 옮길 경로, 옵션)
        Path movedFile = Files.move(copiedFile,toPath.resolve("moved.txt"),standardCopyOption);

        //Delete File 그냥 delete 메서드 안에 Path 클래스를 넣으면 된다.
        Files.delete(movedFile);
        //이 외에도 Files 클래스에는 createTempDirectory(), createTmpFile() 등의 메서드도 존재한다.
        //더 자세한 것은 API 문서 참조.
    }

    public static void calculate(){
        long from = 1; long to = 10;
        GetSum sum = new GetSum(from,to);
        Long result = joinPool.invoke(sum);
        //ForkJoinPool 클래스의 invoke 메서드를 통해 계산을 수행하는 객체를 넘겨준다.
        //그러면 알아서 compute 메서드를 호출해서 계산을 수행한다.
        System.out.println("Sum is "+result);
    }
    public static void checkPath(String dir){
        Path path = Paths.get(dir);
        System.out.println(path); //없는 경로는 생성해서 리턴
        System.out.println(path.getFileName()); //폴더 이름
        System.out.println(path.getNameCount()); //하위 폴더 수
        System.out.println(path.getParent()); //부모 폴더 수
        System.out.println(path.getRoot()); //루트 폴더 (드라이버 이름)
    }
    public static void checkPath2(String dir1,String dir2){
        Path path1 = Paths.get(dir1);
        Path path2 = Paths.get(dir2);
        Path relativized = path1.relativize(path2);
        Path absolute = relativized.toAbsolutePath();
        Path normalized = absolute.normalize();
        Path resolved = path1.resolve("Path3");
        System.out.println(relativized); //상대 경로 - 커맨드 창에서 어떻게 이동(cd ..)해야 하는지
        System.out.println(absolute); //절대 경로 - 해당 폴더를 기존으로 했을 때의 절대 경로
        System.out.println(normalized); //경로 상에 있는 .이나 ..을 없애는 작업 수행.
        System.out.println(resolved); //매개변수로 받은 경로를 파일 마지막에 생성
    }
    public static void writeFile(Path path) throws IOException {
        List<String> contents = new ArrayList<>();
        contents.add("안녕하세요.");
        contents.add("저는 트위치에서 방송을 하고 있는");
        contents.add("스트리머 케인입니다.");
        contents.add("현재 날짜 = "+new Date());
        Charset charset = Charset.forName("EUC-KR");
        StandardOpenOption openOption = StandardOpenOption.CREATE;
        Files.write(path,contents,charset,openOption);
        //write 매개변수. 경로, 내용, 인코딩방식, 여는 방법
        /*
        CREATE, CREATE_NEW, APPEND, DELETE_ON_CLOSE, WRITE 등등이 존재한다.
         */
    }
    public static void readFile(Path path) throws IOException {
        Charset charset = Charset.forName("EUC-KR");
        System.out.println("Path = "+path);
        List<String> contents = Files.readAllLines(path,charset);
        //readAllLines(경로, 인코딩 방법) 당연히 시간이 많이 걸리기 때문에 큰 파일에서는 쓸 수 없다.
        for(String content:contents){
            System.out.println(content);
        }
    }
}
