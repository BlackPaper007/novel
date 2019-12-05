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
