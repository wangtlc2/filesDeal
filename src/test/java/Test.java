import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;

public class Test {
	public static void main(String[] args) throws IOException {
		// 磁盘大小
		System.out.println(chg2G(FileSystemUtils.freeSpaceKb("y:/")));

		// 获取文件（夹）的大小，返回long类型
		System.out.println(chg2G(FileUtils.sizeOf(new File("y:/workspace-stq-new-sx/所有分支maven化改造资料/山西maven化资料/1、MAVEN方案/"))));

		// 直接将IO流转成字符串
		// InputStream in = new URL("http://jakarta.apache.org").openStream();
		// try {
		// System.out.println(IOUtils.toString(in));
		// } finally {
		// IOUtils.closeQuietly(in);
		// }

		// 读取文本文件的所有行
		File file = new File("c:/ServinvokeFile.log");
		List lines = FileUtils.readLines(file, "UTF-8");
		for (int i = 0; i < lines.size(); i++) {
			System.out.println(lines.get(i));
		}

		// 路径处理
		String filename = "C:/commons/io/../lang/project.xml";
		System.out.println(FilenameUtils.normalize(filename)); // result is
																// "C:/commons/lang/project.xml"

		// 打印文件的所有行
		LineIterator it = FileUtils.lineIterator(file, "UTF-8");
		try {
			while (it.hasNext()) {
				String line = it.nextLine(); // / do something with line
				System.out.println(line);
			}
		} finally {
			LineIterator.closeQuietly(it);
		}

	}

	private static float chg2G(long sizeOfDirectory) {
		return (float) sizeOfDirectory / 1024 / 1024;
	}

}
