package data.loader;

import java.util.Map;

import data.structure.MethodMetrics;

public interface IMethodMetricsDataLoader {
	public Map<String, MethodMetrics> loadMethodMetrics(String filePath);
	
}
