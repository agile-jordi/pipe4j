package pipe;

public class PipeConfiguration {
	private long timeoutMillis = -1;

	public long getTimeoutMillis() {
		return timeoutMillis;
	}

	public void setTimeoutMillis(long timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}
	
	public boolean hasTimeout() {
		return this.timeoutMillis > 0l;
	}
}
