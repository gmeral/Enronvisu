import java.util.Map;


public class Main {
	public static void main(String args[]) {
		
		DbApiSql db = new DbApiSql();
		Map<String, Integer> map = db.getAllNames();
		CsvParser.exportMapEdgeValue(map, "/home/guiiii/git/BIV/Enronvisu/data/EdgeValues2000-Enron.csv");
	}
}
