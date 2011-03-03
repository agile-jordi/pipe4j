package beatle.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.taskdefs.Echo;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;

public class Beatle {
	public void execute(Tag tag) {
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
		sb.append("_" + tag.getName() + ".execute();");
		System.out.println(sb.toString());
		ParserContext ctx = ParserContext.create();
		ctx.addImport("echo", Echo.class);
		Map<String, Object> vars = new HashMap<String, Object>();
		Serializable compiledExpression = MVEL.compileExpression(sb.toString(),
				ctx);
		MVEL.executeExpression(compiledExpression, vars);
		
	}
}
