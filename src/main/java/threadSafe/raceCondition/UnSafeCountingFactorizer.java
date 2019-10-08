package threadSafe.raceCondition;

import threadSafe.NotThreadSafe;

/**
 * 存在竞态条件（递增）
 * 由于不恰当的执行时序而出现不正确的结果
 */
@NotThreadSafe
public class UnSafeCountingFactorizer {
    private long count = 0;

    public long getCount() {
        return count;
    }

    public void service() {
        //非原子性操作（读取-修改-写入）
        ++count;
    }
}
