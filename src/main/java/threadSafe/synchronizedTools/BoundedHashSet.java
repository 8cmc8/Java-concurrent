package threadSafe.synchronizedTools;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 使用Semaphore为容器设置边界
 * @param <T>
 */
public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<>());
        //通过构造函数指定许可量
        this.sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        //获得一个许可，获取不到则阻塞直到有许可
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            //如果add未添加任何元素，则立即释放许可
            if (!wasAdded) {
                sem.release();
            }
        }
    }

    public boolean remove(Object o) {
        boolean wasRemoved = set.remove(o);
        //删除元素，则释放许可
        if (wasRemoved) {
            sem.release();
        }
        return wasRemoved;
    }
}
