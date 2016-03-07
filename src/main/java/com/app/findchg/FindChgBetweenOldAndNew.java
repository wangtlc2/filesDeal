package com.app.findchg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 比如Config.getValue变为ConfigUtil.getValue
 * 现只知道Config.getValue怎么才能找到ConfigUtil.getValue呢？
 * 算法：
 * 1.查找旧工程中出现Config.getValue的某些类
 * 2.在新工程中找出以上的类
 * 3.开发从新工程的类中对比一下即可
 * 
 * @Package: com.app.findchg
 * @ClassName: FindChgBetweenOldAndNew
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-4-23 下午9:38:03
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 *
 * 修改日期    修改人    修改目的
 *
 */
public class FindChgBetweenOldAndNew {

	public static void main(String[] args) {BufferedReader br = null;
	try {
		br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-web-sx/src/main/webapp/flashshow/flashshowinfo.jsp")), "UTF-8"));
		String lineContent = null;
		int i=0;
		while ((lineContent = br.readLine()) != null) {
			i++;
		}
		System.out.println(i);
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
	}}
}
