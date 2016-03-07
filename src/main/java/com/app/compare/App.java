package com.app.compare;

import java.io.File;

import org.apache.commons.io.FileUtils;

import lombok.extern.log4j.Log4j;

@Log4j
public class App {
	static FileCompareStrategy fileCompareStrategy = new FileCompareStrategyByFileInfo();
	// 本地文件或上次的生产环境包
	static File localRootDir = new File("y:\\tmp-to-del\\82\\");
	// 生产环境取下的
	static File newDownRootDir = new File("y:\\tmp-to-del\\97\\");
	// 比较svn比local多的内容
	static File resultRootDir = new File("Y:\\tmp-to-del\\82-97-result\\");

	public static void main(String[] args) throws Exception {
		if (resultRootDir.exists()) {
			FileUtils.cleanDirectory(resultRootDir);
		}else {
			resultRootDir.mkdir();
		}
		log.info("开始处理");
		workInDir(newDownRootDir);
		log.info("处理完毕");
	}

	public static void workInDir(File file) throws Exception {
		File[] files = file.listFiles();
		for (File newDownTmpFile : files) {
			if (newDownTmpFile.isDirectory()) {// 遇到目录
				File localFile = getLocalFile(newDownTmpFile);
				if (localFile.exists()) {// 目录存在则继续
					workInDir(newDownTmpFile);
				} else {// 否则深层拷贝
					log.info("进行拷贝==目录:" + newDownTmpFile.getAbsolutePath().replace(newDownRootDir.getAbsolutePath(), ""));
					FileUtils.copyDirectoryToDirectory(newDownTmpFile,getResultFile(newDownTmpFile).getParentFile());
				}
			} else {// 文件
				File localFile = getLocalFile(newDownTmpFile);
				if (localFile.exists() && fileCompareStrategy.isEqualsTwoFile(localFile, newDownTmpFile)) {// 如果在本地存在且相等则不处理，否则拷贝
					// 不处理
				} else {
					// 拷贝
					log.info("进行拷贝==" + (localFile.exists() ? "修改" : "新加") + "文件" + newDownTmpFile.getAbsolutePath().replace(newDownRootDir.getAbsolutePath(), ""));
					FileUtils.copyFile(newDownTmpFile, getResultFile(newDownTmpFile),true);
				}
			}
		}
	}

	
	private static File getLocalFile(File newDownFile) {
		return new File(localRootDir + "/" + newDownFile.getAbsolutePath().replace(newDownRootDir.getAbsolutePath(), ""));
	}

	private static File getResultFile(File newDownFile) {
		return new File(resultRootDir + "/" + newDownFile.getAbsolutePath().replace(newDownRootDir.getAbsolutePath(), ""));
	}
}
