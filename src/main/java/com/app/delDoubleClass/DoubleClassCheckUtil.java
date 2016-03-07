package com.app.delDoubleClass;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * 重复类名的类列表
 * @Package: com.app.delDoubleClass
 * @ClassName: DoubleClassCheckUtil
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-4-12 上午10:56:33
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 *
 * 修改日期    修改人    修改目的
 *
 */
public class DoubleClassCheckUtil {
	private static List<String> fileNames = new ArrayList<String>();
	public static List<String> results=new ArrayList<String>();
	private static int count=1;
	public static void main(String[] args) {
		dealWithFilesInDirectory("Y:/workspace-stq/echd-telecom-uni/");
	}

	public static void init() {
		dealWithFilesInDirectory("Y:/workspace-stq/echd-telecom-uni/");		
	}

	public static void dealWithFilesInDirectory(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		for (File fl : files) {
			if (fl.isDirectory()) {
				dealWithFilesInDirectory(fl.toString());
			} else {
				if (dealTheFile(fl)) {
					if (fileNames.contains(fl.getName())) {
						System.out.println(count+++"重复" + fl.getAbsolutePath());
					} else {
						results.add(fl.getName());
						fileNames.add(fl.getName());
					}
				}
			}
		}
	}

	private static boolean dealTheFile(File fl) {
		if (fl.getName().lastIndexOf(".java") != -1 && fl.getAbsolutePath().indexOf(".svn") == -1) {
			return true;
		}
		return false;
	}

}
