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
package pipe4j.core.connector;

import java.io.Closeable;
import java.util.Collection;
import java.util.HashSet;

import pipe4j.core.CallablePipe;

/**
 * Register pipe connector implementations and offers utility method to connect
 * 2 pipe threads.
 * 
 * @author bbennett
 */
public class PipeConnectorHelper {
	private static Collection<PipeConnector> connectors = new HashSet<PipeConnector>();

	static {
		registerPipeConnector(new BlockingBufferPipeConnector());
		registerPipeConnector(new StreamPipeConnector());
	}

	/**
	 * Register a connector implementation.
	 * 
	 * @param connector
	 *            Connector implementation
	 */
	public static void registerPipeConnector(PipeConnector connector) {
		connectors.add(connector);
	}

	/**
	 * Connect 2 pipe threads.
	 * 
	 * @param previous
	 *            First callable pipe
	 * @param callablePipe
	 *            Second callable pipe
	 */
	public static void connect(CallablePipe<Closeable, Closeable> previous,
			CallablePipe<Closeable, Closeable> callablePipe, boolean debug) {
		PipeConnector connector = null;

		for (PipeConnector conn : connectors) {
			if (conn.supports(previous, callablePipe)) {
				connector = conn;
				break;
			}
		}
		if (connector == null) {
			throw new IllegalArgumentException("Don't know how to connect "
					+ previous.getPipe().toString() + " with "
					+ callablePipe.getPipe().toString());
		}

		connector.connect(previous, callablePipe, debug);
	}
}
