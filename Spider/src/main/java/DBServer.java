import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;


public class DBServer {
	public static void save(String tableName,Data data){
		String sql = "insert into "+tableName+" "+"(`data`,`time`)"+" "+"values"+" "+"(?,?)";
		try(Connection connection  = ConnectionFactory.getConnection()) {
			
			 PreparedStatement preStatement = connection.prepareStatement(sql);
			
			 preStatement.setString(1, data.data);
			 preStatement.setTimestamp(2, data.time);
			 preStatement.executeUpdate();
			 connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
