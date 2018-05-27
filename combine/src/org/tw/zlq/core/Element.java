/**
 * 
 */
package org.tw.zlq.core;

/**
 * @author longqiang
 *
 */
public class Element {
	private int index;
	private Integer value;
	
	public Element(int index, Integer value) {
		super();
		this.index = index;
		this.value = value;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	@Override
	public int hashCode() {
		return index;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		if (index != other.index)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "<" + index + ", " + value + ">";
	}
}
