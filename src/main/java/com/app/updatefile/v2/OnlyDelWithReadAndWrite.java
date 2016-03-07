package com.app.updatefile.v2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.util.FilesInDirsUtil;

/**
 * 批量删除文件中指定行
 * 
 * @Package: com.app.updatefile
 * @ClassName: WithReadAndWrite
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-4-12 上午10:36:53
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 *
 * 修改日期    修改人    修改目的
 *
 */
public class OnlyDelWithReadAndWrite {
	static Logger logger = Logger.getLogger(OnlyDelWithReadAndWrite.class);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 待处理的目录
	 */
	static String toDealDir = "Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-web-sx/src/main/java/";
	/**
	 * 待处理的文件后缀
	 */
	static String postfix=".java";
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * 待替换的行
	 */
	static List<String> toDealLineContentList = new ArrayList<String>();
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * 初始化配置参数
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-12 上午10:19:11
	 * 
	 */
	private static void init() {
		logger.info("初始化配置参数--开始");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(OnlyDelWithReadAndWrite.class.getResource("todellist.txt").getFile())));
			String lineContent = null;
			while ((lineContent = br.readLine()) != null) {
				toDealLineContentList.add(lineContent);
				logger.info(lineContent);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br!=null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("初始化配置参数--完毕");
	}

	public static void main(String[] args) throws IOException {
		init();
		doDeal();
		logger.info("处理完毕！");
	}

	/**
	 * 进行处理
	 *
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-12 上午10:24:35
	 *
	 * @throws IOException
	 */
	private static void doDeal() throws IOException {
		List<File> toDealFilesList = new ArrayList<File>();

		FilesInDirsUtil.findFilesByPostfixInDir(toDealDir, postfix, toDealFilesList);

		logger.info("准备对【"+toDealFilesList.size()+"】文件处理");
		for (int i = 0; i < toDealFilesList.size(); i++) {
			List<String> resultContextList = new ArrayList<String>();
			readFromFile(resultContextList, toDealFilesList.get(i));
			if (resultContextList.size()>0) {//有待处理的内容
				writeBackIntoFile(resultContextList, toDealFilesList.get(i));
			}
		}
	}

	/**
	 * 读取文件
	 *
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-12 上午10:23:49
	 *
	 * @param resultContextList
	 * @param file
	 */
	private static void readFromFile(List<String> resultContextList, File file) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String lineContent = null;
			while ((lineContent = br.readLine()) != null) {
				if (ifUsed(lineContent)) {
					resultContextList.add(lineContent);
//					logger.info("读入行：" + lineContent);
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

	/**
	 * 该行是否保留
	 *
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-12 上午10:43:44
	 *
	 * @param lineContent
	 * @return
	 */
	private static boolean ifUsed(String lineContent) {
		for (int i = 0; i < toDealLineContentList.size(); i++) {
			if(lineContent.indexOf(toDealLineContentList.get(i)) != -1 && lineContent.indexOf("*/")==-1){
				//且不能包含*/，这种的是注释的，如果干掉，可能没有封闭了，而且注释的先不管
				return false;
			}
		}
		return true;
	}

	/**
	 * 回写文件
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-12 上午10:20:25
	 * 
	 * @param resultContextList
	 * @param file
	 * @throws IOException
	 */
	private static void writeBackIntoFile(List<String> resultContextList, File file) throws IOException {
		logger.info("回写文件："+file);
		BufferedWriter bw = null;
		try {

			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			for (int i = 0; i < resultContextList.size(); i++) {
				bw.write(resultContextList.get(i) + "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}
}
