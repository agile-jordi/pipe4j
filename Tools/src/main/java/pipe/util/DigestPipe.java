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
package pipe.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;

import pipe.core.AbstractPipe;
import pipe.core.PipeProcessor;

public class DigestPipe extends AbstractPipe implements PipeProcessor {
	private static final String HEXES = "0123456789abcdef";
	private static final String defaultAlgorithm = "MD5";
	private String algorithm;

	public DigestPipe() {
		this(defaultAlgorithm);
	}

	public DigestPipe(String algorithm) {
		super();
		this.algorithm = algorithm;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		byte[] buffer = new byte[8192];

		int numRead;
		while (!isCancelled() && (numRead = is.read(buffer)) != -1) {
			md.update(buffer, 0, numRead);
		}

		if (isCancelled()) {
			return;
		}

		String checksum = getHex(md.digest());
		OutputStreamWriter writer = new OutputStreamWriter(os);
		writer.write(checksum);
		writer.flush();
	}

	private String getHex(byte[] raw) {
		StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
					HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}

}
