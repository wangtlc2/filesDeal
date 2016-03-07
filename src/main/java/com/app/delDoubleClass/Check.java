package com.app.delDoubleClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * 判断处理对没
 * @Package: com.app.delDoubleClass
 * @ClassName: Check
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-4-12 上午10:56:45
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 *
 * 修改日期    修改人    修改目的
 *
 */
public class Check {
	private static int count=1;

	public static void main(String[] args) {
		DoubleClassCheckUtil.init();
		doCheck();
	}
	
	private static void doCheck() {
		File file = new File("tocheck.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String lineContent = null;
			while ((lineContent = br.readLine()) != null) {
				String tmp=lineContent.substring(lineContent.lastIndexOf("/")+1);
				if (DoubleClassCheckUtil.results.contains(tmp)) {
				}else {
					System.out.println(count+++tmp+"有错误！");
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

	}
}
