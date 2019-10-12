package threadSafe.synchronizedTools;

import threadSafe.ThreadSafe;

import java.util.concurrent.CountDownLatch;

/**
 * 使用CountDownLatch实现闭锁
 * CountDownLatch.await()方法在倒计数非0时将阻塞线程
 * 闭锁可以确保某些活动直到其他活动都完成后才能继续
 */
public class TestHarness {
    //测试n个线程并发执行某个任务时需要的时间
    public static long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        //启动门将使主线程同时释放所有工作线程
        final CountDownLatch startGate = new CountDownLatch(1);
        //结束门使得主线程等待所有工作线程执行完成才继续执行
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        //确保每个线程执行完，倒计数减1
                        endGate.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }

        long start = System.nanoTime();
        //启动门倒计数减1（为0），释放所有阻塞的工作线程
        startGate.countDown();
        //主线程阻塞，直到结束门倒计数减为0
        endGate.await();

        long end = System.nanoTime();
        return end - start;
    }


    public static void main(String[] args) throws InterruptedException {
        //测试100个线程并发执行一个myTask任务的用时
        long time = timeTasks(100, new myTask());
        System.out.println(time);
    }
}

class myTask implements Runnable {
    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            sum += i;
        }
    }
}
