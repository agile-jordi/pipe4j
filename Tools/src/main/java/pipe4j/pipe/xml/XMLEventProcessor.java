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

import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * Buffers {@link XMLEvent} instances and returns map of xpath => value.
 * 
 * @author bbennett
 */
class XMLEventProcessor {
	private static final String TEXT_SUFFIX = "/text()";
	private static final String ATTR_PREFIX = "/@";
	private static final char SLASH = '/';
	private final Stack<String> stack = new Stack<String>();
	private String bufferedText = null;
	private static final Pattern NON_BLANK_PATTERN = Pattern.compile("\\S");

	Map<String, String> process(XMLEvent event) {
		Map<String, String> paths = new TreeMap<String, String>();
		if (event.isStartElement()) {
			bufferedText = null;
			StartElement element = (StartElement) event;
			addXPathToStack(element.getName().toString());

			String xpath = stack.peek();
			/*
			 * This flags the start of the element. We will flag again if
			 * element contains text.
			 */
			paths.put(xpath, null);

			@SuppressWarnings("unchecked")
			Iterator<Attribute> iterator = element.getAttributes();
			while (iterator.hasNext()) {
				Attribute attribute = iterator.next();
				QName name = attribute.getName();
				String prefix = name.getPrefix();
				String attName = (prefix == null || prefix.trim().length() == 0 ? ""
						: prefix + ":")
						+ name.getLocalPart();
				paths.put(xpath + ATTR_PREFIX + attName, attribute.getValue());
			}
		} else if (event.isEndElement()) {
			EndElement element = event.asEndElement();
			String xpath = stack.pop();
			if (!xpath.endsWith(SLASH + element.getName().toString())) {
				throw new RuntimeException("Element mismatch! Started "
						+ xpath + " but ended "
						+ element.getName().toString());
			}
			if (bufferedText != null
					&& NON_BLANK_PATTERN.matcher(bufferedText).find()) {
				paths.put(xpath + TEXT_SUFFIX, bufferedText);
				bufferedText = null;
			}
		} else if (event.isCharacters()) {
			Characters characters = (Characters) event;
			bufferedText = characters.getData();
		}

		return paths;
	}

	private void addXPathToStack(String xpath) {
		if (!this.stack.isEmpty()) {
			String previous = this.stack.peek();
			this.stack.push(previous + SLASH + xpath);
		} else {
			this.stack.push(SLASH + xpath);
		}
	}
}
