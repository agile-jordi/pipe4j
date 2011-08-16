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

/**
 * DTO to hold an XPath and value, if any.
 * 
 * @author bbennett
 */
public class XPathAndValue implements Comparable<XPathAndValue> {

	private final String xpath;
	private final String value;
	private final int line;
	private final int column;

	public XPathAndValue(String xpath, String value) {
		this(xpath, value, -1, -1);
	}

	public XPathAndValue(String xpath, String value, int line, int column) {
		super();
		this.xpath = xpath;
		this.value = value;
		this.line = line;
		this.column = column;
	}

	public String getValue() {
		return value;
	}

	public String getXpath() {
		return xpath;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
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

	@Override
	public int compareTo(XPathAndValue that) {
		if (that == null) {
			return 0;
		}
		
		String xpath1 = this.xpath == null ? "" : this.xpath;
		String xpath2 = that.xpath == null ? "" : that.xpath;
		int rv = xpath1.compareTo(xpath2);
		
		if (rv == 0) {
			String value1 = this.value == null ? "" : this.value;
			String value2 = that.value == null ? "" : that.value;
			rv = value1.compareTo(value2);
		}
		return rv;
	}
}
