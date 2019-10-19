package data.process;

import java.util.Map;

import data.structure.FileMetrics;
import data.structure.MethodMetrics;

public interface IMetricsJoiner {
	public Map<String, FileMetrics> joinMetrics(Map<String, FileMetrics> classMetrics, Map<String, MethodMetrics> methodMetrics);
}
