package com.app.dbsql.bean;

import java.io.File;

/**
 * 表名或序列名
 * 
 * @Package: com.app.dbsql.bean
 * @ClassName: SqlEntity
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-4-22 下午4:32:23
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 * 
 *           修改日期 修改人 修改目的
 * 
 */
public class SqlEntity {
	/**
	 * 经处理的表名或序列名：带分表后缀
	 */
	private String object;
	/**
	 * 分表名
	 */
	private String multTmpObjName;
	/**
	 * true:表 false:序列
	 */
	private boolean isTable;
	/**
	 * 原始建表SQL文件
	 */
	private File oldSqlFile;
	/**
	 * 目标文件
	 */
	private File newSqlFile;

	/**
	 * 是否是分表
	 */
	private boolean isMult;

	public SqlEntity(String object, boolean isTable, boolean isMult, File oldSqlFile, File newSqlFile) {
		super();
		this.object = object;
		this.isTable = isTable;
		this.isMult = isMult;
		this.oldSqlFile = oldSqlFile;
		this.newSqlFile = newSqlFile;
	}

	public boolean isMult() {
		return isMult;
	}

	public void setMult(boolean isMult) {
		this.isMult = isMult;
	}

	public File getOldSqlFile() {
		return oldSqlFile;
	}

	public void setOldSqlFile(File oldSqlFile) {
		this.oldSqlFile = oldSqlFile;
	}

	public File getNewSqlFile() {
		return newSqlFile;
	}

	public void setNewSqlFile(File newSqlFile) {
		this.newSqlFile = newSqlFile;
	}

	public boolean isTable() {
		return isTable;
	}

	public void setTable(boolean isTable) {
		this.isTable = isTable;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getMultTmpObjName() {
		return multTmpObjName;
	}

	public void setMultTmpObjName(String multTmpObjName) {
		this.multTmpObjName = multTmpObjName;
	}

}
