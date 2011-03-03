package beatle.core.tasks.ant;

import java.util.Map;

import beatle.core.Tag;

public class AntTagTransformer implements TagTransformer {
	@Override
	public String transformTag(Tag tag) {
		StringBuilder sb = new StringBuilder();
		sb.append("_" + tag.getName() + " = new " + tag.getName() + "();\n");
		for (Map.Entry<String, String> entry : tag.getAttributeMap().entrySet()) {
			sb.append("_" + tag.getName() + "." + entry.getKey() + " = "
					+ entry.getValue() + ";\n");
		}
		if (tag.getValue() != null) {
			sb.append("_" + tag.getName() + ".addText(\"" + tag.getValue()
					+ "\");\n");
		}
		sb.append("_" + tag.getName() + ".execute();\n");
		sb.append("_" + tag.getName() + " = null;\n");
		return sb.toString();
	}
}
