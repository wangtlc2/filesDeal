package com.util;

import java.io.File;

public class FileUtil4Del {
	/**
	 * 删除文件
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-25 上午6:34:08
	 * 
	 * @param fileFullPath
	 *            如："D:\\hello.txt"
	 */
	public static void createNew(String fileFullPath) {
		File f = new File(fileFullPath);
		if (f.exists()) {
			f.delete();
		} else {
			System.out.println("文件不存在");
		}

	}

}
