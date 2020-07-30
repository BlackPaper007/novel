package com.entity;


import java.util.Date;

import lombok.Data;

@Data
public class Novel {
    private Integer id;
    /** 书名 */
    private String name;
    /** 作者 */
    private String author;
    /** 原链接 */
    private String url;
    /** 书籍类别 */
    private String type;
    /** 简介 */
    private String info;
    /** 最新章节 */
    private String latelychapter;
    /** 最新章节链接 */
    private String latelychapterurl;
    /** 最新更新时间 */
    private Date latelytime;
    /** 是否完结 1连载 2完结 */
    private Integer status;
    /** 平台id 根据枚举类 */
    private Integer platformId;

}