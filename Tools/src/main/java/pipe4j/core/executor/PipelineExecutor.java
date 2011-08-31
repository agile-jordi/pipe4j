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
package pipe4j.core.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pipe4j.core.CallablePipe;
import pipe4j.core.PipelineInfo;
import pipe4j.core.Result;
import pipe4j.core.Result.Type;

public class PipelineExecutor {
	public static PipelineInfo execute(long timeoutMilliseconds,
			List<CallablePipe> callables) {
		ExecutorService pool = Executors.newFixedThreadPool(callables.size());
		List<Future<Result>> futureList = new ArrayList<Future<Result>>(
				callables.size());

		final long startTimestamp = System.currentTimeMillis();
		for (CallablePipe callablePipe : callables) {
			futureList.add(pool.submit(callablePipe));
		}
		pool.shutdown();

		boolean aborted = abortIfNecessary(timeoutMilliseconds, pool, callables);
		final long endTimestamp = System.currentTimeMillis();

		List<Result> resultList = new ArrayList<Result>(callables.size());
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
			ExecutorService pool, List<CallablePipe> callables) {
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
			for (CallablePipe callablePipe : callables) {
				callablePipe.cancel();
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
}
