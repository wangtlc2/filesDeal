package com.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.app.updatefile.v2.OnlyDelWithReadAndWrite;
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
public class CheckBadImportInJsp {
	private static Logger logger = Logger.getLogger(CheckBadImportInJsp.class);

	private static String jspDir = "Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-web-sx/src/main/webapp/";
	
	public static void main(String[] args) {
		logger.info("开始执行！");
		List<File> files=new ArrayList<File>();
		files=FilesInDirsUtil.findFilesByPostfixInDir(jspDir,".jsp",files);
		for (int i = 0; i < files.size(); i++) {//逐个处理JSP文件
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(files.get(i)));
				String lineContent = null;
				while ((lineContent = br.readLine()) != null) {
					if (lineContent.indexOf("import") != -1 && lineContent.indexOf("@") != -1 
							&& lineContent.indexOf("css.css")==-1 && lineContent.indexOf("import.jsp")==-1
							&& lineContent.indexOf(";")!=-1 ) {// 只处理JSP
						logger.info("该JSP【"+files.get(i)+"】的import有问题："+lineContent);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("处理完毕！结果如下：");
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
