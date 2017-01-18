import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;


public class ConnectionFactory {
	private final static String url = "jdbc:mysql://114.215.108.198:3306/sc?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
	
	private final static String dbDriver="com.mysql.cj.jdbc.Driver";   
	
	private static String dbUser="zerg";  
	private static String dbPass="Dlut-2016sc&yz"; 
	
	 public static LinkedBlockingQueue<Connection> connectionPool = new LinkedBlockingQueue<Connection>(100);
	
	
	 static{
			try {
				Class.forName(dbDriver);
				for(int i=0;i<100;i++){
					Connection connection = DriverManager.getConnection(url, dbUser, dbPass);
			
					connectionPool.add(connection);
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static Connection getConnection() throws SQLException, InterruptedException{
			return (Connection) connectionPool.take();
		}
		
		public static void returnConnection(Connection connection){
			connectionPool.add(connection);
		}
}

