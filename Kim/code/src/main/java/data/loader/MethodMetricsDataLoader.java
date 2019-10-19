package data.loader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import data.structure.MethodMetrics;

public class MethodMetricsDataLoader implements IMethodMetricsDataLoader {
	private static final DataFormatter DATA_FORMATTER = new DataFormatter();

	private static int FILE_NAME_INDEX;
	private static int LOC_INDEX;
	private static int MAX_NESTED_BLOCK_INDEX;
	private static int PARAMETERS_INDEX;
	private static int MC_CABE_INDEX;
	private static int FAN_OUT_INDEX;
	
	@Override
	public Map<String, MethodMetrics> loadMethodMetrics(String filePath) {
		Map<String, MethodMetrics> data = new HashMap<>();

		try {
			Workbook workbook = WorkbookFactory.create(new File(filePath));
			Sheet firstSheet = workbook.getSheetAt(0);

			initializeIndexes(firstSheet.getRow(0));
			
			for (int i = 1; i <= firstSheet.getLastRowNum(); i++) {
				Row row = firstSheet.getRow(i);

				String fileName = DATA_FORMATTER.formatCellValue(row.getCell(FILE_NAME_INDEX)).substring(21);
				
				if (!data.containsKey(fileName)) {
					data.put(fileName, new MethodMetrics(fileName));
				}
				
				MethodMetrics methodMetrics = data.get(fileName);
				methodMetrics.getAllLineOfCode().add(getIntValue(row.getCell(LOC_INDEX)));
				methodMetrics.getNestedBlockDepth().add(getIntValue(row.getCell(MAX_NESTED_BLOCK_INDEX)));
				methodMetrics.getParameters().add(getIntValue(row.getCell(PARAMETERS_INDEX)));
				methodMetrics.getMcCabeComplexity().add(getIntValue(row.getCell(MC_CABE_INDEX)));
				methodMetrics.getFanOut().add(getIntValue(row.getCell(FAN_OUT_INDEX)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	private Integer getIntValue(Cell cell) {
		return Integer.valueOf(DATA_FORMATTER.formatCellValue(cell));
	}
	
	private void initializeIndexes(Row row) {
		for(Cell cell : row) {
			switch(DATA_FORMATTER.formatCellValue(cell)) {
			case "file":
				FILE_NAME_INDEX = cell.getColumnIndex();
				break;
				
			case "loc":
				LOC_INDEX = cell.getColumnIndex();
				break;
				
			case "maxNestedBlocks":
				MAX_NESTED_BLOCK_INDEX = cell.getColumnIndex();
				break;
				
			case "parameters":
				PARAMETERS_INDEX = cell.getColumnIndex();
				break;
				
			case "wmc":
				MC_CABE_INDEX = cell.getColumnIndex();
				break;
				
			case "rfc":
				FAN_OUT_INDEX = cell.getColumnIndex();
				break;
			}
		}
	}
}
