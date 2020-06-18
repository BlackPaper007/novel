<<<<<<< HEAD
package com.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 章节实体
 * @author smile
 *
 */
@Getter
@Setter
@ToString
public class Chapter implements Serializable{

	private static final long serialVersionUID = 7300213980585379474L;
	
	private String title;
	private String oriUrl;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oriUrl == null) ? 0 : oriUrl.hashCode());
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
		Chapter other = (Chapter) obj;
		if (oriUrl == null) {
			if (other.oriUrl != null)
				return false;
		} else if (!oriUrl.equals(other.oriUrl))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
=======
package com.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 章节实体
 * @author smile
 *
 */
@Getter
@Setter
@ToString
public class Chapter implements Serializable{

	private static final long serialVersionUID = 7300213980585379474L;
	
	private String title;
	private String oriUrl;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oriUrl == null) ? 0 : oriUrl.hashCode());
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
		Chapter other = (Chapter) obj;
		if (oriUrl == null) {
			if (other.oriUrl != null)
				return false;
		} else if (!oriUrl.equals(other.oriUrl))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
>>>>>>> second commit
