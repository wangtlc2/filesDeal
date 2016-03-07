package com.app.delDoubleClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.delDoubleClass.bean.ClassNameInfoBean;
import com.app.delDoubleClass.bean.JavaNameInfoBean;
import com.app.delDoubleClass.bean.ToCheckBean;
import com.util.FileContentUtil;
import com.util.FilesInDirsUtil;

/**
 * 重复类名的类列表
 * 
 * @Package: com.app.delDoubleClass
 * @ClassName: DoubleClassCheckUtil
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-4-12 上午10:56:33
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 * 
 *           修改日期 修改人 修改目的
 * 
 */
public class DoubleClassCheckUtilV2 {
	static Logger logger = Logger.getLogger(DoubleClassCheckUtilV2.class);

	/**
	 * 配置项
	 */
	private static List<ToCheckBean> toCheckBeanList;
	
	/**
	 * WEB应用下所有JSP，由于只有一个应用所以就单独搞了
	 */
	private static List<File> webJspList=new ArrayList<File>();
	
	private static String webRoot="Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-web-sx/src/main/webapp/";
	
	static {
		toCheckBeanList = new ArrayList<ToCheckBean>();
		toCheckBeanList.add(new ToCheckBean("peb_core", "Y:/workspace-stq-new-sx/peb_core/target/classes/", "Y:/workspace-stq-new-sx/peb_core/src/main/java/"));
		toCheckBeanList.add(new ToCheckBean("uni", "Y:/workspace-stq-maven-other/echd-chinamobile-uni-wangxjd/bin/", "Y:/workspace-stq-maven-other/echd-chinamobile-uni-wangxjd/src/main/java/"));
//		toCheckBeanList.add(new ToCheckBean("impl", "Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-impl-sx/target/classes/", "Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-impl-sx/src/main/java/"));
//		toCheckBeanList.add(new ToCheckBean("web", "Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-web-sx/target/classes/", "Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-web-sx/src/main/java/"));
	}

	/**
	 * 保存所有类名全集
	 */
	static List<String> allClassNameList = new ArrayList<String>();

	/**
	 * 初始化目录下的文件
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-19 上午9:17:59
	 *
	 */
	private static void iniFiles() {
		for (int i = 0; i < toCheckBeanList.size(); i++) {// 遍历每个工程
			ToCheckBean toCheckBean = toCheckBeanList.get(i);

			FilesInDirsUtil.findFileNamesInDir(toCheckBean.getName(), toCheckBean.getPath(), toCheckBean.getFileNameInfoList());// 初始化每个工程下所有CLASS文件
			logger.info("报告：在" + toCheckBean.getPath() + "下找到类文件数：" + toCheckBean.getFileNameInfoList().size());

			FilesInDirsUtil.findJavaFileNamesInDir(toCheckBean.getName(), toCheckBean.getJavaPath(), toCheckBean.getJavaNameInfoList());// 初始化每个工程下所有JAVA文件
			logger.info("报告：在" + toCheckBean.getJavaPath() + "下找到JAVA文件数：" + toCheckBean.getJavaNameInfoList().size());
		}
//		FilesInDirsUtil.findFilesByPostfixInDir(webRoot,".jsp", webJspList);// 初始化WEB下所有JSP文件
	}

	/**
	 * 进行处理
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-19 上午8:55:13
	 * 
	 */
	private static void dealFiles() {

		for (int i = 0; i < toCheckBeanList.size(); i++) {// 遍历每个工程
			ToCheckBean toCheckBean = toCheckBeanList.get(i);

			List<ClassNameInfoBean> fileNameInfoList = toCheckBean.getFileNameInfoList();
			for (int j = 0; j < fileNameInfoList.size(); j++) {// 遍历该工程中的所有类名
				if (allClassNameList.contains(fileNameInfoList.get(j).getClassName())) {
					// 输出问题
//					System.out.println(fileNameInfoList.get(j).getClassName());
					layOut(fileNameInfoList.get(j));
				} else {
					allClassNameList.add(fileNameInfoList.get(j).getClassName());
				}
			}
		}
	}

	static int count;

	/**
	 * 输出结果
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-19 上午8:53:48
	 * 
	 * @param className
	 */
	private static void layOut(ClassNameInfoBean classNameInfoBean) {
		List<ClassNameInfoBean> temp=new ArrayList<ClassNameInfoBean>();
		boolean isPebcore=false;
		boolean isUni=false;
		for (int i = 0; i < toCheckBeanList.size(); i++) {// 在每个工程中检查
			ToCheckBean toCheckBean = toCheckBeanList.get(i);
			List<ClassNameInfoBean> fileNameInfoList = toCheckBean.getFileNameInfoList();
			for (int j = 0; j < fileNameInfoList.size(); j++) {// 遍历该工程中的所有类名
				if (fileNameInfoList.get(j).getClassName().equals(classNameInfoBean.getClassName())) {
					if (fileNameInfoList.get(j).getPathType().equals("peb_core")) {
						isPebcore=true;
					}
					if (fileNameInfoList.get(j).getPathType().equals("uni")) {
						isUni=true;
					}
//					logger.info(fileNameInfoList.get(j));
					temp.add(fileNameInfoList.get(j));
//					findTheClassCalledByJavaFiles(fileNameInfoList.get(j));
//					findTheClassCalledByJspFiles(fileNameInfoList.get(j));
				}
			}
		}
		if (isUni && isPebcore) {
			logger.info("【第" + (++count) + "个重复类】=====================================================================================================");
			for (int i = 0; i < temp.size(); i++) {
				logger.info(temp.get(i));
			}
		}
	}

	private static void findTheClassCalledByJspFiles(ClassNameInfoBean classNameInfoBean) {
		boolean isAlsoOut=false;
		for (int i = 0; i < webJspList.size(); i++) {
			String tmp=classNameInfoBean.getClassFullPath();
			tmp=tmp.substring(0, tmp.indexOf(".class"));
			if (FileContentUtil.isContainsContentInTargetFile(tmp, webJspList.get(i))) {
				if (!isAlsoOut) {
					logger.info("开始：以下是引用该类的所有JSP文件--------------------------------------------------------------------------------------");
					isAlsoOut=true;
				}
				logger.info("该类在JSP文件出现:"+webJspList.get(i).getAbsolutePath());
			}
		}
		if (isAlsoOut) {
			logger.info("结束：以上是引用该类的所有JSP文件--------------------------------------------------------------------------------------");
		}else {
			logger.info("没有引用该类的JSP文件--------------------------------------------------------------------------------------");
		}
	}

	private static void findTheClassCalledByJavaFiles(ClassNameInfoBean classNameInfoBean) {
		boolean isAlsoOut=false;
		for (int i = 0; i < toCheckBeanList.size(); i++) {// 在每个工程中检查
			List<JavaNameInfoBean> javaNameInfoList=toCheckBeanList.get(i).getJavaNameInfoList();
			for (int j = 0; j < javaNameInfoList.size(); j++) {
				String tmp=classNameInfoBean.getClassFullPath();
				tmp=tmp.substring(0, tmp.indexOf(".class"));
				if (FileContentUtil.isContainsContentInTargetFile(tmp, javaNameInfoList.get(j).getJavaFile())) {
					if (!isAlsoOut) {
						logger.info("开始：以下是引用该类的所有JAVA文件--------------------------------------------------------------------------------------");
						isAlsoOut=true;
					}
					logger.info("该类在JAVA文件出现:"+javaNameInfoList.get(j));
				}
			}
		}
		if (isAlsoOut) {
			logger.info("结束：以上是引用该类的所有JAVA文件--------------------------------------------------------------------------------------");
		}else {
			logger.info("没有引用该类的JAVA文件--------------------------------------------------------------------------------------");
		}
	}

	public static void main(String[] args) {
		iniFiles();
		dealFiles();
	}

}
