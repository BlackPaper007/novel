package com.impl.novels.job;

import java.util.concurrent.Callable;

import com.entity.Novel;
import com.impl.abs.AbstractSpider;

public class BaseJob extends AbstractSpider implements Callable<Novel>{

	protected String url;
	protected String name;
	
	
	public BaseJob(String url, String name) {
		super();
		this.url = url;
		this.name = name;
	}

	@Override
	public Novel call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
