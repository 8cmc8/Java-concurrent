package threadSafe.synchronize;

/**
 * 内置锁的重入
 * （线程可以多次获得线程本身已经持有的锁）
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        LoggingWidget loggingWidget = new LoggingWidget();
        loggingWidget.doSomething();
    }
}

class Widget {
    public synchronized void doSomething() {
        System.out.println("父类中的this:" + this);
    }
}

class LoggingWidget extends Widget {
    @Override
    public synchronized void doSomething() {
        //通过子类对象引用继承自父类的方法，其中的synchronized方法获取的锁对象仍是子类对象
        //如果内置锁是不可重入的，此处将产生死锁
        super.doSomething();
        System.out.println("子类中的this:" + this);
    }
}
