package pipe.net;

import java.io.IOException;
import java.net.URL;

import pipe.AbstractDelegatingPipe;

public class GetUrlPipe extends AbstractDelegatingPipe {
	public GetUrlPipe(String urlString) throws IOException {
		URL url = new URL(urlString);
		setInputStream(url.openStream());
	}
}
