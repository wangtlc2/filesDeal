package com.app.compare3;

import java.io.File;

import lombok.extern.log4j.Log4j;

import com.app.compare.FileCompareStrategy;
import com.app.compare.FileCompareStrategyByFileContent;

/**
 * <pre>
 * 移动总部二期：
 * 对比DEV分支和RM分支的代码差异，对比结果：
 * 文件/目录  是否差异		差异值
 * XXX.JAVA  有               DEV有，RM无
 * XXX.JAVA  有               DEV和RM内容不一致
 * 
 * </pre>
 * @author wangtlc 
 * @date 2016年3月7日 下午2:30:50
 *
 * 修改日期    修改人    修改目的
 *
 */
@Log4j
public class App {
	static FileCompareStrategy fileCompareStrategy = new FileCompareStrategyByFileContent();
	static File productDir = new File("E:/workspace-stq-all/chinamobile-jt/chinamobile-jt-dev/web-jt");
	static File mavenDir = new File("E:/workspace-stq-all/chinamobile-jt/echd-chinamobile-jt-prod/web-jt");

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
