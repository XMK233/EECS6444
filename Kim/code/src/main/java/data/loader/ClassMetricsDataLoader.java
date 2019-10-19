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

import data.structure.FileMetrics;

public class ClassMetricsDataLoader implements IClassMetricsDataLoader {
	private static final DataFormatter DATA_FORMATTER = new DataFormatter();
	
	private static int FILE_NAME_INDEX;
	private static int TYPE_INDEX;
	private static int LOC_INDEX;
	private static int ANONYMOUS_TYPE_INDEX;
	private static int NUM_OF_STATIC_METHOD_INDEX;
	private static int NUM_OF_STATIC_FIELD_INDEX;
	private static int NUM_OF_METHOD_INDEX;
	private static int NUM_OF_FIELD_INDEX;
	
	@Override
	public Map<String, FileMetrics> loadClassMetrics(String classPath) {
		Map<String, FileMetrics> data = new HashMap<>();
		
		try {
			Workbook workbook = WorkbookFactory.create(new File(classPath));
			Sheet firstSheet = workbook.getSheetAt(0);
			
			initializeIndexes(firstSheet.getRow(0));
			
			for(int i = 1; i <= firstSheet.getLastRowNum(); i++) {
				Row row = firstSheet.getRow(i);
				
				String fileName = DATA_FORMATTER.formatCellValue(row.getCell(FILE_NAME_INDEX)).substring(21);
				
				if(!data.containsKey(fileName)) {
					data.put(fileName, new FileMetrics());
				}
				
				FileMetrics classMetrics = data.get(fileName);
				classMetrics.setFileName(fileName);
				classMetrics.setType(DATA_FORMATTER.formatCellValue(row.getCell(TYPE_INDEX)));
				classMetrics.setLoc(getIntValue(row.getCell(LOC_INDEX)));
				classMetrics.setAnonymousClassesQty(getIntValue(row.getCell(ANONYMOUS_TYPE_INDEX)));
				classMetrics.setNumOfStaticMethod(getIntValue(row.getCell(NUM_OF_STATIC_METHOD_INDEX)));
				classMetrics.setNumOfStaticField(getIntValue(row.getCell(NUM_OF_STATIC_FIELD_INDEX)));
				classMetrics.setNumOfMethod(getIntValue(row.getCell(NUM_OF_METHOD_INDEX)));
				classMetrics.setNumOfField(getIntValue(row.getCell(NUM_OF_FIELD_INDEX)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	private int getIntValue(Cell cell) {
		return Integer.parseInt(DATA_FORMATTER.formatCellValue(cell));
	}

	private void initializeIndexes(Row row) {
		for(Cell cell : row) {
			switch (DATA_FORMATTER.formatCellValue(cell)) {
			case "file":
				FILE_NAME_INDEX = cell.getColumnIndex();
				break;
				
			case "type":
				TYPE_INDEX = cell.getColumnIndex();
				break;
				
			case "loc":
				LOC_INDEX = cell.getColumnIndex();
				break;
				
			case "anonymousClassesQty":
				ANONYMOUS_TYPE_INDEX = cell.getColumnIndex();
				break;
				
			case "staticMethods":
				NUM_OF_STATIC_METHOD_INDEX = cell.getColumnIndex();
				break;
				
			case "staticFields":
				NUM_OF_STATIC_FIELD_INDEX = cell.getColumnIndex();
				break;
				
			case "totalMethods":
				NUM_OF_METHOD_INDEX = cell.getColumnIndex();
				break;
				
			case "totalFields":
				NUM_OF_FIELD_INDEX = cell.getColumnIndex();
				break;
			}
		}
	}

}
