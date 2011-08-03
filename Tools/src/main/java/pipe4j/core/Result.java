package pipe4j.core;

public interface Result {
	enum Type {
		SUCCESS, FAILURE
	};

	Result.Type getType();

	boolean hasException();

	Exception getException();
}