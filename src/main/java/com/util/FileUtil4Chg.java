package com.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.io.Writer;

// 整个IO类中除了字节流和字符流还包括字节和字符转换流。
//
// OutputStreramWriter将输出的字符流转化为字节流
//
// InputStreamReader将输入的字节流转换为字符流
//
// 但是不管如何操作，最后都是以字节的形式保存在文件中的。
public class FileUtil4Chg {
	/**
	 * 将字节输出流转化为字符输出流
	 * */
	public static void main(String[] args) throws IOException {
		String fileName = "d:" + File.separator + "hello.txt";
		File file = new File(fileName);
		Writer out = new OutputStreamWriter(new FileOutputStream(file));
		out.write("hello");
		out.close();
	}

	/**
	 * 将字节输入流变为字符输入流
	 * */

	public static void main2(String[] args) throws IOException {
		String fileName = "d:" + File.separator + "hello.txt";
		File file = new File(fileName);
		Reader read = new InputStreamReader(new FileInputStream(file));
		char[] b = new char[100];
		int len = read.read(b);
		System.out.println(new String(b, 0, len));
		read.close();
	}

	// 前面列举的输出输入都是以文件进行的，现在我们以内容为输出输入目的地，使用内存操作流
	//
	// ByteArrayInputStream 主要将内容写入内容
	//
	// ByteArrayOutputStream 主要将内容从内存输出
	/**
	 * 使用内存操作流将一个大写字母转化为小写字母
	 * */

	public static void main3(String[] args) throws IOException {
		String str = "ROLLENHOLT";
		ByteArrayInputStream input = new ByteArrayInputStream(str.getBytes());
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int temp = 0;
		while ((temp = input.read()) != -1) {
			char ch = (char) temp;
			output.write(Character.toLowerCase(ch));
		}
		String outStr = output.toString();
		input.close();
		output.close();
		System.out.println(outStr);
	}
//SequenceInputStream主要用来将2个流合并在一起，比如将两个txt中的内容合并为另外一个txt。下面给出一个实例：

	public static void main1(String[] args) throws IOException {
		File file1 = new File("d:" + File.separator + "hello1.txt");
		File file2 = new File("d:" + File.separator + "hello2.txt");
		File file3 = new File("d:" + File.separator + "hello.txt");
		InputStream input1 = new FileInputStream(file1);
		InputStream input2 = new FileInputStream(file2);
		OutputStream output = new FileOutputStream(file3);
		// 合并流
		SequenceInputStream sis = new SequenceInputStream(input1, input2);
		int temp = 0;
		while ((temp = sis.read()) != -1) {
			output.write(temp);
		}
		input1.close();
		input2.close();
		output.close();
		sis.close();
	}

}
