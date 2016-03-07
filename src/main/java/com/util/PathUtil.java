package com.util;

import java.io.File;

public class PathUtil {

	//Y:\workspace-stq\peb_core\src\main\java\com\sitech\core\esb\ServiceClientEx.java
	//转换为
	//com.sitech.core.esb.ServiceClientEx
	public static String chgAbsPath2ClassPath(String tmp) {
		return tmp.substring(tmp.indexOf(File.separatorChar+"java"+File.separatorChar)+6,tmp.lastIndexOf(".java")).replace(File.separatorChar, '.');
	}
	

}
