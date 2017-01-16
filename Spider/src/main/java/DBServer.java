import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class DBServer {
	private static ScheduledExecutorService scheduledThreadPool1	= Executors.newScheduledThreadPool(5);
	private static void mysave(String tableName,Data data){
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
	
	public static void save(String tableName,Data data){
		DBWork work = new DBWork(tableName,data);
		scheduledThreadPool1.execute(work);
	}
	
	static class DBWork implements Runnable{
		String tableName;
		Data data;
		
		DBWork(String tableName,Data data){
			this.tableName = tableName;
			this.data = data;
		
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			DBServer.mysave(tableName, data);
		}
		
	}
}
