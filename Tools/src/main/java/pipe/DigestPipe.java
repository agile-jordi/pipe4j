package pipe;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;

public class DigestPipe extends AbstractPipe implements Pipe {
	private static final String HEXES = "0123456789ABCDEF";
	private static final int defaultBufferSize = 8 * 1024;
	private int bufferSize = defaultBufferSize;
	private static final String defaultAlgorithm = "MD5";
	private String algorithm;

	public DigestPipe() {
		this(defaultBufferSize, defaultAlgorithm);
	}

	public DigestPipe(int bufferSize) {
		this(bufferSize, defaultAlgorithm);
	}

	public DigestPipe(String algorithm) {
		this(defaultBufferSize, algorithm);
	}

	public DigestPipe(int bufferSize, String algorithm) {
		super();
		this.bufferSize = bufferSize;
		this.algorithm = algorithm;
	}

	@Override
	public void run() throws Exception {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		byte[] buffer = new byte[bufferSize];

		InputStream inputStream = getInputStream();
		int numRead;
		while ((numRead = inputStream.read(buffer)) != -1) {
			md.update(buffer, 0, numRead);
		}

		String checksum = getHex(md.digest());
		new OutputStreamWriter(getOutputStream()).write(checksum);
	}

	public static String getHex(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
					HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}

}
