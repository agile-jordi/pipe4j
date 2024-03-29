/*
 * Copyright (C) 2010 Bernardo O. Bennett
 * 
 * This file is part of Pipe4j.
 * 
 * Pipe4j is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Pipe4j is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the Lesser GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the Lesser GNU General Public License
 * along with Pipe4j. If not, see <http://www.gnu.org/licenses/>.
 */
package pipe4j.pipe.txt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pipe4j.pipe.SimpleStreamPipe;

/**
 * Counts lines, words, chars and bytes from {@link InputStream} and writes
 * result to {@link OutputStream}.
 * 
 * @author bbennett
 */
public class WordCountPipe extends SimpleStreamPipe {
	@Override
	protected void run(InputStream inputStream, OutputStream outputStream)
			throws Exception {
		int totalBytes = 0;
		int totalWords = 0;
		int totalLines = 0;
		int totalChars = 0;

		CountingInputStream cis = new CountingInputStream(inputStream);
		CountingInputStreamReader cisr = new CountingInputStreamReader(cis);
		BufferedReader reader = new BufferedReader(cisr);

		String line;
		Pattern pattern = Pattern.compile("\\S+");
		while (!cancelled() && (line = reader.readLine()) != null) {
			++totalLines;
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				++totalWords;
			}
		}

		totalChars = cisr.getCount();
		totalBytes = cis.getCount();
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		writer.write(totalLines + "\t" + totalWords + "\t" + totalChars + "\t"
				+ totalBytes);
		writer.flush();
	}
}
