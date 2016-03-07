package com.app.updatefile.v2.bean;

public class UpdateBean {
	public UpdateBean(String source, String target) {
		super();
		this.source = source;
		this.target = target;
	}

	public UpdateBean(String source, String target, String className4s, String className4t) {
		super();
		this.source = source;
		this.target = target;
		this.className4s = className4s;
		this.className4t = className4t;
	}

	/**
	 * 初始替换的源字符串：完整类路径
	 */
	private String source;
	/**
	 * 替换后的字符串：完整类路径
	 */
	private String target;

	/**
	 * 被替换的类名
	 */
	private String className4s;
	/**
	 * 替换后的新类名
	 */
	private String className4t;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getClassName4s() {
		return className4s;
	}

	public void setClassName4s(String className4s) {
		this.className4s = className4s;
	}

	public String getClassName4t() {
		return className4t;
	}

	public void setClassName4t(String className4t) {
		this.className4t = className4t;
	}

	@Override
	public String toString() {
		return "UpdateBean [source=" + source + ", target=" + target + ", className4s=" + className4s + ", className4t=" + className4t + "]";
	}

}
