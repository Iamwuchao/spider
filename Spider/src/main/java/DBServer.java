import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;


public class DBServer {
	private static ScheduledExecutorService scheduledThreadPool1	= Executors.newScheduledThreadPool(20);
	
	private static ScheduledExecutorService scheduledThreadPool2	= Executors.newScheduledThreadPool(10);
	private static AtomicInteger queueSize = new AtomicInteger(0);
	
	private static void mysave(String tableName,Data data){
		long start = System.currentTimeMillis();
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
		long end = System.currentTimeMillis();
		System.out.println("usedTime "+(end - data.time.getTime()));
		System.out.println("times "+(end-start));
		System.out.println(" size "+queueSize.get());
		queueSize.decrementAndGet();
		
		
	}
	
	public static void save1(String tableName,Data data){
		DBWork work = new DBWork(tableName,data);
		queueSize.addAndGet(1);
		scheduledThreadPool1.execute(work);
	}
	
	public static void save2(String tableName,Data data){
		DBWork work = new DBWork(tableName,data);
		scheduledThreadPool2.execute(work);
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
