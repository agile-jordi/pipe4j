/*
 * Copyright (C) 2010 Bernardo O. Bennett
 * 
 * This file is part of Pipe4j.
 * 
 * Pipe4j is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Pipe4j is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the Lesser GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the Lesser GNU General Public License
 * along with Pipe4j. If not, see <http://www.gnu.org/licenses/>.
 */
package pipe4j.pipe.xml;

import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;

import pipe4j.core.connector.BlockingBuffer;
import pipe4j.pipe.AbstractPipe;

/**
 * Uses a {@link XMLEventReader} to read xml events from {@link InputStream} and
 * write to object buffer output.
 * 
 * @author bbennett
 */
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
