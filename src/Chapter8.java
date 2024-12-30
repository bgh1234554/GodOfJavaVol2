import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Chapter8 {
    public static void main(String[] args) throws IOException, NullPointerException {
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

        /*
        InputStream - FileInputStream, ObjectInputStream, FilterInputStream을 많이 사용
        FilterInputStream은 생성자가 protected라 자식 클래스를 통해서만 생성이 가능한데, 주로
        BufferedInputStream과 DataInputStream을 많이 사용한다.

        OutputStream도 동일한 종류의 클래스를 주로 사용하는데 flush() 메서드가 있다.
        대부분 저장할때 buffer을 가지고, 데이터를 쌓아두었다가 한번에 저장하는데,
        flush()는 새로운 내용을 기다리지 말고 지금 바로 저장하라고 호출하는 메서드다.

        둘 다 쓰고 난 뒤에는 꼭 close()를 해줘야한다.
        하지만, 보통 웹 개발을 할때는 Stream쪽보단 Reader와 Writer을 많이 사용한다.
         */
        /*
        파일에 글을 쓸 때 FileWriter 클래스를 쓰면 메서드를 호출할 때마다 파일에 쓰기 때문에 비효율적이다.
        따라서 단점을 보완하기 위해 BufferedWriter 클래스를 대개 이용한다.
        FileWriter 객체 생성시 IOException이 발생하는 경우
        1. 파일 이름이 파일이 아닌 경로인 경우
        2. 파일이 존재하지 않는데, 권한 문제로 생성할 수 없는 경우
        3. 파일이 존재하지만, 파일을 열 수 없는 경우.

        자세한 예제는 Recap 안에 있는 StreamEx와 StreamEx2.java를 보기.
        FileWriter(filename,true)라고 적을 시 뒤에 append가 되는 효과가 있다.

        BufferedReader와 BufferedWriter을 사용해 파일을 읽고, 파일에 쓸 수 있으나, Scanner 클래스를 사용할 수도 있다.
         */
        File fileScan = new File("\\Users\\MSI\\Desktop\\나무위키.txt");
        Scanner scanner = new Scanner(fileScan);
        while(scanner.hasNextLine()) System.out.println(scanner.nextLine());
        scanner.close(); //각 메서드의 역할은 이름 그대로임.

        //특정 폴더의 크기 출력하는 메서드 예제
        //kb, mb, gb 등을 크기에 맞게 출력하기 위한 convertFileLength 메서드도 만든다.
        String path = "C:\\GodofJava";
        long sum = printFileSize(path);
        System.out.println(path+"'s total size="+convertFileLength(sum));
    }

    private static long printFileSize(String path) {
        File file = new File(path);
        long sum = 0;
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File filetmp:files){
                if(filetmp.isFile()){
                    long size = filetmp.length();
                    //System.out.println(filetmp+"="+convertFileLength(size));
                    sum+=size;
                }
                else{
                    long size = printFileSize(filetmp.getAbsolutePath());
                    System.out.println(filetmp+"="+convertFileLength(size));
                    sum+=size;
                }
            }
        }
        return sum;
    }

    private static String convertFileLength(long size) {
        DecimalFormat df = new DecimalFormat("0.00");
        if(size<=1024){
            return df.format(1.0*size)+" b";
        }
        else if(size<=1024*1024) return df.format(1.0*size/1024)+" kb";
        else if(size<=1024*1024*1024) return df.format(1.0*size/(1024*1024))+" mb";
        else return df.format(1.0*size/(1024*1024*1024))+" gb";
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
