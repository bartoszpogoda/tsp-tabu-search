package tsp.algorithm.impl;

import java.io.PrintStream;

import tsp.algorithm.Algorithm;
import tsp.algorithm.Path;
import tsp.instance.Instance;

public class AlgorithmImpl implements Algorithm {

	private int timeLimitSeconds = 600;
	private int diversificationIntervalSeconds = 60;
	
	private PrintStream loggerStream = System.out;
	private int resultLoggingIntervalSeconds = 30;
	
	@Override
	public Path execute(Instance instance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTimeLimit(int seconds) {
		this.timeLimitSeconds = seconds;
	}

	@Override
	public void setDiversificationInterval(int seconds) {
		this.diversificationIntervalSeconds = seconds;
	}

	@Override
	public void setLogging(PrintStream loggerStream, int resultLoggingIntervalSeconds) {
		this.loggerStream = loggerStream;
		this.resultLoggingIntervalSeconds = resultLoggingIntervalSeconds;
	}

}
