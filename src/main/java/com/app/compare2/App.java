package com.app.compare2;

import java.io.File;

import lombok.extern.log4j.Log4j;

import com.app.compare.FileCompareStrategy;
import com.app.compare.FileCompareStrategyByFileContent;

@Log4j
public class App {
	static FileCompareStrategy fileCompareStrategy = new FileCompareStrategyByFileContent();
	// 生产环境取下的
	static File productDir = new File("Y:/BaiduMusic/echd-chinamobile-web-sx-1.0.0-SNAPSHOT/");
	// maven
	static File mavenDir = new File("Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-web-sx/src/main/webapp/");

	public static void main(String[] args) throws Exception {
		log.info("开始处理");
		workInDir(productDir);//遍历生产的，看和MAVEN中是否一样
		log.info("处理完毕");
	}

	public static void workInDir(File file) throws Exception {
		File[] files = file.listFiles();
		for (File productFile : files) {
			if (productFile.isDirectory()) {// 遇到目录
				workInDir(productFile);
			} else {// 文件
				if (!productFile.getName().endsWith(".jsp")) {
//					log.info("该文件不是JSP,跳过:"+productFile.getName());
					continue;
				}
				File mavenFile = chgMavenFile(productFile);
//				log.info("处理:"+mavenFile);
				if (!mavenFile.exists()) {
					log.info("文件不存在："+productFile);
				}else if (fileCompareStrategy.isEqualsTwoFile(mavenFile, productFile)) {// 如果在本地存在且相等则不处理，否则拷贝
					// 不处理
				} else {
					log.info("发现文件不一致："+productFile);
				}
			}
		}
	}
	
	private static File chgMavenFile(File productFile) {
		return new File(mavenDir + "/" + productFile.getAbsolutePath().replace(productDir.getAbsolutePath(), ""));
	}
}
