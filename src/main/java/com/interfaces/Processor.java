package com.interfaces;

public interface Processor {
	
	public void insertNovel(String site);
	
	public void updateNovel(String site);
	
	/**
	 * 根据数据库里的数据和所支持的站点更新书籍
	 * @param site 支持的站点
	 */
	public void merge(String site);

}
