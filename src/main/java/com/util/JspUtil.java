package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.apache.log4j.Logger;
@Log4j
public class JspUtil {

	/**
	 * 在指定的JSP文件中提取import的内容
	 *
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-3-21 下午9:37:40
	 *
	 * @param file
	 * @return 可能返回多个值，因为可能引用了多个类
	 */
	public static List<String> getClassPathFromJspFile(File file) {
		List<String> result=new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String lineContent = null;
			while ((lineContent = br.readLine()) != null) {
				if (lineContent.indexOf("import") != -1 && lineContent.indexOf("@") != -1 
						&& lineContent.indexOf("css.css")==-1 && lineContent.indexOf("import.jsp")==-1) {// 只处理JSP
					int start = lineContent.indexOf("\"", lineContent.indexOf(" import") + 1);// import前面要加个空格，否则会把带import的单词的给混淆进来
					int end = lineContent.indexOf("\"", start + 1);
					try {
						result.add(lineContent.substring(start + 1, end).replace(".", "/").replace(";", ""));
						log.info("在JSP"+file.getName()+"中找到IMPORT："+lineContent.substring(start + 1, end).replace(".", "/").replace(";", ""));
					} catch (StringIndexOutOfBoundsException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
