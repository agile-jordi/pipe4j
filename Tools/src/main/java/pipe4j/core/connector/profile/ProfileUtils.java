package pipe4j.core.connector.profile;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.List;

import pipe4j.core.PipelineInfo;
import pipe4j.core.Result;

public class ProfileUtils {
	public static void printReport(PipelineInfo info) {
		printReport(info, System.out);
	}

	public static void printReport(PipelineInfo info, PrintStream printStream) {
		NumberFormat NF = NumberFormat.getPercentInstance();
		NF.setMinimumFractionDigits(2);
		NF.setMaximumFractionDigits(2);

		List<Result> resultList = info.getResultList();
		Result head = resultList.get(0);
		Result tail = resultList.get(resultList.size() - 1);

		long totalTimestampMilliseconds = tail.getEndTimestamp()
				- head.getStartTimestamp();
		long totalWaitMilliseconds = head.getWriteWaitTimeMilliseconds()
				+ tail.getReadWaitTimeMilliseconds();

		printStream
				.println("Head "
						+ head.getPipeName()
						+ " : time "
						+ ((head.getEndTimestamp() - head.getStartTimestamp()))
						+ ", write wait "
						+ head.getWriteWaitTimeMilliseconds()
						+ " ms ("
						+ NF.format((double) (head
								.getWriteWaitTimeMilliseconds() / (head
								.getEndTimestamp() - head.getStartTimestamp())))
						+ ")");

		for (int i = 1; i < resultList.size() - 2; i++) {
			Result previous = resultList.get(i - 1);
			Result pipe = resultList.get(i);
			Result next = resultList.get(i + 1);

			totalWaitMilliseconds += pipe.getReadWaitTimeMilliseconds()
					+ pipe.getWriteWaitTimeMilliseconds();

			boolean suspect = previous.getWriteWaitTimeMilliseconds() > pipe
					.getReadWaitTimeMilliseconds()
					|| pipe.getWriteWaitTimeMilliseconds() < next
							.getReadWaitTimeMilliseconds();

			printStream
					.println("Pipe "
							+ pipe.getPipeName()
							+ " : time "
							+ ((pipe.getEndTimestamp() - pipe
									.getStartTimestamp()))
							+ " read wait "
							+ pipe.getReadWaitTimeMilliseconds()
							+ " ms, write wait "
							+ pipe.getWriteWaitTimeMilliseconds()
							+ " ms ("
							+ NF.format((double) (pipe
									.getReadWaitTimeMilliseconds() / (pipe
									.getEndTimestamp() - pipe
									.getStartTimestamp()))) + ")"
							+ (suspect ? " (suspect) " : ""));

			previous = pipe;
		}

		printStream
				.println("Tail "
						+ tail.getPipeName()
						+ " : time "
						+ ((tail.getEndTimestamp() - tail.getStartTimestamp()))
						+ ", read wait "
						+ tail.getReadWaitTimeMilliseconds()
						+ " ms("
						+ NF.format((double) (tail
								.getReadWaitTimeMilliseconds() / (tail
								.getEndTimestamp() - tail.getStartTimestamp())))
						+ ")");
		printStream.println();
		printStream.println("Total time: " + totalTimestampMilliseconds);
		printStream.println("Total wait time: " + totalWaitMilliseconds);
	}
}
