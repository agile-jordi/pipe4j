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
package pipe4j.pipe.compare;

import java.io.PrintWriter;

import pipe4j.core.Connections;
import pipe4j.core.connector.BlockingBuffer;
import pipe4j.pipe.AbstractPipe;

public class TextLineComparatorPipe extends AbstractPipe {
	@Override
	public void run(Connections connections) throws Exception {
		BlockingBuffer inputBufferOne = connections.getNamedInputBuffer("one");
		BlockingBuffer inputBufferTwo = connections.getNamedInputBuffer("two");

		PrintWriter pw = new PrintWriter(connections.getOutputStream(), true);
		String lineOne = (String) inputBufferOne.take();
		String lineTwo = (String) inputBufferTwo.take();

		try {
			while (!cancelled() && lineOne != null && lineTwo != null) {
				if (!lineOne.equals(lineTwo)) {
					pw.print("different");
					return;
				}

				lineOne = (String) inputBufferOne.take();
				lineTwo = (String) inputBufferTwo.take();
			}

			pw.print("identical");
		} finally {
			pw.flush();
		}
	}
}
