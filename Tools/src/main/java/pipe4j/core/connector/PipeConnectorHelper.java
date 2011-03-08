package pipe4j.core.connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

import pipe4j.core.BlockingBuffer;
import pipe4j.core.BlockingBufferImpl;
import pipe4j.core.PipeThread;

public class PipeConnectorHelper {
	private enum DataType {
		NULL, BYTE, OBJECT
	}

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

		Method prevRunMethod = getRunMethod(prevThread.getPipe().getClass());
		Method runMethod = getRunMethod(nextThread.getPipe().getClass());

		DataType outType = getDataType(prevRunMethod.getParameterTypes()[1]);
		DataType inType = getDataType(runMethod.getParameterTypes()[0]);

		if (outType != inType) {
			throw new IllegalArgumentException("Incompatible pipes: "
					+ prevThread.getPipe().getClass() + " outputs " + outType
					+ " but " + nextThread.getPipe().getClass() + " expects "
					+ inType);
		}

		Object in;
		Object out;
		if (outType == DataType.BYTE) {
			out = new PipedOutputStream();
			try {
				in = new PipedInputStream((PipedOutputStream) out);
			} catch (IOException wontHappen) {
				throw new RuntimeException(wontHappen);
			}
		} else {
			in = new BlockingBufferImpl<Object>();
			out = in;
		}
		prevThread.setOut(out);
		nextThread.setIn(in);
	}

	private static DataType getDataType(Class<?> clazz) {
		if (clazz.isAssignableFrom(InputStream.class))
			return DataType.BYTE;
		if (clazz.isAssignableFrom(OutputStream.class))
			return DataType.BYTE;
		if (clazz.isAssignableFrom(BlockingBuffer.class))
			return DataType.OBJECT;
		throw new IllegalArgumentException("Unknown data type: "
				+ clazz.getName());
	}

	private static Method getRunMethod(Class<?> clazz) {
		for (Method method : clazz.getMethods()) {
			if (method.isBridge())
				continue;
			if (method.getName().equals("run")
					&& void.class.equals(method.getReturnType())
					&& method.getParameterTypes().length == 2)
				return method;
		}
		throw new IllegalArgumentException("Cannot find run method for class "
				+ clazz.getName());
	}
}
