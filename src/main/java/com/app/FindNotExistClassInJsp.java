package com.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.util.FilesInDirsUtil;
import com.util.JspUtil;
/**
 * 提取JSP中引用了不存在的类的JSP文件列表
 * @Package: com
 * @ClassName: FindNotExistClassInJsp
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-3-21 下午9:28:47
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 *
 * 修改日期    修改人    修改目的
 *
 */
public class FindNotExistClassInJsp {
	private static Logger logger = Logger.getLogger(FindNotExistClassInJsp.class);
	private static Logger myrecord = Logger.getLogger("myrecord");
	private static String jspDir = "Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-web-sx/src/main/webapp/";
	
	//要定位CLASS，而不是JAVA，因为可能有内部类
	private static List<String> classDirList=new ArrayList<String>();
	
	private static Set<String> resultSet=new HashSet<String>();
	
	static{
		classDirList.add("Y:/workspace-stq-new-sx/peb_core/target/classes/");
		classDirList.add("Y:/workspace-stq-new-sx/echd-chinamobile-uni/target/classes/");
		classDirList.add("Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-impl-sx/target/classes/");
		classDirList.add("Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-web-sx/src/main/webapp/WEB-INF/classes/");
	}
	
	//白名单
	static List<String> whiteList=new ArrayList<String>();
	/**
	 * 初始化配置参数
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-12 上午10:19:11
	 * 
	 */
	private static void init() {
		logger.info("初始化配置参数--开始");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(FindNotExistClassInJsp.class.getResource("white.txt").getFile())));
			String lineContent = null;
			while ((lineContent = br.readLine()) != null) {
				if (!lineContent.startsWith("#")) {
					whiteList.add(lineContent);
					logger.info(lineContent);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br!=null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("初始化配置参数--完毕");
	}

	public static void main(String[] args) {
		init();
		logger.info("开始执行！");
		List<File> files=new ArrayList<File>();
		files=FilesInDirsUtil.findFilesByPostfixInDir(jspDir,".jsp",files);
		for (int i = 0; i < files.size(); i++) {//逐个处理JSP文件
			List<String> classPathList=JspUtil.getClassPathFromJspFile(files.get(i));
			for (int j = 0; j < classPathList.size(); j++) {//检查该JSP中引用的所有类
				String classNameFullPath=classPathList.get(j);
				logger.info(classNameFullPath.replace("/", "."));
				if (whiteList.contains(classNameFullPath.replace("/", "."))) {
					continue;
				}
				if (!isFrame(classNameFullPath)) {//只处理非框架类
					if (isImportMany(classNameFullPath)) {//带*的让手动检查
						if (!FilesInDirsUtil.judgeClassPackageExistInDirList(classDirList,classNameFullPath)) {//检查*上级路径，如果目录不存在则肯定不存在
							myrecord.error("不通过：【"+files.get(i).getAbsolutePath()+"】引用了不存在的类路径：【"+classNameFullPath.replace("/", ".")+"】");
						}else {//如果上级路径存在则由人工判断该JSP具体引用了*目录里的哪个类
							myrecord.error("告警：【"+files.get(i).getAbsolutePath()+"】引用了*，且该上级目录存在，所以请手动检查该JSP具体引用了哪些类：【"+classNameFullPath.replace("/", ".")+"】");
						}
						resultSet.add(classNameFullPath.replace("/", "."));
					}else if (!FilesInDirsUtil.judgeClassExistInDirList(classDirList,classNameFullPath)) {
						logger.error("不通过：【"+files.get(i).getAbsolutePath()+"】引用了不存在的类：【"+classNameFullPath.replace("/", ".")+"】");
						resultSet.add(classNameFullPath.replace("/", "."));
					}
				}
			}
		}
		logger.info("处理完毕！结果如下：");
		int i=0;
		for (Iterator iterator = resultSet.iterator(); iterator.hasNext();) {
			String tmp = (String) iterator.next();
			if (!FilesInDirsUtil.isWhite(tmp)) {
				myrecord.info("【"+(++i)+"个】"+tmp);
			}
		}
	}

	/**
	 * 根据类全路径判断是否是JDK或开源框架的类，因为这种类无法根据物理路径判断是否存在
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-3-21 下午10:07:44
	 *
	 * @param classNameFullPath
	 * @return
	 */
	private static boolean isFrame(String classNameFullPath) {
		if (!StringUtils.isEmpty(classNameFullPath) && (classNameFullPath.startsWith("java")
				|| classNameFullPath.startsWith("net") || classNameFullPath.startsWith("org"))) {
			return true;
		}else {
			//常规检查
			return false;
		}
	}
	
	/**
	 * 判断是否类全路径里带*，这种的也不太好通过路径判断，因为不太好判断JSP中到底用了哪个类
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-3-21 下午10:08:16
	 *
	 * @param classNameFullPath
	 * @return
	 */
	private static boolean isImportMany(String classNameFullPath) {
		if (classNameFullPath.indexOf("*")!=-1) {
			return true;
		}else {
			return false;
		}
	}
	
}
