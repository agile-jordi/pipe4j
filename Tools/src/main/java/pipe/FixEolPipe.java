package pipe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FixEolPipe extends AbstractPipe {
	enum Platform {
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
	public void run() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				getInputStream()));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				getOutputStream()));

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
			while (isRunning() && ((line = reader.readLine()) != null)) {
				writer.write(eol); // intead of calling newLine
				writer.write(line);
			}
		}
		writer.flush();
	}
}
