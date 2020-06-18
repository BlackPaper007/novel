<<<<<<< HEAD
package com.entity;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 章节内容实体
 * @author smile
 *
 */
@Getter
@Setter
public class ChapterDetail implements Serializable{

	private static final long serialVersionUID = 7768769897503900297L;

	private String title;
	private String content;
	private String prev;
	private String next;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + ((prev == null) ? 0 : prev.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChapterDetail other = (ChapterDetail) obj;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (prev == null) {
			if (other.prev != null)
				return false;
		} else if (!prev.equals(other.prev))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ChapterDetail [title=" + title + ", content=" + StringUtils.abbreviate(content, 20) + ", prev=" + prev + ", next=" + next + "]";
	}
	
}
=======
package com.entity;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 章节内容实体
 * @author smile
 *
 */
@Getter
@Setter
public class ChapterDetail implements Serializable{

	private static final long serialVersionUID = 7768769897503900297L;

	private String title;
	private String content;
	private String prev;
	private String next;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + ((prev == null) ? 0 : prev.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChapterDetail other = (ChapterDetail) obj;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (prev == null) {
			if (other.prev != null)
				return false;
		} else if (!prev.equals(other.prev))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ChapterDetail [title=" + title + ", content=" + StringUtils.abbreviate(content, 20) + ", prev=" + prev + ", next=" + next + "]";
	}
	
}
>>>>>>> second commit
