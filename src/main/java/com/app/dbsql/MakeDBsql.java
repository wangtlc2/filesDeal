package com.app.dbsql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.dbsql.bean.SqlEntity;
import com.util.FileContentUtil;

/**
 * 根据表名、序列名生成上线脚本
 * 
 * @Package: com.app.dbsql
 * @ClassName: MakeDBsql
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-4-22 下午4:25:17
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 * 
 *           修改日期 修改人 修改目的
 * 
 */
public class MakeDBsql {
	private static Logger logger = Logger.getLogger(MakeDBsql.class);

	static List<SqlEntity> sqlEntityList;
	//原SQL文件目录
	static String filePathPre;
	//结果文件
	static File resultFile;
	// 表所属用户
	private static String tableUserOwn;
	// 表目标用户
	private static String tableUserTarget;

	private static StringBuffer tableSqlSpace;

	private static List<String> multTablePreList = new ArrayList<String>();

	static {
		filePathPre = "Y:\\stq-new-doc\\所有分支maven化改造资料\\山西maven化资料\\3、数据库相关\\";
		resultFile = new File(filePathPre + "all_sql.txt");
		if (resultFile.exists()) {
			resultFile.delete();
		}
		tableUserOwn = "DBECHNADM";
		tableUserTarget = "DBECHNRUN";
		sqlEntityList = new ArrayList<SqlEntity>();

		//		sqlEntityList.add(new SqlEntity("TD_PTL_FUNCCODE_REL", true, false, new File(filePathPre + "建表SQL-其它-权限表 - TD_PTL_FUNCCODE_REL.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("TD_PTL_ROLE", true, false, new File(filePathPre + "建表SQL-其它-权限表 - TD_PTL_ROLE.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("TD_PTL_ROLE_FUNC", true, false, new File(filePathPre + "建表SQL-其它-权限表 - TD_PTL_ROLE_FUNC.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("TD_PTL_FUNCCODE", true, false, new File(filePathPre + "建表SQL-其它-权限表-TD_PTL_FUNCCODE.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("TD_PTL_PAGE_INFO", true, false, new File(filePathPre + "建表SQL-其它-权限表-TD_PTL_PAGE_INFO.txt"), resultFile));
		//
		//		sqlEntityList.add(new SqlEntity("TW_PTL_SERVOPT_LOG", true, true, new File(filePathPre + "建表SQL-日志-操作日志表-TW_PTL_SERVOPT_LOG.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("TW_PTL_ACCESSLOG_LOG", true, true, new File(filePathPre + "建表SQL-日志-访问日志-TW_PTL_ACCESSLOG_LOG.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("TW_PTL_ACCESSPEAK_LOG", true, true, new File(filePathPre + "建表SQL-日志-峰值表-TW_PTL_ACCESSPEAK_LOG.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("TW_PTL_SERVINVOKE_LOG", true, true, new File(filePathPre + "建表SQL-日志-接口日志-TW_PTL_SERVINVOKE_LOG.txt"), resultFile));
		//
		//		sqlEntityList.add(new SqlEntity("SEQ_TW_PTL_SERVICE", false, false, new File(filePathPre + "建表SQL-日志-操作日志表-TW_PTL_SERVOPT_LOG - SEQ.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("SEQ_TW_PTL_ACCESSLOG", false, false, new File(filePathPre + "建表SQL-日志-访问日志-TW_PTL_ACCESSLOG_LOG - SEQ.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("SEQ_TW_PTL_ACCESSPEAKLOG", false, false, new File(filePathPre + "建表SQL-日志-峰值表-TW_PTL_ACCESSPEAK_LOG - SEQ.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("SEQ_TW_PTL_INVOKE", false, false, new File(filePathPre + "建表SQL-日志-接口日志-TW_PTL_SERVINVOKE_LOG - SEQ.txt"), resultFile));

		sqlEntityList.add(new SqlEntity("SEQ_OBH_ECHN_ORDER_CHOOSE_NO", false, false, new File(filePathPre + "SEQ_OBH_ECHN_ORDER_CHOOSE_NO.txt"), resultFile));
		sqlEntityList.add(new SqlEntity("TD_OBH_ECHN_ORDER_CHOOSE_NO", true, false, new File(filePathPre + "TD_OBH_ECHN_ORDER_CHOOSE_NO.txt"), resultFile));

		sqlEntityList.add(new SqlEntity("SEQ_OBH_ECHN_ORDER_MAIN", false, false, new File(filePathPre + "SEQ_OBH_ECHN_ORDER_MAIN.txt"), resultFile));
		sqlEntityList.add(new SqlEntity("TD_OBH_ECHN_ORDER_MAIN", true, false, new File(filePathPre + "TD_OBH_ECHN_ORDER_MAIN.txt"), resultFile));

		sqlEntityList.add(new SqlEntity("SEQ_OBH_ECHN_ORDER_PACKAGE", false, false, new File(filePathPre + "SEQ_OBH_ECHN_ORDER_PACKAGE.txt"), resultFile));
		sqlEntityList.add(new SqlEntity("TD_OBH_ECHN_ORDER_PACKAGE", true, false, new File(filePathPre + "TD_OBH_ECHN_ORDER_PACKAGE.txt"), resultFile));

		sqlEntityList.add(new SqlEntity("SEQ_OBH_ECHN_ORDER_REAL_INFO", false, false, new File(filePathPre + "SEQ_OBH_ECHN_ORDER_REAL_INFO.txt"), resultFile));
		sqlEntityList.add(new SqlEntity("TD_OBH_ECHN_ORDER_REAL_INFO", true, false, new File(filePathPre + "TD_OBH_ECHN_ORDER_REAL_INFO.txt"), resultFile));

		sqlEntityList.add(new SqlEntity("SEQ_OBH_ECHN_ORDER_SHOP_001", false, false, new File(filePathPre + "SEQ_OBH_ECHN_ORDER_SHOP_001.txt"), resultFile));
		sqlEntityList.add(new SqlEntity("TD_OBH_ECHN_ORDER_SHOP_001", true, false, new File(filePathPre + "TD_OBH_ECHN_ORDER_SHOP_001.txt"), resultFile));

		//		sqlEntityList.add(new SqlEntity("TW_PTL_USERLOGIN_LOG", true, true, new File(filePathPre + "建表SQL-日志-登录日志表-TW_PTL_USERLOGIN_LOG.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("TW_PTL_ACCESSLOG_LOG", true, true, new File(filePathPre + "建表SQL-日志-访问日志-TW_PTL_ACCESSLOG_LOG.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("TW_PTL_ACCESSPEAK_LOG", true, true, new File(filePathPre + "建表SQL-日志-峰值表-TW_PTL_ACCESSPEAK_LOG.txt"), resultFile));
		//		sqlEntityList.add(new SqlEntity("TW_PTL_SERVINVOKE_LOG", true, true, new File(filePathPre + "建表SQL-日志-接口日志-TW_PTL_SERVINVOKE_LOG.txt"), resultFile));
	}

	private static void initMultTablePreList() {
		//		multTablePreList.add("201304");
		//		multTablePreList.add("201305");
		multTablePreList.add("201306");
		multTablePreList.add("201307");
		multTablePreList.add("201308");
		multTablePreList.add("201309");
		multTablePreList.add("201310");
		multTablePreList.add("201311");
		multTablePreList.add("201312");
	}

	private static void initTableSqlSpace() {
		tableSqlSpace = new StringBuffer();
		tableSqlSpace.append(FileContentUtil.getContentFromFile(new MakeDBsql(), "tableSqlSpace4shenchan.txt"));
		//		tableSqlSpace.append(FileContentUtil.getContentFromFile(new MakeDBsql(), "tableSqlSpace4local.txt"));

	}

	/**
	 * 入口
	 */
	public static void main(String[] args) throws IOException {
		try {
			initMultTablePreList();
			initTableSqlSpace();
			generate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("生成完毕！");

	}

	/**
	 * 生成新文件
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-22 下午4:48:33
	 * 
	 * @throws Exception
	 */
	public static void generate() throws Exception {
		for (int i = 0; i < sqlEntityList.size(); i++) {
			SqlEntity sqlEntity = sqlEntityList.get(i);
			logger.info("处理：" + sqlEntity);
			if (sqlEntity.isMult()) {// 分表
				for (int j = 0; j < multTablePreList.size(); j++) {
					sqlEntity.setMultTmpObjName(sqlEntity.getObject() + multTablePreList.get(j));// 重置分表名
					doGenerateTheObject(sqlEntity);
				}
			} else {
				doGenerateTheObject(sqlEntity);
			}
		}

	}

	private static void doGenerateTheObject(SqlEntity sqlEntity) throws IOException {
		StringBuffer result = getCreateTableSql(sqlEntity);
		FileContentUtil.writeToFile(sqlEntity.getNewSqlFile(), result.toString(), true);
	}

	/**
	 * 生成新文件内容
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-22 下午4:48:49
	 * 
	 * @param sqlEntity
	 * @return
	 */
	private static StringBuffer getCreateTableSql(SqlEntity sqlEntity) {
		BufferedReader br = null;
		StringBuffer result = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(sqlEntity.getOldSqlFile())));
			String lineContent = null;
			while ((lineContent = br.readLine()) != null) {
				if (sqlEntity.isTable() && sqlEntity.isMult() && lineContent.indexOf("{") != -1 && lineContent.indexOf("}") != -1) {
					String tableNameT = lineContent.substring(lineContent.indexOf('{'), lineContent.indexOf('}') + 1);
					lineContent = lineContent.replace(tableNameT, sqlEntity.getMultTmpObjName());
				}
				result.append(lineContent + FileContentUtil.CHANGE_LINE);
			}
			result.append(getNewContent4TableSqlSpace(sqlEntity));
			result.append(getNewContent4GrantSql(sqlEntity));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 生成新内容
	 */
	private static String getNewContent4TableSqlSpace(SqlEntity sqlEntity) {
		String result = "";
		if (sqlEntity.isTable()) {
			result += tableSqlSpace.toString();
		}

		return result;
	}

	/**
	 * 生成新内容
	 */
	private static String getNewContent4GrantSql(SqlEntity sqlEntity) {
		String result = "";
		if (sqlEntity.isTable()) {
			if (sqlEntity.isMult()) {
				result += "CREATE OR REPLACE PUBLIC SYNONYM " + sqlEntity.getMultTmpObjName() + " FOR " + tableUserOwn + "."
						+ sqlEntity.getMultTmpObjName() + ";" + FileContentUtil.CHANGE_LINE;
				result += "GRANT DELETE, INSERT, SELECT, UPDATE ON " + tableUserOwn + "." + sqlEntity.getMultTmpObjName() + " TO " + tableUserTarget
						+ ";" + FileContentUtil.CHANGE_LINE;
			} else {
				result += "CREATE OR REPLACE PUBLIC SYNONYM " + sqlEntity.getObject() + " FOR " + tableUserOwn + "." + sqlEntity.getObject() + ";"
						+ FileContentUtil.CHANGE_LINE;
				result += "GRANT DELETE, INSERT, SELECT, UPDATE ON " + tableUserOwn + "." + sqlEntity.getObject() + " TO " + tableUserTarget + ";"
						+ FileContentUtil.CHANGE_LINE;
			}
		} else {
			result += "CREATE OR REPLACE PUBLIC SYNONYM " + sqlEntity.getObject() + " FOR " + tableUserOwn + "." + sqlEntity.getObject() + ";"
					+ FileContentUtil.CHANGE_LINE;
			result += "GRANT SELECT ON " + tableUserOwn + "." + sqlEntity.getObject() + " TO " + tableUserTarget + ";" + FileContentUtil.CHANGE_LINE;
		}
		return result;
	}
}
