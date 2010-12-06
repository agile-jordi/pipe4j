package pipe2.core;

import java.io.File;

public class TestUtils {
	public static final String txtMD5 = "b91ff45cda8fe9b05ebd9bd38ddef48a";
	public static String txtInFilePath;
	public static String txtOutFilePath;

	static {
		String pathSep = File.separator;
		txtInFilePath = new StringBuilder(System.getProperty("user.dir"))
				.append(pathSep).append("src").append(pathSep).append("test")
				.append(pathSep).append("java").append(pathSep)
				.append("sample.txt").toString();
		txtOutFilePath = new StringBuilder(System.getProperty("user.dir"))
				.append(pathSep).append("target").append(pathSep)
				.append("test-classes").append(pathSep)
				.append("sample_out.txt").toString();
	}
}
