package pipe;

class MiddlePipe extends AbstractDelegatingPipe implements Pipe {
	@Override
	protected int getBufferSize() {
		return 1024 * 4;
	}
}