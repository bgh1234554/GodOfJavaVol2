import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

public class Chapter13_1 extends Thread {
    String dirName;
    public static void main(String[] args) throws InterruptedException {
        /*
        WatcherService - 파일에 변경사항이 생겼을 때 이를 알려주는 메서드.
         */
        String dirName="C:\\GodofJava"; //감시하고자 하는 경로
        String fileName="WatcherSample.txt";
        Chapter13_1 sample = new Chapter13_1(dirName);
        sample.setDaemon(true);
        sample.start();
        Thread.sleep(1000);
        for(int loop=0;loop<10;loop++){
            sample.fileWriteDelete(dirName,fileName+loop);
            //main절에서는 파일을 쓰고 지우고, 이를 감지할때마다 Daemon 쓰레드에서 메시지를 내뿜는다.
        }
    }
    public Chapter13_1(String dirName){
        this.dirName=dirName;
    }
    @Override
    public void run() {
        System.out.println("Watcher Thread has started!");
        System.out.printf("Dir=%s\n",dirName);
        addWatcher();
    }
    public void addWatcher(){
        try{
            Path dir = Paths.get(dirName);
            WatchService watcher = FileSystems.getDefault().newWatchService();
            //가장 쉽게 생성하는 방법.
            WatchKey key = dir.register(watcher, ENTRY_CREATE,ENTRY_DELETE,ENTRY_MODIFY);
            //register 메서드를 통해 어떤 것을 감시하는지 등록할 수 있다.
            while(true){
                key = watcher.take();
                //이벤트가 발생하때까지 기다리다가, 이벤트를 받으면 리턴한다.
                List<WatchEvent<?>> eventList = key.pollEvents();
                //한번에 여러개의 이벤트가 리턴될 수 있기 때문에 List 안에 넣는다.
                //일반적으로는 하나만 저장되지만, 여러개일 수도 있기 때문에 이와 같이 저장한다.
                for(WatchEvent<?> event : eventList){
                    Path name = (Path)event.context();
                    //context 메서드는 이벤트 당시 선언했던 객체 타입을 리턴한다.
                    if(event.kind()==ENTRY_CREATE){
                        System.out.printf("%s created\n",name);
                    } else if(event.kind()==ENTRY_MODIFY){
                        System.out.printf("%s modified\n",name);
                    } else if(event.kind()==ENTRY_DELETE){
                        System.out.printf("%s deleted\n",name);
                    }
                }
                key.reset(); //모든 처리가 끝난 후에 reset 처리한다.
            }
            //SetDaemon이 true이기 때문에 break문이 없어도 알아서 while문이 종료된다.
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
    private void fileWriteDelete(String dirName, String fileName) {
        Path path = Paths.get(dirName, fileName);
        String contents="Watcher Sample";
        StandardOpenOption openOption = StandardOpenOption.CREATE;
        try{
            System.out.println("Write" + fileName);
            Files.write(path,contents.getBytes(),openOption);
            Files.delete(path);
            Thread.sleep(1000);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
