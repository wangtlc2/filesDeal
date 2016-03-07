package com.util;

import java.io.File;

public class FileUtil4Create {
	/**
	 * 创建文件
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-25 上午6:34:08
	 * 
	 * @param fileFullPath
	 *            如："D:\\hello.txt"
	 */
	public static void createNew(String fileFullPath) {
		File f = new File(fileFullPath);
		try {
			f.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建文件夹
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-25 上午6:35:50
	 * 
	 * @param fileFullPath
	 *            如:"D:"+File.separator+"hello"
	 */
	public static void createNewDir(String fileFullPath) {
		File f = new File(fileFullPath);
		f.mkdir();

	}

}
