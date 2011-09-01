package pipe4j.core;

import java.util.List;

public interface ExceptionWrapper {

	void addException(Exception exception);

	List<Exception> getExceptionList();

	void aggregate(ExceptionWrapper exceptionWrapper);

	boolean hasExceptions();
}