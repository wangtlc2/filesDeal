import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class TestXml {
	public static void main(String[] args) throws IOException {
		String tmp = FileUtils.readFileToString(new File("adapterInterface.xml"),"UTF-8");
		System.out.println(tmp);
		ByteArrayInputStream bis = new ByteArrayInputStream(tmp.getBytes());
		;
//		System.out.println(new String(tmp.getBytes("GBK")));
	}
}
