package org.tw.zlq.conference.error;

/**
 * 自定义异常类
 */
public class TwException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -49268268046827761L;
	private ErrorCode errCode;
	private String errDetail;
	public TwException(ErrorCode code, String detail) {
		this.errCode = code;
		this.errDetail = detail;
	}
	
	public TwException(ErrorCode code, String detail, Throwable e) {
		super(e);
		this.errCode = code;
		this.errDetail = detail;
	}

	public ErrorCode getErrCode() {
		return errCode;
	}

	public String getErrDetail() {
		return errDetail;
	}
	
	public String getMessage() {
		return String.format("Code:%s, desc:%s, detail:%s", errCode.getCode(), errCode.getDesc(), errDetail);
	}
}
