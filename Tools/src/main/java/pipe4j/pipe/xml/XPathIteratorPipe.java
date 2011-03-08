package pipe4j.pipe.xml;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import javax.xml.stream.events.XMLEvent;

import pipe4j.pipe.AbstractPipe;

public class XPathIteratorPipe extends
		AbstractPipe<BlockingQueue<XMLEvent>, BlockingQueue<XPathAndValue>> {
	private final Processor processor = new Processor();

	@Override
	public void run(BlockingQueue<XMLEvent> in, BlockingQueue<XPathAndValue> out)
			throws Exception {
		XMLEvent event;
		while (!cancelled() && (event = in.take()) != null) {
			Map<String, String> result = processor.process(event);
			for (Map.Entry<String, String> entry : result.entrySet()) {
				out.put(new XPathAndValue(entry.getKey(), entry
						.getValue()));
			}
		}
	}
}
