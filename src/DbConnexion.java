


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DbConnexion {
	
		private static String USER = "root";
		private static String PSWRD = "harmony";
		private static String DB = "enron";
		private static String HOST = "localhost";
		private static String PORT = "3306";
        private static String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB;

        private final static Logger LOGGER = Logger.getLogger(DbConnexion.class.getName());

        private static DbConnexion me;

        private static Connection con = null;

        public static DbConnexion getBddConnection() {
                if (me == null) {
                        me = new DbConnexion();
                }
                return me;
        }

        private DbConnexion() {

                try {
                        Class.forName("com.mysql.jdbc.Driver");

                        con = (Connection) DriverManager.getConnection(url, USER, PSWRD);

                } catch (ClassNotFoundException e1) {
                        LOGGER.log(Level.SEVERE, e1.getMessage(), e1);
                } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                }
        }
        
        public Connection getCon() {
                return (Connection) con;
        }

}
