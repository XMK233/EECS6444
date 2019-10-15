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
import data.structure.ClassMetrics;
import data.structure.MethodMetrics;

public class Main {
	public static final String XLSX_CLASS_FILE_PATH = "./src/main/resources/class.xlsx";
	public static final String XLSX_METRIC_FILE_PATH = "./src/main/resources/method.xlsx";

	private static final IMethodMetricsDataLoader METHOD_DATALOADER = new MethodMetricsDataLoader();
	private static final IMethodMetricsProcessor METHOD_METRICS_PROCESSOR = new MethodMetricsProcessor();

	private static final IClassMetricsDataLoader CLASS_DATALOADER = new ClassMetricsDataLoader();
	
	private static final IMetricsJoiner METRICS_JOINER = new MetricsJoiner();
	
	private static final CSVDataOutput DATA_OUTPUT = new CSVDataOutput();

	private static final SanityTest SANITY_TEST = new SanityTest();

	public static void main(String[] args) {

		Map<String, MethodMetrics> methodMetrics = METHOD_DATALOADER.loadMethodMetrics(XLSX_METRIC_FILE_PATH);

		methodMetrics = METHOD_METRICS_PROCESSOR.processMethodMetrics(methodMetrics);

		//System.out.println("Data after processing: " + methodMetrics);

		SANITY_TEST.sanityTest(methodMetrics);

		Map<String, ClassMetrics> classMetrics = CLASS_DATALOADER.loadClassMetrics(XLSX_CLASS_FILE_PATH);

		//System.out.println("ClassMetrics: " + classMetrics);

		classMetrics = METRICS_JOINER.joinMetrics(classMetrics, methodMetrics);
		
		DATA_OUTPUT.outputCSVFile("3.1", classMetrics);
	}

}
