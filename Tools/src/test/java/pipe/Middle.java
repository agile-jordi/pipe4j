package pipe;

class Middle extends AbstractPipedRunnable implements Pipe {
	@Override
	public void run() throws Exception {
		char[] buffer = new char[1024 * 4];
		int n;
		while ((n = getReader().read(buffer)) != -1) {
			getWriter().write(buffer, 0, n);
		}
	}
}