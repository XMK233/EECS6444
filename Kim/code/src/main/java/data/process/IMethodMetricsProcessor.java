package data.process;

import java.util.List;
import java.util.Map;

import data.structure.MethodMetrics;

public interface IMethodMetricsProcessor {
	public Map<String, MethodMetrics> processMethodMetrics(Map<String, MethodMetrics> rawInputMetrics);
	
	public default int calculateSumOfAll(List<Integer> inputMetrics) {
		return inputMetrics.stream().mapToInt(i -> i).sum();
	}
	
	public default int calculateMaxOfAll(List<Integer> inputMetrics) {
		return inputMetrics.stream().mapToInt(i -> i).max().orElse(0);
	}
	
	public default double calculateAverageOfAll(List<Integer> inputMetrics) {
		return inputMetrics.stream().mapToInt(i -> i).average().orElse(0);
	}
	
	public default double roundUp(double input) {
		return Math.round(input * 100.0)/100.0;
	}
}
