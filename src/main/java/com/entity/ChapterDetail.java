package com.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 章节内容实体
 * @author smile
 *
 */
@Data
public class ChapterDetail implements Serializable{

	private static final long serialVersionUID = 7768769897503900297L;

	private String title;
	private String content;
	private String prev;
	private String next;
	
}
