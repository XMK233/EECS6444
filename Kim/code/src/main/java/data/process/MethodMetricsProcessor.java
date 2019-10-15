package data.process;

import java.util.List;
import java.util.Map;

import data.structure.MethodMetrics;

public class MethodMetricsProcessor implements IMethodMetricsProcessor {
	@Override
	public Map<String, MethodMetrics> processMethodMetrics(Map<String, MethodMetrics> rawInputMetrics) {
		rawInputMetrics.keySet().forEach(key -> {
			MethodMetrics targetClass= rawInputMetrics.get(key);
			
			List<Integer> allLineOfCode = targetClass.getAllLineOfCode();
			targetClass.setTotalLoc(calculateSumOfAll(allLineOfCode));
			targetClass.setMaxLoc(calculateMaxOfAll(allLineOfCode));
			targetClass.setAverageLoc(roundUp(calculateAverageOfAll(allLineOfCode)));
			
			List<Integer> nestedBlockDepth = targetClass.getNestedBlockDepth();
			targetClass.setTotalNestedBlockDepth(calculateSumOfAll(nestedBlockDepth));
			targetClass.setMaxNestedBlockDepth(calculateMaxOfAll(nestedBlockDepth));
			targetClass.setAverageNestedBlockDepth(roundUp(calculateAverageOfAll(nestedBlockDepth)));
			
			List<Integer> parameters = targetClass.getParameters();
			targetClass.setTotalParameters(calculateSumOfAll(parameters));
			targetClass.setMaxParameters(calculateMaxOfAll(parameters));
			targetClass.setAverageParameters(roundUp(calculateAverageOfAll(parameters)));
			
			List<Integer> mcCabeComplexity = targetClass.getMcCabeComplexity();
			targetClass.setTotalMcCabe(calculateSumOfAll(mcCabeComplexity));
			targetClass.setMaxMcCabe(calculateMaxOfAll(mcCabeComplexity));
			targetClass.setAverageMcCabe(roundUp(calculateAverageOfAll(mcCabeComplexity)));
			
			List<Integer> fanOut = targetClass.getFanOut();
			targetClass.setTotalFanOut(calculateSumOfAll(fanOut));
			targetClass.setMaxFanOut(calculateMaxOfAll(fanOut));
			targetClass.setAverageFanOut(roundUp(calculateAverageOfAll(fanOut)));
		});
		return rawInputMetrics;
	}

}
