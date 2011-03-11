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
package pipe4j.pipe;

import java.util.concurrent.atomic.AtomicBoolean;

import pipe4j.core.Pipe;

/**
 * Utility abstract parent for most pipe implementations. Supports cancel flag.
 * 
 * @author bbennett
 * 
 * @param <I>
 *            Pipe input type
 * @param <O>
 *            Pipe output type
 */
public abstract class AbstractPipe<I, O> implements Pipe<I, O> {
	private AtomicBoolean cancel = new AtomicBoolean(false);

	@Override
	public void cancel() {
		cancel.set(true);
	}

	/**
	 * @return True if cancel was requested
	 */
	protected boolean cancelled() {
		return this.cancel.get();
	}
}
