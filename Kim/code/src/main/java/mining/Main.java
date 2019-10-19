package mining;

import java.util.Map;

import data.loader.ClassMetricsDataLoader;
import data.loader.IClassMetricsDataLoader;
import data.loader.IMethodMetricsDataLoader;
import data.loader.MethodMetricsDataLoader;
import data.output.CSVDataOutput;
import data.process.IMethodMetricsProcessor;
import data.process.IMetricsJoiner;
import data.process.MethodMetricsProcessor;
import data.process.MetricsJoiner;
import data.structure.FileMetrics;
import data.structure.MethodMetrics;

public class Main {
	public static final String XLSX_CLASS_FILE_PATH = "D:\\eclipsesourcecode\\eclipse-3.5\\class.xlsx";
	public static final String XLSX_METRIC_FILE_PATH = "D:\\eclipsesourcecode\\eclipse-3.5\\method.xlsx";

	private static final IMethodMetricsDataLoader METHOD_DATALOADER = new MethodMetricsDataLoader();
	private static final IMethodMetricsProcessor METHOD_METRICS_PROCESSOR = new MethodMetricsProcessor();

	private static final IClassMetricsDataLoader CLASS_DATALOADER = new ClassMetricsDataLoader();
	
	private static final IMetricsJoiner METRICS_JOINER = new MetricsJoiner();
	
	private static final CSVDataOutput DATA_OUTPUT = new CSVDataOutput();

	public static void main(String[] args) {

		Map<String, MethodMetrics> methodMetrics = METHOD_DATALOADER.loadMethodMetrics(XLSX_METRIC_FILE_PATH);

		methodMetrics = METHOD_METRICS_PROCESSOR.processMethodMetrics(methodMetrics);

		//System.out.println("Data after processing: " + methodMetrics);

		Map<String, FileMetrics> fileMetrics = CLASS_DATALOADER.loadClassMetrics(XLSX_CLASS_FILE_PATH);

		//System.out.println("ClassMetrics: " + classMetrics);

		fileMetrics = METRICS_JOINER.joinMetrics(fileMetrics, methodMetrics);
		
		DATA_OUTPUT.outputCSVFile("3.5", fileMetrics);
	}

}
