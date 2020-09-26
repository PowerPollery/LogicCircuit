package logic_circuit.tools.util;
/**
 * 线程池
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool {
	/*获取无限制(缓冲功能)的线程池*/
	private static ThreadPoolExecutor cachedThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	/*新建线程*/
	public static void newThread(Runnable r) {
		cachedThreadPool.execute(r);
	}
	/*程序结束时要关闭*/
	public static void shutdown() {
		cachedThreadPool.shutdown();
	}
	/*得到活跃线程的数量*/
	public static int getThreadCount() {
		return cachedThreadPool.getActiveCount();
	}
}
