<<<<<<< HEAD
package com.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 线程配置
 * @author smile
 *
 */
@Getter
@Setter
public class ThreadConfig {

	private static final int DEFAULT_TRISE = 5;
	private static final int DEFAULT_SIZE = 100;
	/** 2-4秒 */
	//private static final Long DEFAULT_SLEEPTIME = (long) ((2 + Math.random() * 2) * 1000);
	private static final int DEFAULT_MAXTHREAD = 30;
	private String path; // 下载保存路径
	private int size; // 每个线程下载章节数
	private int tries;// 下载尝试次数
	/** 最大线程个数 */
	private int maxThread;
	private Long sleepTime;

	public ThreadConfig() {
		this.size = DEFAULT_SIZE;
		this.tries = DEFAULT_TRISE;
		this.sleepTime = (long) 100;
		this.maxThread = DEFAULT_MAXTHREAD;
	}
}
=======
package com.entity;

<<<<<<< HEAD
=======
import java.io.Serializable;
import java.util.List;
import java.util.Map;

>>>>>>> second commit
import lombok.Getter;
import lombok.Setter;

/**
 * 线程配置
 * @author smile
 *
 */
@Getter
@Setter
<<<<<<< HEAD
public class ThreadConfig {
=======
public class ThreadConfig <T, ID extends Serializable>{
>>>>>>> second commit

	private static final int DEFAULT_TRISE = 5;
	private static final int DEFAULT_SIZE = 100;
	/** 2-4秒 */
<<<<<<< HEAD
	//private static final Long DEFAULT_SLEEPTIME = (long) ((2 + Math.random() * 2) * 1000);
	private static final int DEFAULT_MAXTHREAD = 30;
=======
	private static final Long DEFAULT_SLEEPTIME = (long) ((2 + Math.random() * 2) * 1000);
	private static final int DEFAULT_MAXTHREAD = 10;
>>>>>>> second commit
	private String path; // 下载保存路径
	private int size; // 每个线程下载章节数
	private int tries;// 下载尝试次数
	/** 最大线程个数 */
	private int maxThread;
	private Long sleepTime;

	public ThreadConfig() {
		this.size = DEFAULT_SIZE;
		this.tries = DEFAULT_TRISE;
		this.sleepTime = (long) 100;
<<<<<<< HEAD
		this.maxThread = DEFAULT_MAXTHREAD;
	}
=======
		//this.sleepTime = DEFAULT_SLEEPTIME;
		this.maxThread = DEFAULT_MAXTHREAD;
	}
	
	public int getMaxThread(List<T> list) {
		int max=(int) Math.ceil(list.size() * 1.0 / size);
		return max ;
	}
	
	public <S extends T> Map<String, List<T>> splitTask(List<T> list,Map<String, List<T>> tasks) {
		int maxThreadSize = getMaxThread(list);
		for (int i = 0; i < maxThreadSize; i++) {
			// 每个线程开始的章节数
			int fromIndex = i * size;
			// 每个线程结束的章节数
			int toIndex = i == maxThreadSize - 1 ? list.size() : fromIndex + size;
			tasks.put((fromIndex + 1) + "-" + toIndex , list.subList(fromIndex, toIndex));
		}
		return tasks;
	}



	
>>>>>>> second commit
}
>>>>>>> second commit
