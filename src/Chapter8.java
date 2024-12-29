import java.io.*;
import java.util.Date;

public class Chapter8 {
    public static void main(String[] args) throws IOException {
        /*
            Input/Output 햇갈릴때는 JVM을 기준으로 생각하면 편하다.
            스트림 - 끊기지 않는 연속적인 데이터.
            JDK 1.4부터 NIO, JDK 7부터 NIO2라는 것이 나왔다. NIO는 버퍼와 채널 기반으로 데이터를 처리한다.
            NIO2에 Files라는 클래스가 나왔는데, File 클래스의 메서드를 static으로 구현해서 객체를 생성할 필요가 없다.

            File 클래스 - 파일 및 경로 정보를 통제하기 위한 클래스.
            isDirectory(), isFile(), isHidden() 폴더인지, 파일인지, 숨김파일인지 확인하는 메서드.
            canRead(), canWrite(), canExecute() 파일에 대한 권한 확인하는 방법.
            delete() 삭제하고 싶을 때.
         */
        File filename = new File("C:\\GodofJava\\text"); // \를 2개 써야한다.
        //File.separator라는 구분자용 static 변수도 있다.
        System.out.println(filename.exists());
        File file = new File("README.md");
        System.out.println(new Date(file.lastModified()));
        filename.mkdirs();
        //마지막 수정된 날짜를 long 타입으로 리턴하기 때문에, Date 클래스가 필요하다.

        /*
        파일을 만드는 메서드 - createNewFile()
        Absolute Path = 절대 경로
        file 객체의 경로가 상대적일 경우 두 값이 달라질 수 있다.
        C:\GodofJava\a\..\b - 절대 경로, C:\GodofJava\b -> 절대적이고 유일한 경로
        파일 이름을 제외하고 경로만 보고 싶을 때 - getParent() 메서드 사용해 부모 폴더의 경로를 찾으면 된다.
        Canonical Path = 절대적이고 유일한 경로
         */
        File file2 = new File("\\GodofJava\\text","testing.txt");
        try {
            file2.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(file2.getAbsoluteFile());
        System.out.println(file2.getCanonicalFile());
        System.out.println(file2.getName());
        System.out.println(file2.getPath()); //C드라이브 부분은 제외하고 출력한다.
        System.out.println(file2.getParent()); //C드라이브 부분은 제외하고 출력한다.

        File[] ff = File.listRoots(); //루트 디렉터리의 배열을 제공. C드라이브, D드라이브 등등
        System.out.println(ff.length);
        /*
        list() 현재 디렉터리의 하위에 있는 목록을 String으로 제공, listFiles()는 File의 배열 형태로 제공.
        FileFilter와 FileNameFilter 인터페이스를 매개변수로 가질 수 있다.
        특정 형식으로 끝나는 파일을 걸러내기 위한 기능.
        File 클래스를 매개변수로 받는 accept 메서드를 구현해야 한다.
        예제 - godofjava 폴더 내 test 폴더 내에 샘플 이미지를 복사하고, test.png라는 폴더를 생성한 뒤 실행
         */
        System.out.println("-----------------");
        //필터 X
        File pathtest = new File("\\GodofJava\\text");
        File[] filelist = pathtest.listFiles();
        for(File filetmp : filelist){
            System.out.println(filetmp.getName());
        }
        System.out.println("-----------------");
        //필터 O
        File[] filelist2=pathtest.listFiles(new PNGJPGFileFilter());
        for(File filetmp : filelist2){
            System.out.println(filetmp.getName());
        }
        System.out.println("-----------------");
        //필터 O - 파일인지 폴더인지 구분 못함.
        File[] filelist3=pathtest.listFiles(new PNGJPGFileFilter2());
        for(File filetmp : filelist3){
            System.out.println(filetmp.getName());
        }
        //JAVA7 이후에 추가된 Files 클래스가 더 편리하다. 나중에 배울 예정.

    }
    static class PNGJPGFileFilter implements FileFilter{
        @Override
        public boolean accept(File pathname) {
            if(pathname.isFile()){
                String filename = pathname.getName();
                return filename.endsWith(".png") || filename.endsWith(".jpg");
            }
            return false;
        }
    }
    static class PNGJPGFileFilter2 implements FilenameFilter{
        //이름을 미리 받기 때문에 getName을 할 필요가 없다는 장점이 있다. 하지만, 디렉터리와 파일을 구분할 수 없다.
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".png") || name.endsWith(".jpg");
        }
    }
}
