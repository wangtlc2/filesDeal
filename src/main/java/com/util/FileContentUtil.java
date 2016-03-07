package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 文件内容处理类
 * @Package: com.util
 * @ClassName: FileContentUtil
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-4-19 上午9:20:44
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 *
 * 修改日期    修改人    修改目的
 *
 */
public class FileContentUtil {
	/**
	 * 换行符
	 */
	public final static String CHANGE_LINE="\r\n";
	
	private static Logger logger=Logger.getLogger(FileContentUtil.class);
	
	/**
	 * 检查content行是否在javaFile文件中存在
	 *
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-19 上午9:22:40
	 *
	 * @param content
	 * @param targetFile
	 */
	public static boolean isContainsContentInTargetFile(String content,File targetFile) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(targetFile)));
			String lineContent = null;
			while ((lineContent = br.readLine()) != null) {
				if (lineContent.indexOf(content)!=-1) {
					return true;
				}
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
		return false;
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
	public static void writeBackIntoFile(List<String> resultContextList, File file,String charset) throws IOException {
		logger.info("回写文件："+file);
		BufferedWriter bw = null;
		try {

			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
			for (int i = 0; i < resultContextList.size(); i++) {
				bw.write(resultContextList.get(i));
				if (i == resultContextList.size()-1) {
					//由于使用br的readline所以无法判断最后一行是否是空行，此处统一处理，如果是空的则不输出
				}else {
					bw.write("\r\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}
	
	/**
	 * 写文件
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-22 下午4:30:11
	 *
	 * @param targetFile
	 * @param fileContent
	 * @throws IOException 
	 */
	public static void writeToFile(File  targetFile, String fileContent, boolean isAppend) throws IOException {
		if (!targetFile.exists()){
			try { 
				targetFile.createNewFile(); 
			} catch (IOException e) { 
				e.printStackTrace(); 
			}
		}

		FileWriter  pw = null;
		try {
			pw = new FileWriter (targetFile,isAppend);
			pw.write(fileContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
	
	/**
	 * 从类路径中读取配置
	 *
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-22 下午6:23:15
	 *
	 * @param classParam
	 * @param fileName
	 * @return
	 */
	public static String getContentFromFile(Object classParam,String fileName) {
		StringBuffer sb=new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(classParam.getClass().getResource(fileName).getFile())));
			String lineContent = null;
			while ((lineContent = br.readLine()) != null) {
				sb.append(lineContent+CHANGE_LINE);
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
		return sb.toString();
	}
}
