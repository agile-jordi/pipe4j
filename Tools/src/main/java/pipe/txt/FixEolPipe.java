package pipe.txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import pipe.core.AbstractPipe;

public class FixEolPipe extends AbstractPipe {
	public enum Platform {
		DOS, UNIX, MAC, AUTO
	}

	private Platform platform = Platform.AUTO;

	public FixEolPipe() {
	}

	public FixEolPipe(Platform platform) {
		super();
		this.platform = platform;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

		final String eol;
		switch (this.platform) {
		case MAC:
			eol = "\r";
			break;
		case DOS:
			eol = "\r\n";
			break;
		case UNIX:
			eol = "\n";
			break;
		case AUTO:
			eol = System.getProperty("line.separator");
			break;
		default:
			throw new IllegalArgumentException("Unknown platform: "
					+ this.platform);
		}

		String line = reader.readLine();
		if (line != null) {
			writer.write(line);
			while (!isCancelled() && ((line = reader.readLine()) != null)) {
				writer.write(eol); // intead of calling newLine
				writer.write(line);
			}
		}
		writer.flush();
	}
}
