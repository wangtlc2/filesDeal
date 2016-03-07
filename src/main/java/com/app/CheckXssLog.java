package com.app;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.apache.commons.io.FileUtils;

@Log4j
public class CheckXssLog {
	public static void main(String[] args) throws IOException {
		List<String> lines=FileUtils.readLines(new File("Y:\\BaiduYunDownload\\xssfilter_report.log.2014-02-21"),"GBK");
		for (int i = 0; i < lines.size(); i++) {
			String tmp4line = lines.get(i);
			if (tmp4line.contains("校验不通过")||tmp4line.contains("xss防攻击拦截url:")) {
				log.info(tmp4line);
			}else {
//				log.info(tmp4line);
			}
		}
	}
}
