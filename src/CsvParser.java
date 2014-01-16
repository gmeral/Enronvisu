import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class CsvParser {

	public static final int FROM_COLUMN = 0;
	public static final int TO_COLUMN = 1;

	public static Map<Integer, MailStatPair> fillMap(String csvFilePath) {
		Map<Integer, MailStatPair> statsMap = new HashMap<Integer, MailStatPair>();
		try {
			InputStream ips=new FileInputStream(csvFilePath);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			System.out.println("Colonnes lues :" + br.readLine());
			while ((ligne=br.readLine())!=null){
				String[] splitLine = ligne.split("\\s");
				int senderId = Integer.parseInt(splitLine[FROM_COLUMN]);
				int receiverId = Integer.parseInt(splitLine[TO_COLUMN]);
				if(!(statsMap.containsKey(senderId)))
					statsMap.put(senderId, new MailStatPair(1, 0));
				else
					statsMap.get(senderId).nbSent += 1;
				
				if(!(statsMap.containsKey(receiverId)))
					statsMap.put(receiverId, new MailStatPair(0, 1));
				else
					statsMap.get(senderId).nbReceived += 1;
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return statsMap;
	}

	public static void exportMap(Map<Integer, MailStatPair> dataMap, String outPath) {
		try {
			OutputStream ops = new FileOutputStream(outPath);
			OutputStreamWriter opsw = new OutputStreamWriter(ops);
			BufferedWriter bw = new BufferedWriter(opsw);

			Set<Entry<Integer,MailStatPair>> set = dataMap.entrySet();
			bw.write("NodeId nbSent nbReceived");
			bw.newLine();
			int cpt = 0;
			for(Entry<Integer,MailStatPair> ent : set) {
				bw.write(ent.getKey() + " " + ent.getValue().nbSent + " " + ent.getValue().nbReceived);
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
	
	public static void exportMapEdgeValue(Map<String, Integer> dataMap, String outPath) {
		try {
			OutputStream ops = new FileOutputStream(outPath);
			OutputStreamWriter opsw = new OutputStreamWriter(ops);
			BufferedWriter bw = new BufferedWriter(opsw);

			Set<Entry<String, Integer>> set = dataMap.entrySet();
			bw.write("node1 node2 nbmails");
			bw.newLine();
			int cpt = 0;
			for(Entry<String,Integer> ent : set) {
				bw.write(ent.getKey() + ent.getValue());
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
