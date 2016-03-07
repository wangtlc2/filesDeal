package com.util;

import java.io.File;

public class FileUtil4List {
	/**
	 * 列出指定目录的全部文件=包括隐藏文件
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-25 上午6:34:08
	 * 
	 * @param fileFullPath
	 *            如："D:\\"
	 */
	public static void listAll(String fileFullPath) {
		File f = new File(fileFullPath);
		String[] str = f.list();
		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
		}

	}

	public static void listAllFile(String fileFullPath) {
		File f = new File(fileFullPath);
		File[] str = f.listFiles();
		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
		}
	}

	/**
	 * 判断一个指定的路径是否为目录
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-25 上午7:33:58
	 * 
	 * @param fileFullPath
	 * @return
	 */
	public static boolean judgeIsDir(String fileFullPath) {
		File f = new File(fileFullPath);
		if (f.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 列出指定目录的全部内容
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-25 上午7:33:40
	 * 
	 * @param f
	 */
	public static void print(File f) {
		if (f != null) {
			if (f.isDirectory()) {
				File[] fileArray = f.listFiles();
				if (fileArray != null) {
					for (int i = 0; i < fileArray.length; i++) {
						// 递归调用
						print(fileArray[i]);
					}
				}
			} else {
				System.out.println(f);
			}
		}
	}

}
