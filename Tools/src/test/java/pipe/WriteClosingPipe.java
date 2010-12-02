package pipe;



class WriteClosingPipe extends AbstractPipe implements Pipe {
	@Override
	public void run() throws Exception {
		byte[] buffer = new byte[8];
		int n = getInputStream().read(buffer);
		getOutputStream().write(buffer, 0, n);
		getOutputStream().close();
	}

	public boolean isRunning() {
		return false;
	}

	public void setRunning(boolean running) {
	}
}