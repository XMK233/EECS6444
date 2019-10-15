package data.process;

import java.util.Map;

import data.structure.ClassMetrics;
import data.structure.MethodMetrics;

public class MetricsJoiner implements IMetricsJoiner {
	@Override
	public Map<String, ClassMetrics> joinMetrics(Map<String, ClassMetrics> classMetrics, Map<String, MethodMetrics> methodMetrics) {
		classMetrics.keySet().forEach(key -> {
			ClassMetrics metricForClass = classMetrics.get(key);
			String className = metricForClass.getClassName();
			
			if(methodMetrics.containsKey(className)) {
				metricForClass.setMethodMetrics(methodMetrics.get(className));
			} else {
				System.out.printf("Class name %s does not exist in methodMetrics\n", className);
			}
		});
		
		return classMetrics;
	}

}
