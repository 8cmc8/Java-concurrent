package threadSafe.raceCondition;

import threadSafe.ThreadSafe;

import java.util.concurrent.atomic.AtomicLong;

@ThreadSafe
public class CountingFactorizer {
    private final AtomicLong count = new AtomicLong(0);

    public long getCount() {
        return count.get();
    }

    public void service() {
        //原子性操作（不存在时序问题）
        count.incrementAndGet();
    }
}
