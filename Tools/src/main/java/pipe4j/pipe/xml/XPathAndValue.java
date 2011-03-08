package pipe4j.pipe.xml;

public class XPathAndValue {
	private final String xpath;
	private final String value;

	public XPathAndValue(String xpath, String value) {
		super();
		this.xpath = xpath;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getXpath() {
		return xpath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((xpath == null) ? 0 : xpath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XPathAndValue other = (XPathAndValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (xpath == null) {
			if (other.xpath != null)
				return false;
		} else if (!xpath.equals(other.xpath))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "XPathAndValue [xpath=" + xpath + ", value=" + value + "]";
	}
}
