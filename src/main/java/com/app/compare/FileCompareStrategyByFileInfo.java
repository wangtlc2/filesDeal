package com.app.compare;

import java.io.File;

import lombok.extern.log4j.Log4j;
@Log4j
public class FileCompareStrategyByFileInfo implements FileCompareStrategy {

	@Override
	public boolean isEqualsTwoFile(File sourceFile, File targetFile) throws Exception {
		log.debug("进行文件比较,源文件:" + sourceFile + "目标文件:" + targetFile);
		if (sourceFile.isFile() && targetFile.isFile() && sourceFile.getName().equalsIgnoreCase(targetFile.getName())// 比文件名
				&& sourceFile.length() == targetFile.length()// 比长度
				&& sourceFile.lastModified() == targetFile.lastModified()) {// 比修改日期.
			// 如果文件名、长度、修改日期不同则肯定不同
			return true;
		}
		return false;
	}
}
