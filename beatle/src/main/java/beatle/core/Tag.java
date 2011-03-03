package beatle.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tag {
	private String name;
	private String value;
	private Map<String, String> attributeMap = new HashMap<String, String>();
	private List<Tag> childrenList = new ArrayList<Tag>();
	private Tag parent;

	public Tag() {
		super();
	}
	
	public Tag(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Tag setName(String name) {
		this.name = name;
		return this;
	}

	public String getValue() {
		return value;
	}

	public Tag setValue(String value) {
		this.value = value;
		return this;
	}

	public Map<String, String> getAttributeMap() {
		return attributeMap;
	}

	public Tag setAttributeMap(Map<String, String> attributeMap) {
		this.attributeMap = attributeMap;
		return this;
	}

	public List<Tag> getChildrenList() {
		return childrenList;
	}

	public Tag setChildrenList(List<Tag> childrenList) {
		this.childrenList = childrenList;
		return this;
	}

	public String getAttribute(String key) {
		return attributeMap.get(key);
	}

	public Tag setAttribute(String key, String value) {
		attributeMap.put(key, value);
		return this;
	}

	public Tag addChild(Tag e) {
		childrenList.add(e);
		return this;
	}

	public Tag getParent() {
		return parent;
	}

	public Tag setParent(Tag parent) {
		this.parent = parent;
		return this;
	}

	@Override
	public String toString() {
		return "Tag [name=" + name + ", value=" + value + ", attributeMap="
				+ attributeMap + ", childrenList=" + childrenList + "]";
	}
}
