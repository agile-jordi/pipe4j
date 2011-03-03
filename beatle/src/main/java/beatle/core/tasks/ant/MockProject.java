package beatle.core.tasks.ant;

import java.util.Map;

import org.apache.tools.ant.Project;

public class MockProject extends Project {
	private Map<String, Object> vars;

	public MockProject(Map<String, Object> vars) {
		super();
		this.vars = vars;
	}

	@Override
	public String getProperty(String propertyName) {
		Object object = this.vars.get(propertyName);
		return object == null ? "" : object.toString();
	}

	@Override
	public String getUserProperty(String propertyName) {
		return getProperty(propertyName);
	}

	@Override
	public void setNewProperty(String name, String value) {
		setProperty(name, value);
	}

	@Override
	public void setProperty(String name, String value) {
		this.vars.put(name, name);
	}

	@Override
	public void setUserProperty(String name, String value) {
		setProperty(name, value);
	}
}
