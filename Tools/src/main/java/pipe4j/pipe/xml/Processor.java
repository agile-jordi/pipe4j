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

class Processor {
	private static final char SLASH = '/';
	private Stack<String> stack = new Stack<String>();
	private String bufferedText = null;
	private static final Pattern NON_BLANK_PATTERN = Pattern.compile("\\S");

	Map<String, String> process(XMLEvent event) {
		Map<String, String> paths = new TreeMap<String, String>();
		if (event.isStartElement()) {
			bufferedText = null;
			StartElement element = (StartElement) event;
			stack.push(element.getName().toString());

			String xpath = getPath(stack);
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
				paths.put(xpath + "/@" + attName, attribute.getValue());
			}
		} else if (event.isEndElement()) {
			EndElement element = event.asEndElement();
			String xpath = getPath(stack);
			String startName = stack.pop();
			if (!element.getName().toString().equals(startName)) {
				throw new RuntimeException("Element mismatch! Started "
						+ startName + " but ended "
						+ element.getName().toString());
			}
			if (bufferedText != null
					&& NON_BLANK_PATTERN.matcher(bufferedText).find()) {
				paths.put(xpath + "/text()", bufferedText);
				bufferedText = null;
			}
			// System.out.println("Back to " + getPath(stack));
		} else if (event.isCharacters()) {
			Characters characters = (Characters) event;
			bufferedText = characters.getData();
		}

		return paths;
	}

	private String getPath(Stack<String> stack) {
		StringBuilder sb = new StringBuilder();
        for (String string : stack) {
            sb.append(SLASH).append(string);
        }
		return sb.toString();
	}
}
