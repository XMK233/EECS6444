package data.structure;

import java.util.ArrayList;
import java.util.List;

public class MethodMetrics {
	private String className;
	
	private List<Integer> allLineOfCode = new ArrayList<>();
	private int totalLoc;
	private int maxLoc;
	private double averageLoc;
	
	private List<Integer> nestedBlockDepth = new ArrayList<>();
	private int totalNestedBlockDepth;
	private int maxNestedBlockDepth;
	private double averageNestedBlockDepth;
	
	private List<Integer> parameters = new ArrayList<>();
	private int totalParameters;
	private int maxParameters;
	private double averageParameters;
	
	private List<Integer> mcCabeComplexity = new ArrayList<>();
	private int totalMcCabe;
	private int maxMcCabe;
	private double averageMcCabe;
	
	private List<Integer> fanOut = new ArrayList<>();
	private int totalFanOut;
	private int maxFanOut;
	private double averageFanOut;
	
	public MethodMetrics(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Integer> getAllLineOfCode() {
		return allLineOfCode;
	}

	public void setAllLineOfCode(List<Integer> allLineOfCode) {
		this.allLineOfCode = allLineOfCode;
	}

	public double getAverageLoc() {
		return averageLoc;
	}

	public void setAverageLoc(double averageLoc) {
		this.averageLoc = averageLoc;
	}

	public int getMaxLoc() {
		return maxLoc;
	}

	public void setMaxLoc(int maxLoc) {
		this.maxLoc = maxLoc;
	}

	public int getTotalLoc() {
		return totalLoc;
	}

	public void setTotalLoc(int totalLoc) {
		this.totalLoc = totalLoc;
	}

	public List<Integer> getNestedBlockDepth() {
		return nestedBlockDepth;
	}

	public void setNestedBlockDepth(List<Integer> nestedBlockDepth) {
		this.nestedBlockDepth = nestedBlockDepth;
	}

	public int getTotalNestedBlockDepth() {
		return totalNestedBlockDepth;
	}

	public void setTotalNestedBlockDepth(int totalNestedBlockDepth) {
		this.totalNestedBlockDepth = totalNestedBlockDepth;
	}

	public int getMaxNestedBlockDepth() {
		return maxNestedBlockDepth;
	}

	public void setMaxNestedBlockDepth(int maxNestedBlockDepth) {
		this.maxNestedBlockDepth = maxNestedBlockDepth;
	}

	public double getAverageNestedBlockDepth() {
		return averageNestedBlockDepth;
	}

	public void setAverageNestedBlockDepth(double averageNestedBlockDepth) {
		this.averageNestedBlockDepth = averageNestedBlockDepth;
	}

	public List<Integer> getParameters() {
		return parameters;
	}

	public void setParameters(List<Integer> parameters) {
		this.parameters = parameters;
	}

	public int getTotalParameters() {
		return totalParameters;
	}

	public void setTotalParameters(int totalParameters) {
		this.totalParameters = totalParameters;
	}

	public int getMaxParameters() {
		return maxParameters;
	}

	public void setMaxParameters(int maxParameters) {
		this.maxParameters = maxParameters;
	}

	public double getAverageParameters() {
		return averageParameters;
	}

	public void setAverageParameters(double averageParameters) {
		this.averageParameters = averageParameters;
	}

	public List<Integer> getMcCabeComplexity() {
		return mcCabeComplexity;
	}

	public void setMcCabeComplexity(List<Integer> mcCabeComplexity) {
		this.mcCabeComplexity = mcCabeComplexity;
	}

	public int getTotalMcCabe() {
		return totalMcCabe;
	}

	public void setTotalMcCabe(int totalMcCable) {
		this.totalMcCabe = totalMcCable;
	}

	public int getMaxMcCabe() {
		return maxMcCabe;
	}

	public void setMaxMcCabe(int maxMcCable) {
		this.maxMcCabe = maxMcCable;
	}

	public double getAverageMcCabe() {
		return averageMcCabe;
	}

	public void setAverageMcCabe(double averageMcCable) {
		this.averageMcCabe = averageMcCable;
	}

	public List<Integer> getFanOut() {
		return fanOut;
	}

	public void setFanOut(List<Integer> fanOut) {
		this.fanOut = fanOut;
	}

	public int getTotalFanOut() {
		return totalFanOut;
	}

	public void setTotalFanOut(int totalFanOut) {
		this.totalFanOut = totalFanOut;
	}

	public int getMaxFanOut() {
		return maxFanOut;
	}

	public void setMaxFanOut(int maxFanOut) {
		this.maxFanOut = maxFanOut;
	}

	public double getAverageFanOut() {
		return averageFanOut;
	}

	public void setAverageFanOut(double averageFanOut) {
		this.averageFanOut = averageFanOut;
	}

	@Override
	public String toString() {
		return "MethodMetrics [className=" + className + ", totalLoc=" + totalLoc + ", maxLoc=" + maxLoc
				+ ", averageLoc=" + averageLoc + ", totalNestedBlockDepth=" + totalNestedBlockDepth
				+ ", maxNestedBlockDepth=" + maxNestedBlockDepth + ", averageNestedBlockDepth="
				+ averageNestedBlockDepth + ", totalParameters=" + totalParameters + ", maxParameters=" + maxParameters
				+ ", averageParameters=" + averageParameters + ", totalMcCabe=" + totalMcCabe + ", maxMcCabe="
				+ maxMcCabe + ", averageMcCabe=" + averageMcCabe + ", totalFanOut=" + totalFanOut + ", maxFanOut="
				+ maxFanOut + ", averageFanOut=" + averageFanOut + "]";
	}
}
