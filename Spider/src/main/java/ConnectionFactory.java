import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
	private final static String url = "jdbc:mysql://114.215.108.198:3306/sc?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
	
	private final static String dbDriver="com.mysql.cj.jdbc.Driver";   
	
	private static String dbUser="zerg";  
	private static String dbPass="Dlut-2016sc&yz"; 
	
	
	
	
	static{
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		Connection connection = DriverManager.getConnection(url, dbUser, dbPass);
		return connection;
	}
}

