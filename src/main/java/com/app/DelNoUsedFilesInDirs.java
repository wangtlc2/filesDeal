package com.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.util.FilesInDirsUtil;

/**
 * 从生产拷贝下来的JSP包里有很多是没用的JSP，备份的内容，这些都要先删除再提交SVN
 * 
 * @Package: com.app
 * @ClassName: DelNoUsedFilesInDirs
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-4-17 上午10:15:53
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 * 
 *           修改日期 修改人 修改目的
 * 
 */
public class DelNoUsedFilesInDirs {

	private static Logger logger = Logger.getLogger(DelNoUsedFilesInDirs.class);

	private static String jspDir = "y:/workspace-stq-new-sx/所有分支maven化改造资料/山西maven化资料/0530合并/result_97_0530/sxechn5/";

	public static void main(String[] args) throws IOException {
		logger.info("开始处理文件!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		List<File> files = new ArrayList<File>();
		files = FilesInDirsUtil.findFilesByPostfixInDir(jspDir, "-1", files);
		for (int i = 0; i < files.size(); i++) {// 逐个处理JSP文件
			if (noUse(files.get(i))) {
				logger.info("删除文件：" + files.get(i).getName()+"\t"+files.get(i).getAbsolutePath());
				files.get(i).delete();
			}
		}
		logger.info("文件处理完毕!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		logger.info("开始处理文件夹!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		files.clear();
		files = FilesInDirsUtil.findDirsByDirNameInDir(jspDir, "@", files);
		for (int i = 0; i < files.size(); i++) {// 逐个处理JSP文件
			logger.info("删除文件夹：" + files.get(i).getName()+"\t"+files.get(i).getAbsolutePath());
			FileUtils.deleteDirectory(files.get(i));
		}
		logger.info("文件夹处理完毕!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		logger.info("开始处理目录!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		// 处理目录
		files.clear();
		List<String> toDelFileNameList = iniToDelFileNameList();
		files = FilesInDirsUtil.findDirsByDirNameInDir(jspDir, toDelFileNameList, files);
		for (int i = 0; i < files.size(); i++) {// 逐个处理JSP文件
			logger.info("清空目录：" + files.get(i).getName());
			FileUtils.cleanDirectory(files.get(i));//清空就行了，不要删除，因为对帐和详单目录是必要的，只是里面的东东没用
		}
		logger.info("目录处理完毕!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}

	private static List<String> iniToDelFileNameList() {
		List<String> iniToDelFileNameList =new ArrayList<String>();
		iniToDelFileNameList.add("chinapayfiles");//临时-对帐目录
		iniToDelFileNameList.add("feedetail");//临时-详单目录
		iniToDelFileNameList.add("cms_bak");
		return iniToDelFileNameList;
	}

	private static boolean noUse(File file) {
		String fileName = file.getName();
		if (fileName.indexOf("@") != -1 || fileName.equals("cms_bak") 
				|| fileName.endsWith(".tar") || fileName.endsWith(".jar") || fileName.endsWith(".class") 
				|| fileName.endsWith(".bak")|| fileName.endsWith("_chenss") ||
				(fileName.indexOf(".jsp")!=-1 && fileName.substring(fileName.lastIndexOf(".")+1).length()>4)||
				(fileName.indexOf(".html")!=-1 && fileName.substring(fileName.lastIndexOf(".")+1).length()>4)) {
			//如果扩展名大于4就认为是JSP备份文件，后面跟着日期
			return true;
		}

		return false;
	}
}
