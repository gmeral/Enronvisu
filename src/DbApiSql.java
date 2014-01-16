

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

	public DbApiSql() {
		DbConnexion dbCon = DbConnexion.getBddConnection();
		con = dbCon.getCon();
	}

	public Map<String, Integer> getAllNames(){
		java.sql.Statement stmt;
		java.sql.Statement stmt2;
		java.sql.ResultSet rs;
		java.sql.ResultSet rs2;
		StringBuffer line = new StringBuffer("");
		Map<String, String> senderMap = new HashMap<String, String>();
		Map<String, Integer> edgeValue =  new HashMap<String, Integer>();
		System.out.println("Retrieving messages ...");

		try {
			stmt = con.createStatement();

			System.out.println("query");
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
			System.out.println("computing messages receivers...");
			Set<Entry<String, String>> set = senderMap.entrySet();
			int cpt = 0;
			for(Entry<String, String> ent : set) {
				stmt2 = con.createStatement();
				rs2 = stmt2.executeQuery("SELECT * FROM recipientinfo WHERE mid =" + ent.getKey() + ";");
				
				while(rs2.next()) {
					String receiver = rs2.getString("rvalue");
					
					String key = new String(ent.getValue() + "' '" + receiver);
					String reverseKey = new String(receiver + "' '" + ent.getValue());
					if (edgeValue.containsKey(key)) 
						edgeValue.put(key, edgeValue.get(key) + 1);
					else if (edgeValue.containsKey(reverseKey)) 
						edgeValue.put(reverseKey, edgeValue.get(reverseKey) + 1);
					else
						edgeValue.put(key, 1);
					System.out.println("value updated :" + ent.getValue() + " " + receiver);
					
				}
				cpt++;
				if (cpt == 101)
					break;
			}
		}catch (SQLException e) {        
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} 
		return edgeValue;
	}
	/*   public void setNewIssue(String issue_key, String status) {
                java.sql.PreparedStatement preparedStatement;
                try {
                        preparedStatement = con.prepareStatement("INSERT INTO issues ("
                                        + "issue_key, status) VALUES('"
                                        + issue_key + "', '" + status
                                        + "');");
                        preparedStatement.executeUpdate();
                } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }

        }


        public void setIssueKey(int issue_id, String issue_key) {
                java.sql.PreparedStatement preparedStatement;
                try {
                        preparedStatement = con.prepareStatement("UPDATE issues SET "
                                        + "issue_key = '" + issue_key
                                        + "' WHERE issue_id = "
                                        + Integer.toString(issue_id) + ";");
                        preparedStatement.executeUpdate();
                } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }                
        }

        public void setIssueStatus(int issue_id, String status) {
                java.sql.PreparedStatement preparedStatement;
                try {
                        preparedStatement = con.prepareStatement("UPDATE issues SET "
                                        + "status = '" + status
                                        + "' WHERE issue_id = "
                                        + Integer.toString(issue_id) + ";");
                        preparedStatement.executeUpdate();
                } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }                                
        }

        public String getStringFromBdd(String table, String target,
                        String primaryKey, String id) {
                StringBuffer line = new StringBuffer("");
                java.sql.Statement stmt;
                java.sql.ResultSet rs;
                try {
                        stmt = con.createStatement();
                        rs = stmt.executeQuery("SELECT " + target + " FROM " + table
                                        + " WHERE " + primaryKey + " = '" + id
                                        + "';");
                        while (rs.next()) {
                                line.append(rs.getString(target));
                        }
                } catch (SQLException e) {        
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
                return line.toString();
        }

        public ArrayList<String> getIssue(int issue_id) {
                ArrayList<String> s = new ArrayList<String>();
                s.add(getStringFromBdd("issues", "issue_key", "issue_id", Integer.toString(issue_id)));
                s.add(getStringFromBdd("issues", "status", "issue_id", Integer.toString(issue_id)));
                return s;
        }

        public String getIssuekey(int issue_id) {
                String s = getStringFromBdd("issues", "issue_key", "issue_id", Integer.toString(issue_id));
                return s;
        }

        public String getIssueStatus(int issue_id) {
                String s = getStringFromBdd("issues", "status", "issue_id", Integer.toString(issue_id));
                return s;
        }

        public boolean isIssue(String issue_id) {
                String s = getStringFromBdd("ISSUE", "issue_id", "issue_key", issue_id);
                if (s.compareTo("") != 0)
                        return true;
                return false;
        }*/
}
