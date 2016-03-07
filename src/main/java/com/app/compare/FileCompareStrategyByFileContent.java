package com.app.compare;

import java.io.File;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

@Log4j
public class FileCompareStrategyByFileContent implements FileCompareStrategy {
	static int count = 1;

	@Override
	public boolean isEqualsTwoFile(File mavenFile, File productFile) throws Exception {
		log.debug("进行文件比较,源文件:" + mavenFile + "目标文件:" + productFile);
		List<String> fileContents_mavenFile = FileUtils.readLines(mavenFile);
		List<String> fileContents_productFile = FileUtils.readLines(productFile);
		//		if (fileContents_1.size()!=fileContents_2.size()) {
		//			log.error("发现不一致：【大小不同】"+targetFile+","+sourceFile);
		//			return false;
		//		}
		for (int i = 0; i < fileContents_mavenFile.size(); i++) {
//			log.info("进行对比:"+fileContents_mavenFile.get(i).trim()+"\n"+fileContents_productFile.get(i).trim());
			if (!StringUtils.equals(fileContents_mavenFile.get(i).trim(), fileContents_productFile.get(i).trim())) {
//				if (!ignore(fileContents_mavenFile.get(i).trim(),fileContents_productFile.get(i).trim())) {
//				}
				log.error("\n\n\n发现不一致【" + count++ + "】：【内容不同】\n" 
						+ productFile + "\n " + fileContents_mavenFile.get(i) + "\n" 
						+ mavenFile + "\n " +  fileContents_productFile.get(i));
				return false;
				
			}
		}
		return true;//通过
	}

	private static boolean ignore(String content_mavenFile,String content_prodFile) {
//		if (content_mavenFile.contains("page import")
//				|| content_mavenFile.contains("sessionInfoKey")
//				|| (content_mavenFile.contains("UniConstant") && content_prodFile.contains("Constant"))
//				|| content_prodFile.contains(";\"%>")) {//import="com.sitech.echn.service.card.webchoosenos.model.SLstAvailableNoOut.AvailableNoInfo;"%>
//			return true;
//		}
		return false;
	}
}
