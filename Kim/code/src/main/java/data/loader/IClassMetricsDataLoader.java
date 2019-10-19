package data.loader;

import java.util.Map;

import data.structure.FileMetrics;

public interface IClassMetricsDataLoader {
	public Map<String, FileMetrics> loadClassMetrics(String classPath); 
}
