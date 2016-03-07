package com.app;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.apache.commons.io.FileUtils;

@Log4j
public class CheckBadInvoke {
	
	public static void main(String[] args) throws Exception {
		Collection<File> toCheckFiles=FileUtils.listFiles(new File("Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-impl-sx/src/main/java/com/sitech"), new String[]{"java"}, true);
		for (Iterator<File> iterator = toCheckFiles.iterator(); iterator.hasNext();) {
			File toCheckFile = (File) iterator.next();
			doCheckFile(toCheckFile);
		}
	}

	/**
	 * 检测某接口调用类是否有缺失
	 */
	private static void doCheckFile(File toCheckFile) throws IOException {
		List<String> lines=FileUtils.readLines(toCheckFile,"GBK");
		boolean isInvoke=false;
		boolean isSet=false;
		for (int i = 0; i < lines.size(); i++) {
			String tmp4line = lines.get(i);
			if (tmp4line.contains("Digester") ) {
				isInvoke=true;
			} 
			if (tmp4line.contains("ROOT/RETURN_CODE") ) {
				isSet=true;
			}
		}
		if (isInvoke&&!isSet) {
			log.error("检测到异常文件："+toCheckFile);
		}
	}
}
