import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class CsvParser {
	public static void exportMapEdgeValue(Map<String, Integer> dataMap, String outPath) {
		try {
			OutputStream ops = new FileOutputStream(outPath);
			OutputStreamWriter opsw = new OutputStreamWriter(ops);
			BufferedWriter bw = new BufferedWriter(opsw);

			Set<Entry<String, Integer>> set = dataMap.entrySet();
			bw.write("fromNodeId*toNodeId*weight");
			bw.newLine();
			int cpt = 0;
			for(Entry<String,Integer> ent : set) {
				bw.write("'" + ent.getKey() + "'" + "*" + ent.getValue());
				bw.newLine();
				cpt++;
				if((cpt % 100) == 0)
					bw.flush();
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Ecriture du fichier terminée");
	}
}
