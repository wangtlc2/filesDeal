package com.app.updatefile.v2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.app.updatefile.v2.bean.BooleanValue;
import com.app.updatefile.v2.bean.UpdateBean;
import com.util.FileContentUtil;
import com.util.FileEncodeCheckUtil;
import com.util.FilesInDirsUtil;

/**
 * 批量修改类文件中内容
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
public class UpdateWithReadAndWrite {
	static Logger logger = Logger.getLogger(UpdateWithReadAndWrite.class);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 待处理的目录
	 */
//	static String toDealDir ="Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-web-sx/src/main/resources/springxml/";
	static String toDealDir = "Y:/workspace-stq-monitor/monitor_server/monitor_web/src/main/";
	/**
	 * 待处理的文件后缀
	 */
	static String postfix="-1";//".jsp";
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * 待替换的完整类路径、类名
	 */
	static List<UpdateBean> configToUpdateBeanList = new ArrayList<UpdateBean>();
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
			br = new BufferedReader(new InputStreamReader(new FileInputStream(UpdateWithReadAndWrite.class.getResource("toupdatelist.txt").getFile())));
			String lineContent = null;
			while ((lineContent = br.readLine()) != null) {
				if (lineContent.startsWith("#")) {
					continue;
				}
				String s=lineContent.split("=")[0];
				String t=lineContent.split("=")[1];
				if (StringUtils.isEmpty(s) || StringUtils.isEmpty(t)) {
					logger.error(s+"配置有误，不处理！");
				}else if(s.equals(t)){
					logger.error(s+"====="+t+"配置相同，跳过！");
				}else {
					configToUpdateBeanList.add(new UpdateBean(s,t,s.substring(s.lastIndexOf(".")+1),t.substring(t.lastIndexOf(".")+1)));
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

		logger.info("准备对【"+toDealFilesList.size()+"】个文件处理");
		for (int i = 0; i < toDealFilesList.size(); i++) {
			List<String> resultContextList = new ArrayList<String>();
			String charset=FileEncodeCheckUtil.get_charset(toDealFilesList.get(i));
			BooleanValue isUpdated=new BooleanValue();
			readFromFile(resultContextList, toDealFilesList.get(i),isUpdated,charset);
			if (isUpdated.value) {//有待处理的内容
				logger.info("通过编码："+FileEncodeCheckUtil.get_charset(toDealFilesList.get(i))+",写入文件："+toDealFilesList.get(i));
				FileContentUtil.writeBackIntoFile(resultContextList, toDealFilesList.get(i),charset);
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
	 * @param isUpdated 
	 */
	private static void readFromFile(List<String> resultContextList, File file, BooleanValue isUpdated,String charset) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
			String lineContent = null;
			while ((lineContent = br.readLine()) != null) {
				resultContextList.add(getNewContent(lineContent,isUpdated,file));
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
	 * 获取替换后的新内容
	 *
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-15 上午10:08:29
	 *
	 * @param lineContent
	 * @param isUpdated 
	 * @param file 
	 * @return
	 */
	private static String getNewContent(String lineContent, BooleanValue isUpdated, File file) {
		if (lineContent.indexOf("%@page")!=-1 || (lineContent.indexOf("%>")!=-1 && lineContent.indexOf("import")!=-1)) {
			if (lineContent.indexOf(";")!=-1) {
				lineContent=lineContent.replace(";", "");
				isUpdated.value=true;//有内容被替换了
				logger.info("【更新文件："+file.getAbsolutePath()+"】，原因：在import里面有；");
			}
		}
		for (int i = 0; i < configToUpdateBeanList.size(); i++) {
			//处理类路径
			if (!StringUtils.isEmpty(configToUpdateBeanList.get(i).getSource()) &&lineContent.indexOf(configToUpdateBeanList.get(i).getSource())!=-1&&
					!configToUpdateBeanList.get(i).getSource().equals(configToUpdateBeanList.get(i).getTarget())) {
				lineContent=lineContent.replace(configToUpdateBeanList.get(i).getSource(), configToUpdateBeanList.get(i).getTarget());
				isUpdated.value=true;//有内容被替换了
				logger.info("【更新文件："+file.getAbsolutePath()+"】，原因："+configToUpdateBeanList.get(i));
			}
			//处理类名
			try {
				if (!configToUpdateBeanList.get(i).getClassName4s().equals(configToUpdateBeanList.get(i).getClassName4t())) {
					if (lineContent.indexOf("("+configToUpdateBeanList.get(i).getClassName4s()+".")!=-1) {//通过(和.进行尽量准确替换
						lineContent=lineContent.replace("("+configToUpdateBeanList.get(i).getClassName4s()+".", "("+configToUpdateBeanList.get(i).getClassName4t()+".");
						logger.info("【更新文件："+file.getAbsolutePath()+"，因为："+configToUpdateBeanList.get(i));
						isUpdated.value=true;//有内容被替换了
					}
					if (lineContent.indexOf("(!"+configToUpdateBeanList.get(i).getClassName4s()+".")!=-1) {//通过(和.进行尽量准确替换
						lineContent=lineContent.replace("(!"+configToUpdateBeanList.get(i).getClassName4s()+".", "(!"+configToUpdateBeanList.get(i).getClassName4t()+".");
						logger.info("【更新文件："+file.getAbsolutePath()+"，因为："+configToUpdateBeanList.get(i));
						isUpdated.value=true;//有内容被替换了
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lineContent;
	}


}
