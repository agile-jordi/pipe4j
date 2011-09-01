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

import java.util.regex.Pattern;

public class Ignore {
	private Pattern xpathPattern;
	private Pattern valuePattern;

	public Ignore(String xpath) {
		this.xpathPattern = Pattern.compile(xpath);
	}

	public Ignore(String xpath, String value) {
		this(xpath);
		setValue(value);
	}

	public void setValue(String value) {
		this.valuePattern = Pattern.compile(value);
	}

	public boolean shouldIgnore(XPathAndValue xpathAndValue) {
		boolean matches = xpathPattern.matcher(xpathAndValue.getXpath())
				.matches();
		if (matches && this.valuePattern != null) {
			// pattern defined for value
			if (xpathAndValue.getValue() != null) {
				matches = this.valuePattern.matcher(xpathAndValue.getValue())
						.matches();
			} else {
				matches = false;
			}
		}

		return matches;
	}
}
