package com.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.app.delDoubleClass.bean.ClassNameInfoBean;
import com.app.delDoubleClass.bean.JavaNameInfoBean;

/**
 * 文件操作
 * 
 * @Package: com
 * @ClassName: FileUtil
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-3-21 下午9:25:12
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 * 
 *           修改日期 修改人 修改目的
 * 
 */
public class FilesInDirsUtil {
	//排除的目录
	private static String exceptDirs = ".svn";

	private static Logger logger = Logger.getLogger(FilesInDirsUtil.class);

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * 在目录下查找某后缀名的所有文件
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-3-21 下午9:31:48
	 * 
	 * @param dir
	 * @param postfix
	 *            -1为所有文件
	 * @param resultList
	 * @return
	 */
	public static List<File> findFilesByPostfixInDir(String dir, String postfix, List<File> resultList, IntFileDealFilter fileDealFilter) {
		logger.debug("在目录" + dir + "下，查找文件后缀：" + postfix);
		File file = new File(dir);
		File[] files = file.listFiles();
		for (File fl : files) {
			if (fl.isDirectory()) {
				if (exceptDirs.indexOf(fl.getName()) == -1) {
					findFilesByPostfixInDir(fl.toString(), postfix, resultList, fileDealFilter);
				}
			} else {
				if (fl.getName().indexOf(postfix) != -1 || postfix.indexOf("-1") != -1) {
					if (fileDealFilter == null || (fileDealFilter != null && fileDealFilter.isPass(fl))) {
						logger.debug("找到文件：" + fl.getAbsolutePath());
						resultList.add(fl);
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * 在目录下查找某后缀名的所有文件
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-3-21 下午9:31:48
	 * 
	 * @param dir
	 * @param postfix
	 *            -1为所有文件
	 * @param resultList
	 * @return
	 */
	public static List<File> findFilesByPostfixInDir(String dir, String postfix, List<File> resultList) {
		logger.debug("在目录" + dir + "下，查找文件后缀：" + postfix);
		File file = new File(dir);
		File[] files = file.listFiles();
		for (File fl : files) {
			if (fl.isDirectory()) {
				if (exceptDirs.indexOf(fl.getName()) == -1) {
					findFilesByPostfixInDir(fl.toString(), postfix, resultList);
				}
			} else {
				if (fl.getName().indexOf(postfix) != -1 || postfix.indexOf("-1") != -1) {
					logger.debug("找到文件：" + fl.getAbsolutePath());
					resultList.add(fl);
				}
			}
		}
		return resultList;
	}

	/**
	 * 查找目录名
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-18 下午9:49:19
	 * 
	 * @param dir
	 * @param dirNameList
	 * @param resultList
	 * @return
	 */
	public static List<File> findDirsByDirNameInDir(String dir, List<String> dirNameList, List<File> resultList) {
		File file = new File(dir);
		File[] files = file.listFiles();
		for (File fl : files) {
			if (fl.isDirectory()) {
				if (dirNameList.contains(fl.getName())) {
					resultList.add(fl);
				}
				findDirsByDirNameInDir(fl.toString(), dirNameList, resultList);
			}
		}
		return resultList;
	}

	public static List<File> findDirsByDirNameInDir(String dir, String dirNames, List<File> resultList) {
		File file = new File(dir);
		File[] files = file.listFiles();
		for (File fl : files) {
			if (fl.isDirectory()) {
				String[] tmps = dirNames.split(",");
				for (int i = 0; i < tmps.length; i++) {
					if (fl.getName().indexOf(tmps[i]) != -1) {
						resultList.add(fl);
						break;
					}
				}
				findDirsByDirNameInDir(fl.toString(), dirNames, resultList);
			}
		}
		return resultList;
	}

	/**
	 * 在类路径下找类名
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-18 下午10:13:08
	 * 
	 * @param pathType
	 * @param path1
	 * @param fileNameInfoList
	 */
	public static void findFileNamesInDir(String pathType, String path1, List<ClassNameInfoBean> fileNameInfoList) {
		File file = new File(path1);
		File[] files = file.listFiles();
		for (File fl : files) {
			if (fl.isDirectory()) {
				if (exceptDirs.indexOf(fl.getName()) == -1) {//如果碰到有用的文件夹【即非.svn这种例外文件夹】就继续深入该文件夹
					findFileNamesInDir(pathType, fl.toString(), fileNameInfoList);
				}
			} else {
				String tmpFileName = fl.getName();
				if (tmpFileName.endsWith(".class")) {//在CLASS_PATH下找
					if (tmpFileName.indexOf("$") != -1) {//内部类，如：FlowCardOut$FlowCardRecharge.class
						String tmpFull = tmpFileName;
						tmpFileName = tmpFileName.substring(tmpFileName.indexOf("$") + 1);
						if (StringUtils.isNumeric(tmpFileName.substring(0, tmpFileName.indexOf(".")))) {//如果内部类名为数字则忽略
							continue;
						}
						fileNameInfoList.add(new ClassNameInfoBean(tmpFileName, tmpFull, fl.getPath().substring(fl.getParent().indexOf("\\classes\\") + 9).replace("\\", "."), pathType, true));
					} else {
						fileNameInfoList.add(new ClassNameInfoBean(tmpFileName, tmpFileName, fl.getPath().substring(fl.getParent().indexOf("\\classes\\") + 9).replace("\\", "."), pathType, false));
					}
				}
			}
		}
	}

	public static void findJavaFileNamesInDir(String pathType, String path1, List<JavaNameInfoBean> javaFileNameInfoList) {
		File file = new File(path1);
		File[] files = file.listFiles();
		for (File fl : files) {
			if (fl.isDirectory()) {
				if (exceptDirs.indexOf(fl.getName()) == -1) {//如果碰到有用的文件夹【即非.svn这种例外文件夹】就继续深入该文件夹
					findJavaFileNamesInDir(pathType, fl.toString(), javaFileNameInfoList);
				}
			} else {
				String tmpFileName = fl.getName();
				if (tmpFileName.endsWith(".java")) {//在JAVA_PATH下找
					javaFileNameInfoList.add(new JavaNameInfoBean(pathType, fl));
				}
			}
		}
	}

	public static String chgSubClass(String tmp) {
		int lastIndex = tmp.lastIndexOf('/');
		String pre = tmp.substring(0, lastIndex);
		String end = tmp.substring(tmp.lastIndexOf('/')).substring(1);
		return pre + "$" + end;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 判断CLASS是否物理存在
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-3-21 下午9:31:22
	 * 
	 * @param classDir
	 * @param classNameFullPath
	 * @return
	 */
	public static boolean judgeClassFileExistInDir(String classDir, String classNameFullPath) {
		File file = new File(classDir + classNameFullPath + ".class");

		if (file.exists()) {
			return true;
		} else {
			//判断是否是内部类
			if (new File(classDir + chgSubClass(classNameFullPath) + ".class").exists()) {
				logger.info("找到内部类:" + classNameFullPath);
				return true;
			}
			return false;
		}
	}

	public static boolean isWhite(String className) {
		if (className.contains("hisun")) {
			return true;
		}
		if (className.contains("umpay")) {
			return true;
		}
		if (className.contains("crmpd")) {
			return true;
		}
		if (!className.contains("com")) {
			return true;
		}
		if (className.contains(".jar.")) {
			return true;
		}
		if (className.contains(".sso2.")) {
			return true;
		}
		if (className.contains(".activemq.")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断文件classNameFullPath是否在classDirList列表的子目录里
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-3-21 下午9:46:26
	 * 
	 * @param classDirList
	 * @param classNameFullPath
	 * @return
	 */
	public static boolean judgeClassExistInDirList(List<String> classDirList, String classNameFullPath) {
		for (int i = 0; i < classDirList.size(); i++) {
			if (judgeClassFileExistInDir(classDirList.get(i), classNameFullPath)) {
				return true;
			}
		}
		return false;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 判断目录classNameFullPath是否在classDir存在
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-18 下午9:49:37
	 * 
	 * @param classDir
	 * @param classNameFullPath
	 * @return
	 */
	public static boolean judgeDirExistInDir(String classDir, String classNameFullPath) {
		File file = new File(classDir + classNameFullPath);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断classNamePackageFullPath在classDirList目录的子目录是否存在
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-18 下午9:49:53
	 * 
	 * @param classDirList
	 * @param classNamePackageFullPath
	 *            带*的路径，如：com.sitech.crmpd.core.wtc.util.*
	 * @return
	 */
	public static boolean judgeClassPackageExistInDirList(List<String> classDirList, String classNamePackageFullPath) {
		for (int i = 0; i < classDirList.size(); i++) {//com.sitech.crmpd.core.wtc.util.*  转换为  com.sitech.crmpd.core.wtc.util
			if (judgeDirExistInDir(classDirList.get(i), classNamePackageFullPath.substring(0, classNamePackageFullPath.indexOf("*") - 1))) {
				return true;
			}
		}
		return false;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////	

}
