package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil4Copy {
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("命令行参数输入有误，请检查");
			System.exit(1);
		}
		File file1 = new File(args[0]);
		File file2 = new File(args[1]);

		if (!file1.exists()) {
			System.out.println("被复制的文件不存在");
			System.exit(1);
		}
		InputStream input = new FileInputStream(file1);
		OutputStream output = new FileOutputStream(file2);
		if ((input != null) && (output != null)) {
			int temp = 0;
			while ((temp = input.read()) != (-1)) {
				output.write(temp);
			}
		}
		input.close();
		output.close();
	}

}
