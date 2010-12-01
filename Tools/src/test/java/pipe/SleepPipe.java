package pipe;

class SleepPipe extends MiddlePipe {
	private long millis;
	
	public SleepPipe(long millis) {
		super();
		this.millis = millis;
	}

	@Override
	public void run() throws Exception {
		super.run();
		Thread.sleep(millis);
	}
}