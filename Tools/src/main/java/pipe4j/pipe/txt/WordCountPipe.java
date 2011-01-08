/*
 * Copyright (C) 2010 Bernardo O. Bennett
 * 
 * This file is part of Stream4j.
 * 
 * Stream4j is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Stream4j is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the Lesser GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the Lesser GNU General Public License
 * along with Stream4j. If not, see <http://www.gnu.org/licenses/>.
 */
package pipe4j.pipe.txt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pipe4j.pipe.AbstractPipe;

public class WordCountPipe extends AbstractPipe<InputStream, OutputStream> {
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
