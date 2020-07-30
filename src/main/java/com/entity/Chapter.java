package com.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 章节实体
 * @author smile
 *
 */
@Data
public class Chapter implements Serializable{

	private static final long serialVersionUID = 7300213980585379474L;
	
	private String title;
	private String oriUrl;
	
}