/*
 * Copyright (C) 2010 Bernardo O. Bennett
 * 
 * This file is part of Stream4j.
 * 
 * Stream4j is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Stream4j is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the Lesser GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the Lesser GNU General Public License
 * along with Stream4j. If not, see <http://www.gnu.org/licenses/>.
 */
package pipe.core;

import java.io.File;

public class TestUtils {
	public static final String txtMD5 = "b91ff45cda8fe9b05ebd9bd38ddef48a";
	public static String txtInFilePath;
	public static String txtOutFilePath;

	static {
		String pathSep = File.separator;
		txtInFilePath = new StringBuilder(System.getProperty("user.dir"))
				.append(pathSep).append("src").append(pathSep).append("test")
				.append(pathSep).append("java").append(pathSep)
				.append("sample.txt").toString();
		txtOutFilePath = new StringBuilder(System.getProperty("user.dir"))
				.append(pathSep).append("target").append(pathSep)
				.append("test-classes").append(pathSep)
				.append("sample_out.txt").toString();
	}
}
