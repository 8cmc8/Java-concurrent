package threadSafe.raceCondition;

import threadSafe.NotThreadSafe;

/**
 * 存在竞态条件（延迟初始化）
 * 由于不恰当的执行时序而出现不正确的结果
 */
@NotThreadSafe
public class LazyInitRace {
    private Object instance = null;

    public Object getInstance() {
        //非原子性操作（先检查后执行）
        if (instance == null) {
            instance = new Object();
        }
        return instance;
    }
}
