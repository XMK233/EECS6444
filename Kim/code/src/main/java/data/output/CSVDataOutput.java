package data.output;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import data.structure.ClassMetrics;
import data.structure.MethodMetrics;

public class CSVDataOutput {
	public void outputCSVFile(String version, Map<String, ClassMetrics> classMetrics) {
		try {
			FileWriter csvWriter = new FileWriter(String.format("./src/main/resources/eclipse-%s-metrics.csv", version));
			writeHeaders(csvWriter);
			
			StringBuilder sb = new StringBuilder();
			
			for(String className : classMetrics.keySet()) {
				ClassMetrics metricOfClass = classMetrics.get(className);
				
				sb.append(metricOfClass.getClassName());
				sb.append(",");
				sb.append(metricOfClass.getLoc());
				sb.append(",");
				sb.append(metricOfClass.getAnonymousClassesQty());
				sb.append(",");
				sb.append(metricOfClass.getNumOfStaticMethod());
				sb.append(",");
				sb.append(metricOfClass.getNumOfStaticField());
				sb.append(",");
				sb.append(metricOfClass.getNumOfMethod());
				sb.append(",");
				sb.append(metricOfClass.getNumOfField());
				
				MethodMetrics metricOfMethod = metricOfClass.getMethodMetrics();
				if(metricOfMethod != null) {
					sb.append(",");
					sb.append(metricOfMethod.getTotalLoc());
					sb.append(",");
					sb.append(metricOfMethod.getMaxLoc());
					sb.append(",");
					sb.append(metricOfMethod.getAverageLoc());
					sb.append(",");
					
					sb.append(metricOfMethod.getTotalNestedBlockDepth());
					sb.append(",");
					sb.append(metricOfMethod.getMaxNestedBlockDepth());
					sb.append(",");
					sb.append(metricOfMethod.getAverageNestedBlockDepth());
					sb.append(",");
					
					sb.append(metricOfMethod.getTotalParameters());
					sb.append(",");
					sb.append(metricOfMethod.getMaxParameters());
					sb.append(",");
					sb.append(metricOfMethod.getAverageParameters());
					sb.append(",");
					
					sb.append(metricOfMethod.getTotalMcCabe());
					sb.append(",");
					sb.append(metricOfMethod.getMaxMcCabe());
					sb.append(",");
					sb.append(metricOfMethod.getAverageMcCabe());
					sb.append(",");
					
					sb.append(metricOfMethod.getTotalFanOut());
					sb.append(",");
					sb.append(metricOfMethod.getMaxFanOut());
					sb.append(",");
					sb.append(metricOfMethod.getAverageFanOut());
				}
				sb.append("\n");
			}
			csvWriter.append(sb.toString());
			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeHeaders(FileWriter csvWriter) throws IOException {
		csvWriter.append("className,");
		csvWriter.append("loc,");
		csvWriter.append("anonymousClassesQty,");
		csvWriter.append("numOfStaticMethod,");
		csvWriter.append("numOfStaticField,");
		csvWriter.append("numOfMethod,");
		csvWriter.append("numOfField,");
		csvWriter.append("methodTotalLoc,");
		csvWriter.append("methodMaxLoc,");
		csvWriter.append("methodAverageLoc,");
		csvWriter.append("totalNestedBlockDepth,");
		csvWriter.append("maxNestedBlockDepth,");
		csvWriter.append("averageNestedBlockDepth,");
		csvWriter.append("totalParameters,");
		csvWriter.append("maxParameters,");
		csvWriter.append("averageParameters,");
		csvWriter.append("totalMcCabe,");
		csvWriter.append("maxMcCabe,");
		csvWriter.append("averageMcCabe,");
		csvWriter.append("totalFanOut,");
		csvWriter.append("maxFanOut,");
		csvWriter.append("averageFanOut");
		csvWriter.append("\n");
	}
}
