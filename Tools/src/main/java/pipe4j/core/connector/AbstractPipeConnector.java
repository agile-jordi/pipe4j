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
import java.lang.reflect.Method;

import pipe4j.core.CallablePipe;

/**
 * Abstract parent for connector implementations with utility methods.
 * 
 * @author bbennett
 */
public abstract class AbstractPipeConnector implements PipeConnector {
	protected Method getRunMethod(Class<?> clazz) {
		for (Method method : clazz.getMethods()) {
			if (method.isBridge())
				continue;
			if (method.getName().equals("run")
					&& void.class.equals(method.getReturnType())
					&& method.getParameterTypes().length == 2)
				return method;
		}
		throw new IllegalArgumentException("Cannot find run method for class "
				+ clazz.getName());
	}

	@Override
	public boolean supports(CallablePipe<Closeable, Closeable> prev,
			CallablePipe<Closeable, Closeable> next) {
		Method prevMethod = getRunMethod(prev.getPipe().getClass());
		Method nextMethod = getRunMethod(next.getPipe().getClass());

		Class<?> out = prevMethod.getParameterTypes()[1];
		Class<?> in = nextMethod.getParameterTypes()[0];

		return supports(in, out);
	}

	protected abstract boolean supports(Class<?> in, Class<?> out);
}