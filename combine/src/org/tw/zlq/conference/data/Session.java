/**
 * 
 */
package org.tw.zlq.conference.data;

import org.tw.zlq.conference.error.ErrorCode;
import org.tw.zlq.conference.error.TwException;
import org.tw.zlq.core.Element;

/**
 * @author longqiang
 *
 */
public class Session extends Element implements Cloneable{
	private String time;
	private String title;
	private boolean isLightning;
	public Session(int index, String title, Integer cost) {
		super(index, cost);
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isLightning() {
		return isLightning;
	}
	public void setLightning(boolean isLightning) {
		this.isLightning = isLightning;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String toString() {
		if (!isLightning) {
			return String.format("%s %s %dmin", getTime(), getTitle(), getValue());
		}
		return String.format("%s %s lightning", getTime(), getTitle());
	}
	
	public Session clone() {
		try {
			return (Session) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new TwException(ErrorCode.InnerErr, "Clone error.");
		}
	} 
}
