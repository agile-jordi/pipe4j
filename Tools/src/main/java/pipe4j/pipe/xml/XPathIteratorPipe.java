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

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.stream.events.XMLEvent;

import pipe4j.core.connector.BlockingBuffer;
import pipe4j.pipe.SimpleObjectPipe;

/**
 * Reads {@link XMLEvent} instances from previous pipe and writes corresponding
 * {@link XPathAndValue} instances to next pipe.
 * 
 * @author bbennett
 */
public class XPathIteratorPipe extends SimpleObjectPipe {
	private final XMLEventProcessor processor = new XMLEventProcessor();
	private Collection<Ignore> ignoreList = new ArrayList<Ignore>();

	public XPathIteratorPipe() {
		this(new ArrayList<Ignore>());
	}

	public XPathIteratorPipe(Collection<Ignore> ignoreList) {
		this.ignoreList = ignoreList;
	}

	public void addIgnore(Ignore ignore) {
		this.ignoreList.add(ignore);
	}

	@Override
	protected void run(BlockingBuffer inputBuffer, BlockingBuffer outputBuffer)
			throws Exception {
		XMLEvent event;
		while (!cancelled() && (event = (XMLEvent) inputBuffer.take()) != null) {
			for (XPathAndValue xpathAndValue : processor.process(event)) {
				boolean shouldIgnore = false;
				for (Ignore ignore : ignoreList) {
					shouldIgnore = ignore.shouldIgnore(xpathAndValue);
					break;
				}

				if (!shouldIgnore) {
					outputBuffer.put(xpathAndValue);
				}
			}
		}
	}
}
