package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * 输入输出重定向
 * 
 * @Package: com.util
 * @ClassName: FileUtil4ChgTarget
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-4-25 上午8:31:33
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 * 
 *           修改日期 修改人 修改目的
 * 
 */
public class FileUtil4ChgTarget {

	// 输入输出重定向
	/**
	 * 为System.out.println()重定向输出
	 * */

	public static void main11(String[] args) {
		// 此刻直接输出到屏幕
		System.out.println("hello");
		File file = new File("d:" + File.separator + "hello.txt");
		try {
			System.setOut(new PrintStream(new FileOutputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("这些内容在文件中才能看到哦！");
	}

	// eclipse的控制台输出的是hello。然后当我们查看d盘下面的hello.txt文件的时候，会在里面看到：这些内容在文件中才能看到哦
	/**
	 * System.err重定向 这个例子也提示我们可以使用这种方法保存错误信息
	 * */
	public static void main12(String[] args) {
		File file = new File("d:" + File.separator + "hello.txt");
		System.err.println("这些在控制台输出");
		try {
			System.setErr(new PrintStream(new FileOutputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.err.println("这些在文件中才能看到哦！");
	}

	/**
	 * System.in重定向
	 * */

	public static void main(String[] args) {
		File file = new File("d:" + File.separator + "hello.txt");
		if (!file.exists()) {
			return;
		} else {
			try {
				System.setIn(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			byte[] bytes = new byte[1024];
			int len = 0;
			try {
				len = System.in.read(bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("读入的内容为：" + new String(bytes, 0, len));
		}
	}

	/**
	 * 使用缓冲区从键盘上读入内容
	 * */

	public static void main1(String[] args) {
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		System.out.println("请输入内容");
		try {
			str = buf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("你输入的内容是：" + str);
	}


}
