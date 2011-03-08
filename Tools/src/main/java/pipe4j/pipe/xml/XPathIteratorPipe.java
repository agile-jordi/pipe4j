package pipe4j.pipe.xml;

import java.util.Map;

import javax.xml.stream.events.XMLEvent;

import pipe4j.core.BlockingBuffer;
import pipe4j.pipe.AbstractPipe;

public class XPathIteratorPipe extends
		AbstractPipe<BlockingBuffer<XMLEvent>, BlockingBuffer<XPathAndValue>> {
	private final Processor processor = new Processor();

	@Override
	public void run(BlockingBuffer<XMLEvent> in, BlockingBuffer<XPathAndValue> out)
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
