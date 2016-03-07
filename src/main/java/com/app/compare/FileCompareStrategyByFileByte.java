package com.app.compare;

import java.io.File;
import java.io.FileInputStream;

public class FileCompareStrategyByFileByte implements FileCompareStrategy {

	@Override
	public boolean isEqualsTwoFile(File f1, File f2) throws Exception {
		FileInputStream fis1 = new FileInputStream(f1);
		FileInputStream fis2 = new FileInputStream(f2);
		int b = 0;
		int c = 0;
		while (true) {
			b = fis1.read();
			c = fis2.read();
			if (b != -1 && c != -1) {
				if (b != c) {
					return false;
				}
			} else {
				break;
			}
		}
		return true;
	}
}
