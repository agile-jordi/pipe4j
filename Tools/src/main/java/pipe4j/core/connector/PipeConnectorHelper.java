package pipe4j.core.connector;

import java.util.Collection;
import java.util.HashSet;

import pipe4j.core.PipeThread;

public class PipeConnectorHelper {
	private static Collection<PipeConnector> connectors = new HashSet<PipeConnector>();

	static {
		registerPipeConnector(new BlockingBufferPipeConnector());
		registerPipeConnector(new StreamPipeConnector());
	}

	public static void registerPipeConnector(PipeConnector connector) {
		connectors.add(connector);
	}

	public static void connect(PipeThread<Object, Object> prevThread,
			PipeThread<Object, Object> nextThread) {
		PipeConnector connector = null;
		for (PipeConnector conn : connectors) {
			if (conn.supports(prevThread.getPipe(), nextThread.getPipe())) {
				connector = conn;
				break;
			}
		}
		if (connector == null) {
			throw new IllegalArgumentException("Don't know how to connect "
					+ prevThread.getPipe().toString() + " with "
					+ nextThread.getPipe().toString());
		}

		connector.connect(prevThread, nextThread);
	}
}
