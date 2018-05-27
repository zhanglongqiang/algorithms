package org.tw.zlq.conference.error;

public enum ErrorCode {
	InputErr("0001", "Input data error."),
	InnerErr("0000", "Inner error."),
	NoSolutionErr("0009", "No possible solution found.");
	private String desc;
	private String code;
	ErrorCode(String code, String description){
		this.code = code;
		this.desc = description;
	}
	public String getDesc() {
		return desc;
	}
	public String getCode() {
		return code;
	}
}
