package pipe4j.core.connector.profile;

import java.io.PrintStream;
import java.util.List;

import pipe4j.core.PipelineInfo;
import pipe4j.core.Result;

public class ProfileUtils {
	public static void printReport(PipelineInfo info) {
		printReport(info, System.out);
	}

	public static void printReport(PipelineInfo info, PrintStream printStream) {
		List<Result> resultList = info.getResultList();
		Result head = resultList.get(0);
		Result tail = resultList.get(resultList.size() - 1);

		printStream.println("Head " + head.getPipeName() + " : write wait "
				+ head.getWriteWaitTimeMilliseconds() + " ms");

		for (int i = 1; i < resultList.size() - 2; i++) {
			Result previous = resultList.get(i - 1);
			Result pipe = resultList.get(i);
			Result next = resultList.get(i + 1);

			boolean suspect = previous.getWriteWaitTimeMilliseconds() > pipe
					.getReadWaitTimeMilliseconds()
					|| pipe.getWriteWaitTimeMilliseconds() < next
							.getReadWaitTimeMilliseconds();
			
			printStream.println("Pipe " + pipe.getPipeName() + " : read wait "
					+ pipe.getReadWaitTimeMilliseconds() + " ms, write wait "
					+ pipe.getWriteWaitTimeMilliseconds() + " ms"
					+ (suspect ? " (suspect) " : ""));

			previous = pipe;
		}

		printStream.println("Tail " + tail.getPipeName() + " : read wait "
				+ tail.getReadWaitTimeMilliseconds() + " ms");
	}
}
