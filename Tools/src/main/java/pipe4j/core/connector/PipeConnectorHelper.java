package pipe4j.core.connector;

import java.util.Collection;
import java.util.HashSet;

import pipe4j.core.PipeThread;

/**
 * Register pipe connector implementations and offers utility method to connect
 * 2 pipe threads.
 * 
 * @author bbennett
 */
public class PipeConnectorHelper {
	private static Collection<PipeConnector> connectors = new HashSet<PipeConnector>();

	static {
		registerPipeConnector(new BlockingBufferPipeConnector());
		registerPipeConnector(new StreamPipeConnector());
	}

	/**
	 * Register a connector implementation.
	 * 
	 * @param connector Connector implementation
	 */
	public static void registerPipeConnector(PipeConnector connector) {
		connectors.add(connector);
	}

	/**
	 * Connect 2 pipe threads.
	 * 
	 * @param prevThread First pipe thread
	 * @param nextThread Second pipe thread
	 */
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
