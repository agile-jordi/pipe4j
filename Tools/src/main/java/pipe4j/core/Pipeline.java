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

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pipe4j.core.Result.Type;
import pipe4j.core.connector.PipeConnectorHelper;

/**
 * Utility class to assemble and execute pipelines.
 * 
 * @author bbennett
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Pipeline {
	public static PipelineInfo run(Pipe... pipeline) {
		return run(0, pipeline);
	}

	public static PipelineInfo run(long timeoutMilliseconds, Pipe... pipeline) {
		return run(timeoutMilliseconds, false, pipeline);
	}

	public static PipelineInfo run(long timeoutMilliseconds, boolean debug,
			Pipe... pipeline) {
		if (pipeline == null || pipeline.length < 2) {
			throw new IllegalArgumentException("Need at least 2 pipes!");
		}

		List<CallablePipe<Closeable, Closeable>> callables = new ArrayList<CallablePipe<Closeable, Closeable>>(
				pipeline.length);
		CallablePipe<Closeable, Closeable> previous = new CallablePipe<Closeable, Closeable>(
				pipeline[0]);
		callables.add(previous);
		previous.setIn(Null.INSTANCE);

		for (Pipe<Closeable, Closeable> pipe : pipeline) {
			if (pipe == pipeline[0])
				continue;
			CallablePipe<Closeable, Closeable> callablePipe = new CallablePipe<Closeable, Closeable>(
					pipe);
			callables.add(callablePipe);
			connect(previous, callablePipe, debug);
			previous = callablePipe;
		}
		previous.setOut(Null.INSTANCE);

		ExecutorService pool = Executors.newFixedThreadPool(pipeline.length);
		List<Future<Result>> futureList = new ArrayList<Future<Result>>(
				pipeline.length);
		
		final long startTimestamp = System.currentTimeMillis();
		for (CallablePipe<Closeable, Closeable> callablePipe : callables) {
			futureList.add(pool.submit(callablePipe));
		}
		pool.shutdown();

		boolean aborted = abortIfNecessary(timeoutMilliseconds, pool, callables);
		final long endTimestamp = System.currentTimeMillis();
		
		List<Result> resultList = new ArrayList<Result>(pipeline.length);
		PipelineInfo info = new PipelineInfo(resultList);
		info.setStartTimestamp(startTimestamp);
		info.setEndTimestamp(endTimestamp);
		info.setTimeoutExceeded(aborted);

		for (Future<Result> future : futureList) {
			try {
				Result result = future.get();

				if (result.getType() == Type.FAILURE) {
					info.setResult(Type.FAILURE);
				}

				// If exception happened, get first found
				if (!info.hasError() && result.hasException()) {
					info.setException(result.getException());
				}
				resultList.add(result);
			} catch (InterruptedException e) {
				// TODO
			} catch (ExecutionException e) {
				// If exception was thrown, it was caught and stored inside
				// Result
			}
		}

		if (aborted) {
			info.setResult(Type.FAILURE);
		}

		return info;
	}

	private static boolean abortIfNecessary(long timeoutMilliseconds,
			ExecutorService pool,
			List<CallablePipe<Closeable, Closeable>> callables) {
		boolean terminated = false;
		boolean rv = false;
		try {
			if (timeoutMilliseconds <= 0) {
				terminated = pool.awaitTermination(Long.MAX_VALUE,
						TimeUnit.DAYS);
			} else {
				terminated = pool.awaitTermination(timeoutMilliseconds,
						TimeUnit.MILLISECONDS);
			}
		} catch (InterruptedException e1) {
		}

		// Timeout expired and pipeline is still running
		if (!terminated) {
			rv = true;

			// Be nice and request each pipe to cancel
			for (CallablePipe<Closeable, Closeable> callablePipe : callables) {
				callablePipe.getPipe().cancel();
			}

			// Check if pipes gracefully terminated
			try {
				terminated = pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
			}

			// If not use brute force
			if (!terminated) {
				pool.shutdownNow();
			}
		}
		return rv;
	}

	private static void connect(CallablePipe<Closeable, Closeable> previous,
			CallablePipe<Closeable, Closeable> callablePipe, boolean debug) {
		PipeConnectorHelper.connect(previous, callablePipe, debug);
	}
}
