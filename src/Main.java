import java.util.Map;


public class Main {
	public static void main(String args[]) {
		Map<Integer, MailStatPair> dataMap = CsvParser.fillMap("/autofs/netapp/account/cremi/gmeral/BIV/workspace/Enronvisu/data/Email-EnronBIG.csv");
		CsvParser.exportMap(dataMap, "/autofs/netapp/account/cremi/gmeral/BIV/workspace/Enronvisu/data/Stats-EnronBIG.csv");
	}
}
