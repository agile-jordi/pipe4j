package beatle.core;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class XMLParser {
	public Tag parse(InputStream is) throws Exception {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(is);

		Tag tag = null;
		for (int event = parser.next(); event != XMLStreamConstants.END_DOCUMENT; event = parser
				.next()) {
			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				Tag newTag = new Tag();
				newTag.setName(parser.getName().getLocalPart());
				for (int i = 0; i < parser.getAttributeCount(); i++) {
					newTag.setAttribute(parser.getAttributeLocalName(i),
							parser.getAttributeValue(i));
				}
				if (tag != null) {
					newTag.setParent(tag);
					tag.addChild(newTag);
				}
				tag = newTag;
				break;
			case XMLStreamConstants.END_ELEMENT:
				if (tag.getParent() != null) {
					tag = tag.getParent();
				}
				break;
			case XMLStreamConstants.CHARACTERS:
			case XMLStreamConstants.CDATA:
				tag.setValue(parser.getText());
				break;
			}
		}
		parser.close();
		is.close();
		return tag;
	}

}
