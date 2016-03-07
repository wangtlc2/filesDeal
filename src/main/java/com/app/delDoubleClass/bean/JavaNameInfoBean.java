package com.app.delDoubleClass.bean;

import java.io.File;

import com.util.PathUtil;

public class JavaNameInfoBean {
	/**
	 * 比如UNI包下、IMPL包下、WEB包下
	 */
	private String pathType;
	/**
	 * java
	 */
	private File javaFile;
	
	public File getJavaFile() {
		return javaFile;
	}

	public void setJavaFile(File javaFile) {
		this.javaFile = javaFile;
	}


	public String getPathType() {
		return pathType;
	}

	public void setPathType(String pathType) {
		this.pathType = pathType;
	}


	@Override
	public String toString() {
		//将文件路径转换
		return "JavaNameInfoBean [pathType=" + pathType + ", javaFile=" + PathUtil.chgAbsPath2ClassPath(javaFile.getAbsolutePath()) + "]";
	}

	public JavaNameInfoBean(String pathType, File javaFile) {
		super();
		this.javaFile = javaFile;
		this.pathType = pathType;
	}

}
