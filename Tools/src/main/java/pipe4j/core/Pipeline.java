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
package pipe4j.core;

import pipe4j.core.connector.PipeConnectorHelper;

/**
 * Utility class to assemble and execute pipelines.
 * 
 * @author bbennett
 */
public class Pipeline {
	@SuppressWarnings("rawtypes")
	public static PipelineInfo run(Pipe... pipeline) {
		return run(-1, pipeline);
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public static PipelineInfo run(long timeoutMilis, Pipe... pipeline) {
		if (pipeline == null || pipeline.length < 2) {
			throw new IllegalArgumentException("Need at least 2 pipes!");
		}

		final long timestamp = System.currentTimeMillis();
		ThreadGroup threadGroup = new ThreadGroup("Pipeline");

		PipeThread[] threads = new PipeThread[pipeline.length];
		threads[0] = new PipeThread(pipeline[0]);
		threads[0].setIn(Null.INSTANCE);
		for (int i = 1; i < pipeline.length; i++) {
			threads[i] = new PipeThread(pipeline[i]);
			PipeConnectorHelper.connect(threads[i - 1], threads[i]);
		}
		threads[threads.length - 1].setOut(Null.INSTANCE);

		for (Thread thread : threads) {
			thread.start();
		}

		for (PipeThread thread : threads) {
			if (timeoutMilis > 0) {
				// Wait for the requested perid, minus elapsed time
				long discountedTimeout = Math
						.max(0, timeoutMilis
								- (System.currentTimeMillis() - timestamp));
				try {
					thread.join(discountedTimeout);
				} catch (InterruptedException e) {
				}
				if (thread.isAlive()) {
					thread.getPipe().cancel();
					try {
						Thread.sleep(100);
					} catch (InterruptedException ignored) {
					}
				}

				if (thread.isAlive()) {
					thread.interrupt();
					thread.close();
					try {
						Thread.sleep(100);
					} catch (InterruptedException ignored) {
					}
				}

				if (thread.isAlive()) {
					thread.stop(new InterruptedException("Thread stopped!"));
				}
			} else {
				// Wait forever
				try {
					thread.join();
				} catch (InterruptedException e) {
				}
			}
		}

		// If exception happened, get first found
		PipelineInfo info = new PipelineInfo(threadGroup);
		for (int i = 0; i < threads.length; i++) {
			if (threads[i].hasError()) {
				info.setException(threads[i].getException());
				break;
			}
		}
		return info;
	}
}
