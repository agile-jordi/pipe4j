package pipe;

import java.io.File;

import pipe.sink.FileSink;
import pipe.source.FileSource;

import junit.framework.TestCase;

public class FileTest extends TestCase {
	private String txtInFilePath;
	private String txtOutFilePath;

	public FileTest() {
		String pathSep = File.separator;
		txtInFilePath = new StringBuilder(System.getProperty("user.dir"))
				.append(pathSep).append("src").append(pathSep).append("test")
				.append(pathSep).append("java").append(pathSep)
				.append("sample.txt").toString();
		txtOutFilePath = new StringBuilder(System.getProperty("user.dir"))
				.append(pathSep).append("target").append(pathSep)
				.append("test-classes").append(pathSep).append("sample_out.txt")
				.toString();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		new File(this.txtOutFilePath).delete();
	}
	
	public void testDefaultBuffer() throws Exception {
		 new Pipeline(new Pipe[] {
		 new FileSource(txtInFilePath),
		 new FileSink(txtOutFilePath)
		 }).run();
	}
	
	public void testCustomBuffer1() throws Exception {
		 new Pipeline(new Pipe[] {
		 new FileSource(txtInFilePath, 8),
		 new FileSink(txtOutFilePath, 16)
		 }).run();
	}
	
	public void testCustomBuffer2() throws Exception {
		 new Pipeline(new Pipe[] {
		 new FileSource(txtInFilePath, 16),
		 new FileSink(txtOutFilePath, 8)
		 }).run();
	}
}
