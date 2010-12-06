package pipe2.txt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pipe2.core.AbstractPipe;

public class WordCountPipe extends AbstractPipe {
	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		int totalBytes = 0;
		int totalWords = 0;
		int totalLines = 0;
		int totalChars = 0;

		CountingInputStream cis = new CountingInputStream(is);
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
		OutputStreamWriter writer = new OutputStreamWriter(os);
		writer.write(totalLines + "\t" + totalWords + "\t" + totalChars + "\t"
				+ totalBytes);
		writer.flush();
	}
}
