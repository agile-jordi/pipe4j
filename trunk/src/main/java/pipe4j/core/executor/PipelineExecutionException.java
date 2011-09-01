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
import java.util.Collections;
import java.util.List;

import pipe4j.core.ExceptionWrapper;

/**
 * Wrapper exception raised when one or more exceptions are raised during
 * pipeline execution.
 * 
 * @author bbennett
 */
public class PipelineExecutionException extends Exception implements
		ExceptionWrapper {
	private static final long serialVersionUID = 5967585627978604026L;
	private List<Exception> exceptionList = new ArrayList<Exception>();

	public PipelineExecutionException() {
		super("One or more exceptions raised during pipeline execution");
	}

	@Override
	public void addException(Exception exception) {
		this.exceptionList.add(exception);
	}

	@Override
	public List<Exception> getExceptionList() {
		return Collections.unmodifiableList(this.exceptionList);
	}

	@Override
	public void aggregate(ExceptionWrapper exceptionWrapper) {
		this.exceptionList.addAll(exceptionWrapper.getExceptionList());
	}
	
	@Override
	public boolean hasExceptions() {
		return !this.exceptionList.isEmpty();
	}
}
