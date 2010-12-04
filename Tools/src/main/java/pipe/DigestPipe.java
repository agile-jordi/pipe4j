package pipe;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;

public class DigestPipe extends AbstractPipe implements Pipe {
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
	public void run() throws Exception {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		byte[] buffer = new byte[8192];

		InputStream inputStream = getInputStream();
		int numRead;
		while (isRunning() && (numRead = inputStream.read(buffer)) != -1) {
			md.update(buffer, 0, numRead);
		}

		if (!isRunning()) {
			return;
		}

		String checksum = getHex(md.digest());
		OutputStreamWriter writer = new OutputStreamWriter(getOutputStream());
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
