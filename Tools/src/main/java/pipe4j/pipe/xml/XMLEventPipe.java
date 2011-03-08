package pipe4j.pipe.xml;

import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;

import pipe4j.core.BlockingBuffer;
import pipe4j.pipe.AbstractPipe;

public class XMLEventPipe extends
AbstractPipe<InputStream, BlockingBuffer<XMLEvent>> {

	@Override
	public void run(InputStream in, BlockingBuffer<XMLEvent> out)
			throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader reader = factory.createXMLEventReader(in);
		while (!cancelled() && reader.hasNext()) {
			out.put(reader.nextEvent());
		}
	}
}
