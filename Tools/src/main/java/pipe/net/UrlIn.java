package pipe.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import pipe.core.AbstractPipeIn;

public class UrlIn extends AbstractPipeIn {
	private String url;

	public UrlIn(String url) throws IOException {
		this.url = url;
	}

	@Override
	protected InputStream getInputStream() throws Exception {
		return new URL(url).openStream();
	}
}
