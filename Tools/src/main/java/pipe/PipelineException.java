package pipe;

import java.util.ArrayList;
import java.util.List;

public class PipelineException extends RuntimeException {
	private static final long serialVersionUID = 6294816980508295736L;
	private List<Throwable> pipelineThrowableList = new ArrayList<Throwable>();
	
	public PipelineException() {
		super();
	}

	public PipelineException(String message, Throwable cause) {
		super(message, cause);
		addThrowable(cause);
	}

	public PipelineException(String message) {
		super(message);
	}

	public PipelineException(Throwable cause) {
		super(cause);
		addThrowable(cause);
	}

	public void addThrowable(Throwable t) {
		pipelineThrowableList.add(t);
		if (getCause() == null) {
			initCause(t);
		}
	}
}
