package com.app.compare;

import java.io.File;

public interface FileCompareStrategy {
	 boolean isEqualsTwoFile(File sourceFile, File targetFile) throws Exception;
}
