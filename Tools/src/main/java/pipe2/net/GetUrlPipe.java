package pipe2.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import pipe2.core.AbstractDelegatingPipe;

public class GetUrlPipe extends AbstractDelegatingPipe {
	private String url;

	public GetUrlPipe(String url) throws IOException {
		this.url = url;
	}

	@Override
	public void run(InputStream is, OutputStream os) throws Exception {
		super.run(new URL(url).openStream(), os);
	}
}
