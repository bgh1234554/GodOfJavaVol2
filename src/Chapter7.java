import javax.swing.plaf.nimbus.State;

class RunnableSample implements Runnable{
    @Override
    public void run(){
        System.out.println("Running Runnable Sample");
    }
}

class ThreadSample extends Thread{
    @Override
    public void run() {
        System.out.println("Running Thread Sample"+this.getName());
    }
}

class NameThread extends Thread{
    int calc;
    NameThread(String name, int calc){
        super(name);
        this.calc=calc;
    }
    @Override
    public void run(){
        calc++;
    }
}

class CommonCalculate{
    private int amount=0;
    public CommonCalculate(){}
    public void plus(int val){synchronized (this){amount+=val;}}
    public void minus(int val){synchronized (this) {amount -= val;}}
    public int getAmount() {return amount;}
}

class ModifyThread extends Thread{
    private CommonCalculate calc;
    private boolean addFlag;
    public ModifyThread(CommonCalculate calc,boolean addFlag){
        this.calc=calc; this.addFlag=addFlag;
    }
    @Override
    public void run(){
        for(int i=0;i<10000;i++){
            if(addFlag) calc.plus(1);
            else calc.minus(1);
        }
    }
}

class StateThread extends Thread{
    private Object monitor;
    public StateThread(Object monitor){
        this.monitor=monitor;
    }
    public void run(){
        try{
            for(int i=0;i<10000;i++){
                String string = "A";
            }
            synchronized (monitor){
                monitor.wait();
            }
            System.out.println("Monitor is notified");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Chapter7 {

    public static void main(String[] args) throws InterruptedException {
        /*
        스레드 실행 시키는 방법은 2가지 - implements Runnable, extends Thread를 통한 run() 메서드 구현.
        start() 메서드를 통해 시작 대기 상태로 만듬. 다중 상속이 안되기 떄문에 Runnable 메서드를 통한 구현이 존재함.
        JVM이 한 쓰레드가 끝나기 전에 다른 쓰레드를 실행하기 때문에 실행할때마다 먼저 끝나는 Thread가 달라진다.
         */
        runMultiThread();
        /*
        Thread Class 생성자 종류
        Thread()
        Thread(Runnable, name) - Thread-n이라는 기본 이름 외에 이름 지정 가능.
        Thread(ThreadGroup,Runnable,name,long stackSize)
        - ThreadGroup에 속하는 Runnable 객체의 run() 메서드를 수행, 이름 지정, 쓰레드 스택의 크기는 stackSize만큼만 가능
        (마지막 static 메서드 참조)
        (쓰레드 스텍 - 쓰레드 내에서 얼마나 많은 쓰레드가 처리되는지, 많은 메서드를 호출하는 지와 관계)

        당연히 쓰레드도 클래스기 때문에 원하는 인수를 받아서 멤버 변수에 넣을 수 있고, 생성자를 통해 이름을 지정할 수도 있다.
        sleep()안에 들어가는 숫자는 long형태의 밀리초임.

        Thread 메서드 중 priority를 설정할 수 있는 메서드가 있으나 사용하지 않는 것을 권장함.
        1부터 10까지 있으며, MAX_PRIORITY, NORM_PRIORITY, MIN_PRIORITY가 상수로 존재한다.

        데몬 쓰레드 - 부가적인 작업을 수행할때 사용. 모너티링하는 쓰레드라던가... setDaemon() 메서드 사용.

        synchronized 메서드가 없으면 데이터가 꼬일 수 있음.
        예제를 보면 더하기 연산을 수행하고 생긴 임시 결과를 amount에 대입하기 전에
        다른 스레드가 연산을 수행하면서 연산 결과가 반영되지 않는 결과가 발생한다
         */
        for(int i=0;i<5;i++) modifyTest();
        /*
        synchronized 키워드가 붙으면 이상이 없다. 늦게 온 스레드는 연산을 다 수행할 때까지 기다린다.
        -> 그런데 메서드 전체에 이를 사용하면 필요 없는 부분까지 감싸게 되므로, 인스턴스 값을 변경하는 블럭에서만
        synchronized(this){}블럭을 사용하면 효율적이다. this가 아니라 Object lock = new Object();
        와 같이 별도의 객체를 생성하여 처리할 때도 많다. 단순한 문지기 역할임.
        이때 여러 인스턴스에 각각 sync블럭이 있을 때, lock과 같은 문지기용 객체를 따로따로 선언해 줄 수 있다.

        물론 서로 같은 객체를 사용할 경우에만 블럭이 필요하고, 밑에서 calc 객체를 따로따로 지정해주면 필요가 없다.
        StringBuffer는 Thread Safe하고, StringBuilder는 Thread Safe하지 않기 때문에 상황에 맞춰서 사용하면 된다.
         */
        /*
        Thread 상태
        NEW -> 상태 -> TERMINATED
        RUNNABLE, BLOCKED, WAITING, TIEMD_WAITING이 존재한다. Thread State Diagram을 참조하면 편하다.
        join() -> 쓰레드가 종료될때까지 기다린다. 아니면 시간을 지정해줄 수도 있다.
        interrupt() -> InterruptedException 예외를 발생시키면서 쓰레드를 중단시킴.
         */
        Thread thread = Thread.currentThread(); //현재 쓰레드를 리턴함.
        boolean interrupted = Thread.interrupted(); //현재 쓰레드가 중지되었는지 확인함.
        //다른 쓰레드의 상태를 확인할때는 isInterrupted()나 isAlive() 사용.
        /*
        !!notify()와 wait() 메서드는 Object 클래스에 존재한다.
        Object 객체의 모니터에 존재하는 단일 쓰레드를 깨우거나 재우는 것이다.
         */
        try {
            checkThreadState();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /*
        ThreadGroup에서 제공하는 메서드
        ThreaGroup은 쓰레드의 관리를 위한 클래스이며, 기본적으로 운영체제의 폴더처럼 tree 구조를 가지고 있다.
        activeCount() - 실행중인 쓰레드의 개수를 리턴한다.
        enumerate(Thread[] or ThreadGroup[], boolean recurse)
        - 현재 쓰레드 그룹에 있는 모든 쓰레드/쓰레드 그룹을 매개변수로 들어온 배열에 담는다.
        recurse가 true일 경우 하위 쓰레드 그룹도 포함. 리턴값은 배열에 저장된 쓰레드의 개수임
        예제 코드 참조.
         */
        groupThread();
    }
    public static void runMultiThread(){
        RunnableSample []run = new RunnableSample[5];
        ThreadSample []thread = new ThreadSample[5];
        for(int i=0;i<5;i++){
            run[i] = new RunnableSample();
            thread[i]=new ThreadSample();
            new Thread(run[i]).start();
            thread[i].start();
        }
        System.out.println("End runMultiThread()");
    }
    public static void modifyTest(){
        CommonCalculate calc = new CommonCalculate();
        ModifyThread modifyThread1 = new ModifyThread(calc,true);
        ModifyThread modifyThread2 = new ModifyThread(calc,true);

        modifyThread1.start(); modifyThread2.start();
        try{
            modifyThread1.join(); modifyThread2.join();
            System.out.println(calc.getAmount());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void checkThreadState() throws InterruptedException {
        Object monitor=new Object();
        StateThread stateThread = new StateThread(monitor);
        System.out.println(stateThread.getState());
        stateThread.start();
        System.out.println(stateThread.getState());
        stateThread.sleep(100);
        System.out.println(stateThread.getState());
        synchronized (monitor){
            monitor.notify();
        }
        Thread.sleep(100);
        System.out.println(stateThread.getState());
        stateThread.join();
        System.out.println(stateThread.getState());
    }
    public static void groupThread() throws InterruptedException {
        Object monitor = new Object();
        StateThread state1=new StateThread(monitor);
        StateThread state2=new StateThread(monitor);
        ThreadGroup threadGroup=new ThreadGroup("Group1");
        Thread thread1 = new Thread(threadGroup,state1); //Thread(ThreadGroup,Runnable)
        Thread thread2 = new Thread(threadGroup,state2);
        thread1.start(); thread2.start();
        Thread.sleep(1000);
        System.out.println(threadGroup.getName());
        System.out.println(threadGroup.activeCount());
        threadGroup.list();

        Thread[] tmp = new Thread[threadGroup.activeCount()];
        int result = threadGroup.enumerate(tmp);
        System.out.println(result);
        for(Thread thread:tmp) {
            System.out.println(thread);
        }
        synchronized (monitor){
            monitor.notifyAll();
        }
    }
}
