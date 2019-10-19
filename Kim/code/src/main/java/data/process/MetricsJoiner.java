package data.process;

import java.util.Map;

import data.structure.FileMetrics;
import data.structure.MethodMetrics;

public class MetricsJoiner implements IMetricsJoiner {
	@Override
	public Map<String, FileMetrics> joinMetrics(Map<String, FileMetrics> classMetrics, Map<String, MethodMetrics> methodMetrics) {
		classMetrics.keySet().forEach(key -> {
			FileMetrics metricForClass = classMetrics.get(key);
			String className = metricForClass.getFileName();
			
			if(methodMetrics.containsKey(className)) {
				metricForClass.setMethodMetrics(methodMetrics.get(className));
			} else {
				System.out.printf("Class name %s does not exist in methodMetrics\n", className);
			}
		});
		
		return classMetrics;
	}

}
