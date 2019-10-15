package data.loader;

import java.util.Map;

import data.structure.ClassMetrics;

public interface IClassMetricsDataLoader {
	public Map<String, ClassMetrics> loadClassMetrics(String classPath); 
}
