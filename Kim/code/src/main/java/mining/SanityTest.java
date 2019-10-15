package mining;

import java.util.Map;

import org.testng.Assert;

import data.structure.MethodMetrics;

public class SanityTest {
	public void sanityTest(Map<String, MethodMetrics> methodMetrics) {
		String className = "org.eclipse.jdt.core.CheckDebugAttributes";
		
		MethodMetrics checkDebugAttributesMetrics = methodMetrics.get(className);
		
		// LOC assertion
		Assert.assertEquals(checkDebugAttributesMetrics.getTotalLoc(), 50);
		Assert.assertEquals(checkDebugAttributesMetrics.getMaxLoc(), 34);
		Assert.assertEquals(checkDebugAttributesMetrics.getAverageLoc(), 12.5);
		
		// Nested Block Depth assertion
		Assert.assertEquals(checkDebugAttributesMetrics.getTotalNestedBlockDepth(), 6);
		Assert.assertEquals(checkDebugAttributesMetrics.getMaxNestedBlockDepth(), 4);
		Assert.assertEquals(checkDebugAttributesMetrics.getAverageNestedBlockDepth(), 1.5);
		
		// Parameters assertion
		Assert.assertEquals(checkDebugAttributesMetrics.getTotalParameters(), 3);
		Assert.assertEquals(checkDebugAttributesMetrics.getMaxParameters(), 1);
		Assert.assertEquals(checkDebugAttributesMetrics.getAverageParameters(), 0.75);
		
		// McCabe assertion
		Assert.assertEquals(checkDebugAttributesMetrics.getTotalMcCabe(), 15);
		Assert.assertEquals(checkDebugAttributesMetrics.getMaxMcCabe(), 9);
		Assert.assertEquals(checkDebugAttributesMetrics.getAverageMcCabe(), 3.75);
		
		// Fan Out assertion
		Assert.assertEquals(checkDebugAttributesMetrics.getTotalFanOut(), 15);
		Assert.assertEquals(checkDebugAttributesMetrics.getMaxFanOut(), 12);
		Assert.assertEquals(checkDebugAttributesMetrics.getAverageFanOut(), 3.75);
	}
}
