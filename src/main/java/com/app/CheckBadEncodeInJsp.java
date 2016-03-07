package com.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.common.bean.SimpleValueByRef;
import com.util.FileBadEncodeCheckUtil;
import com.util.FileEncodeCheckUtil;
import com.util.FilesInDirsUtil;
/**
 * 提取JSP中引用了不存在的类的JSP文件列表
 * @Package: com
 * @ClassName: FindNotExistClassInJsp
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-3-21 下午9:28:47
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 *
 * 修改日期    修改人    修改目的
 *
 */
public class CheckBadEncodeInJsp {
	private static Logger logger = Logger.getLogger(CheckBadEncodeInJsp.class);

	private static String jspDir = "Y:/workspace-stq-new-sx/echd-chinamobile-sx/echd-chinamobile-web-sx/src/main/webapp/";
	
	public static void main(String[] args) {
		logger.info("开始执行！");
		List<File> files=new ArrayList<File>();
		files=FilesInDirsUtil.findFilesByPostfixInDir(jspDir,".jsp",files);
		for (int i = 0; i < files.size(); i++) {//逐个处理JSP文件
			//查JSP文件内部编码和文件本身编码不一致的文件
			SimpleValueByRef simpleValueByRef=new SimpleValueByRef();
			if (FileBadEncodeCheckUtil.selfEncodeNotEqualsPageEncoding(files.get(i),simpleValueByRef)) {
				logger.info("该JSP【"+files.get(i)+"】编码不一致！自身编码为："+FileEncodeCheckUtil.get_charset(files.get(i))+"，文件中指定编码为："+simpleValueByRef.object);
			}
			
			//查乱码文件
			if (FileBadEncodeCheckUtil.checkHasBadEncode(files.get(i))) {
				logger.info("该JSP【"+files.get(i)+"】有乱码！自身编码为："+FileEncodeCheckUtil.get_charset(files.get(i)));
			}
		}
		
		logger.info("处理完毕！"+FileBadEncodeCheckUtil.resultList);
	}
}
