

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbApiSql {

	private final static Logger LOGGER = Logger.getLogger(DbApiSql.class.getName());

	public static Driver driv;
	public static Connection con = null;
	public static final int NB_ENTRY = 2000;

	public DbApiSql() {
		DbConnexion dbCon = DbConnexion.getBddConnection();
		con = dbCon.getCon();
	}

	public Map<String, Integer> getAllNames(){
		java.sql.Statement stmt;
		java.sql.Statement stmt2;
		java.sql.ResultSet rs;
		java.sql.ResultSet rs2;
		Map<String, String> senderMap = new HashMap<String, String>();
		Map<String, Integer> edgeValue =  new HashMap<String, Integer>();
		LOGGER.info("Retrieving messages ...");

		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM message;");
			while (rs.next()) {
				String sender = rs.getString("sender");
				String messageId = rs.getString("mid");
				senderMap.put(messageId, sender);
			}
		} catch (SQLException e) {        
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

		try{
			LOGGER.info("Computing messages receivers...");
			Set<Entry<String, String>> set = senderMap.entrySet();
			int cpt = 0;
			for(Entry<String, String> ent : set) {
				stmt2 = con.createStatement();
				rs2 = stmt2.executeQuery("SELECT * FROM recipientinfo WHERE mid =" + ent.getKey() + ";");

				while(rs2.next()) {
					String receiver = rs2.getString("rvalue");

					String key = new String(ent.getValue() + "'*'" + receiver);
					String reverseKey = new String(receiver + "'*'" + ent.getValue());
					if (edgeValue.containsKey(key)) 
						edgeValue.put(key, edgeValue.get(key) + 1);
					else if (edgeValue.containsKey(reverseKey)) 
						edgeValue.put(reverseKey, edgeValue.get(reverseKey) + 1);
					else
						edgeValue.put(key, 1);
				}
				cpt++;
				if (cpt == NB_ENTRY)
					break;
			}
		}catch (SQLException e) {        
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} 
		LOGGER.info("Removing weak edges ...");
		Iterator<String> keySetItr = edgeValue.keySet().iterator(); 
		List<String> toBeRemoved = new ArrayList<String>();
		while(keySetItr.hasNext()) {
			String key = keySetItr.next();
			if (edgeValue.get(key) < 10)
				toBeRemoved.add(key);
		}
		for(String key : toBeRemoved)
			edgeValue.remove(key);
		return edgeValue;
	}
}
