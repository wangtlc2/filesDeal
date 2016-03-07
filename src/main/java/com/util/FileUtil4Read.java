package com.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Scanner;

public class FileUtil4Read {
	/**
	 * 字节流 读文件内容
	 * */
	public static void main(String[] args) throws IOException {
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		InputStream in = new FileInputStream(f);
		byte[] b = new byte[1024];
		in.read(b);
		in.close();
		System.out.println(new String(b));
	}

	// 但是这个例子读取出来会有大量的空格，我们可以利用in.read(b);的返回值来设计程序。如下：
	public static void main2(String[] args) throws IOException {
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		InputStream in = new FileInputStream(f);
		byte[] b = new byte[1024];
		int len = in.read(b);
		in.close();
		System.out.println("读入长度为：" + len);
		System.out.println(new String(b, 0, len));
	}

	// 读者观察上面的例子可以看出，我们预先申请了一个指定大小的空间，但是有时候这个空间可能太小，有时候可能太大，我们需要准确的大小，这样节省空间，那么我们可以这样干：
	public static void main3(String[] args) throws IOException {
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		InputStream in = new FileInputStream(f);
		byte[] b = new byte[(int) f.length()];
		in.read(b);
		System.out.println("文件长度为：" + f.length());
		in.close();
		System.out.println(new String(b));
	}

	/**
	 * 字节流 一个一个读 读文件内容,节省空间
	 * */

	public static void main4(String[] args) throws IOException {
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		InputStream in = new FileInputStream(f);
		byte[] b = new byte[(int) f.length()];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) in.read();
		}
		in.close();
		System.out.println(new String(b));
	}

	// 上面的几个例子都是在知道文件的内容多大，然后才展开的，有时候我们不知道文件有多大，这种情况下，我们需要判断是否独到文件的末尾。
	public static void main5(String[] args) throws IOException {
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		InputStream in = new FileInputStream(f);
		byte[] b = new byte[1024];
		int count = 0;
		int temp = 0;
		while ((temp = in.read()) != (-1)) {
			b[count++] = (byte) temp;
		}
		in.close();
		System.out.println(new String(b));
	}

	/**
	 * 字符流 从文件中读出内容
	 * */

	public static void main6(String[] args) throws IOException {
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		char[] ch = new char[100];
		Reader read = new FileReader(f);
		int count = read.read(ch);
		read.close();
		System.out.println("读入的长度为：" + count);
		System.out.println("内容为" + new String(ch, 0, count));
	}

	// 当然最好采用循环读取的方式，因为我们有时候不知道文件到底有多大。
	/**
	 * 字符流 从文件中读出内容
	 * */
	public static void main7(String[] args) throws IOException {
		String fileName = "D:" + File.separator + "hello.txt";
		File f = new File(fileName);
		char[] ch = new char[100];
		Reader read = new FileReader(f);
		int temp = 0;
		int count = 0;
		while ((temp = read.read()) != (-1)) {
			ch[count++] = (char) temp;
		}
		read.close();
		System.out.println("内容为" + new String(ch, 0, count));
	}

	public static void main12(String[] args) {
		Scanner sca = new Scanner(System.in);
		// 读一个整数
		int temp = sca.nextInt();
		System.out.println(temp);
		// 读取浮点数
		float flo = sca.nextFloat();
		System.out.println(flo);
		// 读取字符
		// ...等等的，都是一些太基础的，就不师范了。
	}

	/**
	 * Scanner的小例子，从文件中读内容
	 * */
	public static void main13(String[] args) {

		File file = new File("d:" + File.separator + "hello.txt");
		Scanner sca = null;
		try {
			sca = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String str = sca.next();
		System.out.println("从文件中读取的内容是：" + str);
	}

	public static void main121(String[] args) throws IOException {
		File file = new File("d:" + File.separator + "hello.txt");
		DataInputStream input = new DataInputStream(new FileInputStream(file));
		char[] ch = new char[10];
		int count = 0;
		char temp;
		while ((temp = input.readChar()) != 'C') {
			ch[count++] = temp;
		}
		System.out.println(ch);
	}

}
