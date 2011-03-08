package pipe4j.pipe.xml;

import java.io.InputStream;
import java.util.concurrent.BlockingQueue;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;

import pipe4j.pipe.AbstractPipe;

public class XMLEventPipe extends
AbstractPipe<InputStream, BlockingQueue<XMLEvent>> {

	@Override
	public void run(InputStream in, BlockingQueue<XMLEvent> out)
			throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader reader = factory.createXMLEventReader(in);
		while (!cancelled() && reader.hasNext()) {
			out.put(reader.nextEvent());
		}
	}
}
