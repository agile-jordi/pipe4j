package pipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCountPipe extends AbstractPipe {
	@Override
	public void run() throws Exception {
		int totalBytes = 0;
		int totalWords = 0;
		int totalLines = 0;
		int totalChars = 0;
		
		CountingInputStream cis = new CountingInputStream(getInputStream());
		CountingInputStreamReader cisr = new CountingInputStreamReader(cis);
		BufferedReader reader = new BufferedReader(cisr);

		String line;
		Pattern pattern = Pattern.compile("\\S+");
		while ((line = reader.readLine()) != null) {
			++totalLines;
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				++totalWords;
			}
		}
		
		totalChars = cisr.getCount();
		totalBytes = cis.getCount();
		OutputStreamWriter writer = new OutputStreamWriter(getOutputStream());
		writer.write(totalLines + "\t" + totalWords + "\t" + totalChars + "\t" + totalBytes);
		writer.flush();
	}

	private class CountingInputStream extends InputStream {
		private final InputStream is;
		private int count = 0;

		public CountingInputStream(InputStream is) {
			super();
			this.is = is;
		}

		public int read() throws IOException {
			int read = is.read();
			if (read >= 0) {
				++count;				
			}
			return read;
		}

		public int read(byte[] b) throws IOException {
			int read = is.read(b);
			this.count += read;
			return read;
		}

		public int read(byte[] b, int off, int len) throws IOException {
			int read = is.read(b, off, len);
			this.count += read;
			return read;
		}

		public int getCount() {
			return count;
		}
	}

	private class CountingInputStreamReader extends InputStreamReader {
		private int count = 0;
		
		public CountingInputStreamReader(InputStream in) {
			super(in);
		}

		@Override
		public int read() throws IOException {
			int read = super.read();
			if (read != -1) {
				++count;
			}
			return read;
		}
		
		@Override
		public int read(char[] cbuf) throws IOException {
			int read = super.read(cbuf);
			count += read;
			return read;
		}
		
		@Override
		public int read(char[] cbuf, int offset, int length) throws IOException {
			int read = super.read(cbuf, offset, length);
			count += read;
			return read;
		}
		
		@Override
		public int read(CharBuffer target) throws IOException {
			int read = super.read(target);
			count += read;
			return read;
		}
		
		public int getCount() {
			return count;
		}
	}
}
