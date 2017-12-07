package tsp.algorithm;

import java.io.PrintStream;

import tsp.instance.Instance;

public interface Algorithm {
	Path execute(Instance instance);
	
	void setTimeLimit(int seconds);
	
	void setDiversificationInterval(int seconds);
	
	void setLogging(PrintStream loggerStream, int resultLoggingIntervalSeconds);
}
