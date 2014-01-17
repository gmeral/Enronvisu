import java.util.Map;


public class Testdb {
	public static void main(String args[]) {
		
		DbApiSql db = new DbApiSql();
		Map<String, Integer> map = db.getAllNames();
		CsvParser.exportMapEdgeValue(map, "/home/guiiii/git/BIV/Enronvisu/data/EsgeValues-Enron.csv");
	}
}
