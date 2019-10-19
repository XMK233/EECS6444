package data.structure;

public class FileMetrics {
	private String fileName;
	private String type;
	
	private int loc;
	private int anonymousClassesQty;
	private int numOfStaticMethod;
	private int numOfStaticField;
	private int numOfMethod;
	private int numOfField;
	private MethodMetrics methodMetrics;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLoc() {
		return loc;
	}

	public void setLoc(int loc) {
		this.loc = loc;
	}

	public int getAnonymousClassesQty() {
		return anonymousClassesQty;
	}

	public void setAnonymousClassesQty(int anonymousClassesQty) {
		this.anonymousClassesQty = anonymousClassesQty;
	}

	public int getNumOfStaticMethod() {
		return numOfStaticMethod;
	}

	public void setNumOfStaticMethod(int numOfStaticMethod) {
		this.numOfStaticMethod = numOfStaticMethod;
	}

	public int getNumOfStaticField() {
		return numOfStaticField;
	}

	public void setNumOfStaticField(int numOfStaticField) {
		this.numOfStaticField = numOfStaticField;
	}

	public int getNumOfMethod() {
		return numOfMethod;
	}

	public void setNumOfMethod(int numOfMethod) {
		this.numOfMethod = numOfMethod;
	}

	public int getNumOfField() {
		return numOfField;
	}

	public void setNumOfField(int numOfField) {
		this.numOfField = numOfField;
	}
	public MethodMetrics getMethodMetrics() {
		return methodMetrics;
	}

	public void setMethodMetrics(MethodMetrics methodMetrics) {
		this.methodMetrics = methodMetrics;
	}

	@Override
	public String toString() {
		return "FileMetrics [fileName=" + fileName + ", type=" + type + ", loc=" + loc + ", anonymousClassesQty="
				+ anonymousClassesQty + ", numOfStaticMethod=" + numOfStaticMethod + ", numOfStaticField="
				+ numOfStaticField + ", numOfMethod=" + numOfMethod + ", numOfField=" + numOfField + ", methodMetrics="
				+ methodMetrics + "]";
	}
}
