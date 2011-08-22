package jgrapht;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import pipe4j.core.CallablePipe;
import pipe4j.core.PipelineInfo;
import pipe4j.core.executor.PipelineExecutor;

public class Pipeline {
	private final Graph<Pipe, Connection> graph = new SimpleGraph<Pipe, Connection>(
			PipeConnector.INSTANCE);

	public Pipeline() {
	}

	public static Pipeline createSimplePipeline(Pipe... pipes) {
		Pipeline pipeline = new Pipeline();

		Pipe source = pipes[0];
		pipeline.addPipe(source);
		for (int i = 1; i < pipes.length; i++) {
			Pipe sink = pipes[i];
			pipeline.addPipe(sink);
			pipeline.addPipeConnection(source, sink);
			source = sink;
		}
		return pipeline;
	}

	public void addPipe(Pipe pipe) {
		this.graph.addVertex(pipe);
	}

	public void addPipeConnection(Pipe source, Pipe sink) {
		this.graph.addEdge(source, sink);
	}

	public PipelineInfo execute(long timeoutMilliseconds) {
		Set<Pipe> pipeSet = this.graph.vertexSet();
		List<CallablePipe<Closeable, Closeable>> callables = new ArrayList<CallablePipe<Closeable, Closeable>>(
				pipeSet.size());
		
		for (Pipe pipe : pipeSet) {
//			callables.add(new CallablePipe<Closeable, Closeable>(pipe));
		}
		return PipelineExecutor.execute(timeoutMilliseconds, callables);
	}
}
