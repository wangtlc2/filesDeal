package com.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.Writer;

public class FileUtil4Write {
	/**
	 * 使用RandomAccessFile写入文件 如果你此时打开hello。txt查看的话，会发现那是乱码。
	 * 
	 * @author 王陶林 wangtlc@si-tech.com.cn
	 * @date 2013-4-25 上午7:35:03
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		RandomAccessFile demo = new RandomAccessFile(f, "rw");
		demo.writeBytes("asdsad");
		demo.writeInt(12);
		demo.writeBoolean(true);
		demo.writeChar('A');
		demo.writeFloat(1.21f);
		demo.writeDouble(12.123);
		demo.close();
	}

	/**
	 * 字节流 向文件中写入字符串 查看hello.txt会看到“你好”
	 */
	public static void outputWithStream() throws IOException {
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		OutputStream out = new FileOutputStream(f);
		String str = "你好";
		byte[] b = str.getBytes();
		out.write(b);
		out.close();
	}

	/**
	 * 字节流 向文件中追加新内容：
	 * */
	public static void outputWithappend() throws IOException {
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		OutputStream out = new FileOutputStream(f, true);
		String str = "Rollen";
		// String str="\r\nRollen"; 可以换行
		byte[] b = str.getBytes();
		for (int i = 0; i < b.length; i++) {
			out.write(b[i]);
		}
		out.close();
	}

	/**
	 * 字符流 写入数据
	 * */
	public static void main6(String[] args) throws IOException {
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		Writer out = new FileWriter(f);
		String str = "hello";
		out.write(str);
		out.close();
	}

	// 打印流
	/**
	 * 使用PrintStream进行输出
	 * */

	public static void main7(String[] args) throws IOException {
		PrintStream print = new PrintStream(new FileOutputStream(new File("d:" + File.separator + "hello.txt")));
		print.println(true);
		print.println("Rollen");
		print.close();
	}

	/**
	 * 使用PrintStream进行输出 并进行格式化
	 * */

	public static void main8(String[] args) throws IOException {
		PrintStream print = new PrintStream(new FileOutputStream(new File("d:" + File.separator + "hello.txt")));
		String name = "Rollen";
		int age = 20;
		print.printf("姓名：%s. 年龄：%d.", name, age);
		print.close();
	}

	/**
	 * 使用OutputStream向屏幕上输出内容
	 * */

	public static void main9(String[] args) throws IOException {
		OutputStream out = System.out;
		try {
			out.write("hello".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main1(String[] args) throws IOException {
		File file = new File("d:" + File.separator + "hello.txt");
		char[] ch = { 'A', 'B', 'C' };
		DataOutputStream out = null;
		out = new DataOutputStream(new FileOutputStream(file));
		for (char temp : ch) {
			out.writeChar(temp);
		}
		out.close();
	}

}
