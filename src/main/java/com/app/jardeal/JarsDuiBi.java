package com.app.jardeal;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 对比两个目录下的JAR包区别

 * @Package: com.app
 * @ClassName: JarsDuiBi
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-4-12 上午10:56:11
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 *
 * 修改日期    修改人    修改目的
 *
 */
public class JarsDuiBi {
	static List<String> jar_sList;
	static List<String> jar_tList;
	static {
		jar_sList = new ArrayList<String>();
		jar_tList= new ArrayList<String>();
	}

	public static void main(String[] args) throws Exception {
		generate();
	}

	public static void generate() throws Exception {
		renderTemplateContent1(new FileInputStream(JarsDuiBi.class.getResource("jar_s.txt").getFile()));
		
		renderTemplateContent2(new FileInputStream(JarsDuiBi.class.getResource("jar_t.txt").getFile()));
		
		//批出S有的，T没有的
		for (int i = 0; i < jar_sList.size(); i++) {
			if (!jar_tList.contains(jar_sList.get(i))) {
				System.out.println(jar_sList.get(i));
			}
		}
	}
	public static void renderTemplateContent1(InputStream in) throws IOException {
		BufferedReader inBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String lineContent = null;
			while ((lineContent = inBuff.readLine()) != null) {
				jar_sList.add(lineContent.substring(lineContent.lastIndexOf("/") + 1));
			}

		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
		}

	}
	public static void renderTemplateContent2(InputStream in) throws IOException {
		BufferedReader inBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			String lineContent = null;
			while ((lineContent = inBuff.readLine()) != null) {
				jar_tList.add(lineContent.substring(lineContent.lastIndexOf(" ") + 1));
			}

		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
		}

	}
}
