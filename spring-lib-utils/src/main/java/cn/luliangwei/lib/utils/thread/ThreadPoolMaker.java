package cn.luliangwei.lib.utils.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池生成器.
 * 
 * @author zhd
 * @since 1.0.0
 */
public enum ThreadPoolMaker {
    INSTANCE;
    private int cpuNum = Runtime.getRuntime().availableProcessors();
    private long memSize = Runtime.getRuntime().totalMemory();
    private int minPoolSize = 3 * cpuNum;
    private int maxPoolSize = (int) ((memSize * 0.05 / 1048576) * cpuNum);
    private int keepAliveTime = 5;
    private TimeUnit keepAliveTimeUnit = TimeUnit.SECONDS;
    private int queueCapacity = 2 * maxPoolSize;

    private ThreadPoolExecutor pool = new ThreadPoolExecutor(minPoolSize, maxPoolSize, keepAliveTime, keepAliveTimeUnit,
            new ArrayBlockingQueue<Runnable>(queueCapacity), new ThreadPoolExecutor.DiscardOldestPolicy());

    /**
     * 异步执行线程池中的一条任务.
     * 
     * @param runnable
     *            需要执行的一条具体的任务.
     * @author zhd
     * @since 1.0.0
     */
    public void execute(Runnable runnable) {
        pool.execute(runnable);
    }
    
    /**
     * 异步执行线程池中的多条任务.
     *
     * @param runnables 需要执行的多条具体的任务.
     * @author zhd
     * @since 1.0.0
     */
    public void execute(Runnable... runnables) {
        for (Runnable runnable : runnables) {
            pool.execute(runnable);
        }
    }

    /**
     * 异步执行线程池中多次执行同一条任务.
     *
     * @param repeatNum 任务执的次数
     * @param runnable 需要执行 <code>repeatNum</code> 次的一条具体的任务
     * @author zhd
     * @since 1.0.0
     */
    public void multipleExecute(int repeatNum, Runnable runnable) {
        for (int i = 0; i < repeatNum; i++) {
            execute(runnable);
        }
    }
    
    /**
     * 异步执行线程池中多次执行同多条任务.
     *
     * @param repeatNum 任务执的次数
     * @param runnables 需要执行 <code>repeatNum</code> 多条具体的任务
     * @author zhd
     * @since 1.0.0
     */
    public void multipleExecute(int repeatNum, Runnable... runnables) {
        for (int i = 0; i < repeatNum; i++) {
            execute(runnables);
        }
    }

    /**
     * 配置最小的线程池大小.
     *
     * @param minPoolSize
     *            最小的线程池大小
     * @return 重新配置的自定义线程池生成器
     * @author zhd
     * @since 1.0.0
     */
    public ThreadPoolMaker minPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
        pool.setCorePoolSize(minPoolSize);
        return this;
    }

    /**
     * 配置最大的线程池大小.
     *
     * @param maxPoolSize
     *            最大的线程池大小
     * @return 重新配置的自定义线程池生成器
     * @author zhd
     * @since 1.0.0
     */
    public ThreadPoolMaker maxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        pool.setMaximumPoolSize(maxPoolSize);
        return this;
    }

    /**
     * 配置新线程分配的等待秒数.
     *
     * @param keepAliveTimeSeconds
     *            等待秒数
     * @return 重新配置的自定义线程池生成器
     * @author zhd
     * @since 1.0.0
     */
    public ThreadPoolMaker keepAliveTimeSeconds(int keepAliveTimeSeconds) {
        this.keepAliveTime = keepAliveTimeSeconds;
        pool.setKeepAliveTime(keepAliveTimeSeconds, TimeUnit.SECONDS);
        return this;
    }
}
