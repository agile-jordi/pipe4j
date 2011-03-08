package pipe4j.core;

public interface Pipe<I, O> {
	void run(I in, O out) throws Exception;

	void cancel();
}
