package pipe4j.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Method;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Pipeline {
	private enum DataType {
		NULL, BYTE, OBJECT
	}
	
	@SuppressWarnings("rawtypes")
	public static PipelineInfo run(Pipe... pipeline) {
		return run(-1, pipeline);
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public static PipelineInfo run(long timeoutMilis, Pipe... pipeline) {
		if (pipeline == null || pipeline.length < 2) {
			throw new IllegalArgumentException("Need at least 2 pipes!");
		}

		final long timestamp = System.currentTimeMillis();
		ThreadGroup threadGroup = new ThreadGroup("Pipeline");

		PipeThread[] threads = new PipeThread[pipeline.length];
		threads[0] = new PipeThread(pipeline[0]);
		threads[0].setIn(Null.INSTANCE);
		for (int i = 1; i < pipeline.length; i++) {
			threads[i] = new PipeThread(pipeline[i]);
			connect(threads[i - 1], threads[i]);
		}
		threads[threads.length - 1].setOut(Null.INSTANCE);

		for (Thread thread : threads) {
			thread.start();
		}

		for (PipeThread thread : threads) {
			if (timeoutMilis > 0) {
				// Wait for the requested perid, minus elapsed time
				long discountedTimeout = Math
						.max(0, timeoutMilis
								- (System.currentTimeMillis() - timestamp));
				try {
					thread.join(discountedTimeout);
				} catch (InterruptedException e) {
				}
				if (thread.isAlive()) {
					thread.getPipe().cancel();
					try {
						Thread.sleep(100);
					} catch (InterruptedException ignored) {
					}
				}

				if (thread.isAlive()) {
					thread.interrupt();
					thread.close();
					try {
						Thread.sleep(100);
					} catch (InterruptedException ignored) {
					}
				}

				if (thread.isAlive()) {
					thread.stop(new InterruptedException("Thread stopped!"));
				}
			} else {
				// Wait forever
				try {
					thread.join();
				} catch (InterruptedException e) {
				}
			}
		}

		// If exception happened, get first found
		PipelineInfo info = new PipelineInfo(threadGroup);
		for (int i = 0; i < threads.length; i++) {
			if (threads[i].hasError()) {
				info.setException(threads[i].getException());
				break;
			}
		}
		return info;
	}

	private static void connect(PipeThread<Object, Object> prevThread,
			PipeThread<Object, Object> thread) {
		Method prevRunMethod = getRunMethod(prevThread.getPipe().getClass());
		Method runMethod = getRunMethod(thread.getPipe().getClass());

		DataType outType = getDataType(prevRunMethod.getParameterTypes()[1]);
		DataType inType = getDataType(runMethod.getParameterTypes()[0]);

		if (outType != inType) {
			throw new IllegalArgumentException("Incompatible pipes: "
					+ prevThread.getPipe().getClass() + " outputs " + outType
					+ " but " + thread.getPipe().getClass() + " expects "
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
			in = new ArrayBlockingQueue<Object>(1);
			out = in;
		}
		prevThread.setOut(out);
		thread.setIn(in);
	}

	private static DataType getDataType(Class<?> clazz) {
		if (clazz.isAssignableFrom(InputStream.class))
			return DataType.BYTE;
		if (clazz.isAssignableFrom(OutputStream.class))
			return DataType.BYTE;
		if (clazz.isAssignableFrom(BlockingQueue.class))
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
