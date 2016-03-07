package com.app.delDoubleClass.bean;

import java.util.ArrayList;
import java.util.List;

public class ToCheckBean {
	/**
	 * 工程标识，如：core,uni,impl,web
	 */
	private String name;
	////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * 类路径，如：Y:/workspace-stq/peb_core/target/classes/
	 */
	private String path;
	/**
	 * 保存结果类LIST
	 */
	private List<ClassNameInfoBean> fileNameInfoList=new ArrayList<ClassNameInfoBean>();
	////////////////////////////////////////////////////////////////////////////////////
	/**
	 * JAVA文件路径，如：Y:/workspace-stq/peb_core/src/main/java
	 */
	private String javaPath;
	/**
	 * 保存JAVALIST
	 */
	private List<JavaNameInfoBean> javaNameInfoList=new ArrayList<JavaNameInfoBean>();
	////////////////////////////////////////////////////////////////////////////////////	

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<ClassNameInfoBean> getFileNameInfoList() {
		return fileNameInfoList;
	}

	public void setFileNameInfoList(List<ClassNameInfoBean> fileNameInfoList) {
		this.fileNameInfoList = fileNameInfoList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJavaPath() {
		return javaPath;
	}

	public void setJavaPath(String javaPath) {
		this.javaPath = javaPath;
	}

	public List<JavaNameInfoBean> getJavaNameInfoList() {
		return javaNameInfoList;
	}

	public void setJavaNameInfoList(List<JavaNameInfoBean> javaNameInfoList) {
		this.javaNameInfoList = javaNameInfoList;
	}

	public ToCheckBean(String name, String path, String javaPath) {
		super();
		this.name = name;
		this.path = path;
		this.javaPath = javaPath;
	}

}
