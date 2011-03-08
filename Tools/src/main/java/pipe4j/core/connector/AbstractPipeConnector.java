package pipe4j.core.connector;

import java.lang.reflect.Method;

import pipe4j.core.Pipe;

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
	public boolean supports(Pipe<?, ?> prev, Pipe<?, ?> next) {
		Method prevMethod = getRunMethod(prev.getClass());
		Method nextMethod = getRunMethod(next.getClass());

		Class<?> out = prevMethod.getParameterTypes()[1];
		Class<?> in = nextMethod.getParameterTypes()[0];

		return supports(in, out);
	}

	protected abstract boolean supports(Class<?> in, Class<?> out);
}