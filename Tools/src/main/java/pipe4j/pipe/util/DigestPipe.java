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
package pipe4j.pipe.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;

import pipe4j.pipe.AbstractPipe;

/**
 * Digests {@link InputStream} with the configured algorithm and writes
 * resulting hash compuation to {@link OutputStream}. Default algorithm set to
 * MD5.
 * 
 * @author bbennett
 */
public class DigestPipe extends AbstractPipe<InputStream, OutputStream> {
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
		while (!cancelled() && (numRead = is.read(buffer)) != -1) {
			md.update(buffer, 0, numRead);
		}

		if (cancelled()) {
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
