package pipe;



class WriteClosingPipe extends AbstractPipedRunnable implements Pipe {
	@Override
	public void run() throws Exception {
		char[] buffer = new char[8];
		int n = getReader().read(buffer);
		getWriter().write(buffer, 0, n);
		getWriter().close();
	}

	public boolean isRunning() {
		return false;
	}

	public void setRunning(boolean running) {
	}
}