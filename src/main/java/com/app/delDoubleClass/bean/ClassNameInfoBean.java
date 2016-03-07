package com.app.delDoubleClass.bean;

public class ClassNameInfoBean {
	/**
	 * 类名，包括内部类名
	 */
	private String className;
	private String classFullName;
	private String classFullPath;
	/**
	 * 比如UNI包下、IMPL包下、WEB包下
	 */
	private String pathType;
	/**
	 * 是否内部类
	 */
	private boolean neiBuLei;


	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPathType() {
		return pathType;
	}

	public String getClassFullPath() {
		return classFullPath;
	}

	public void setClassFullPath(String classFullPath) {
		this.classFullPath = classFullPath;
	}

	public void setPathType(String pathType) {
		this.pathType = pathType;
	}

	public boolean isNeiBuLei() {
		return neiBuLei;
	}

	public void setNeiBuLei(boolean neiBuLei) {
		this.neiBuLei = neiBuLei;
	}

	public String getClassFullName() {
		return classFullName;
	}

	public void setClassFullName(String classFullName) {
		this.classFullName = classFullName;
	}

	public ClassNameInfoBean(String className, String classFullName, String classFullPath, String pathType, boolean neiBuLei) {
		super();
		this.className = className;
		this.classFullName = classFullName;
		this.classFullPath = classFullPath;
		this.pathType = pathType;
		this.neiBuLei = neiBuLei;
	}


	@Override
	public String toString() {
//		return "ClassNameInfoBean [ pathType=" + pathType + ", classFullName=" + classFullName + ", classFullPath=" + classFullPath +", neiBuLei=" + neiBuLei + "]";
		return "ClassNameInfoBean [ pathType=" + pathType + ", classFullPath=" + classFullPath +"]";
	}

}
