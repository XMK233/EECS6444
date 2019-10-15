package data.process;

import java.util.Map;

import data.structure.ClassMetrics;
import data.structure.MethodMetrics;

public interface IMetricsJoiner {
	public Map<String, ClassMetrics> joinMetrics(Map<String, ClassMetrics> classMetrics, Map<String, MethodMetrics> methodMetrics);
}
