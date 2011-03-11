package pipe4j.core;

import java.io.IOException;

public interface ConnectorDecorator<I, O> {
	I decorateIn(I in) throws IOException;

	O decorateOut(O out) throws IOException;
}
