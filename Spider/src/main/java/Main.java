import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.client.WebSocketClient;

public class Main {
	private static  String WS_URL="ws://map.norsecorp.com/socketcluster/"; 
	
	private static final String firTable = "data_basic_fir";
	private static final String kabTable= "data_basic_kab";
	private static final String norTable = "data_basic_nor";
	static volatile boolean socketClose ;
	static final int SIZE = 1;
	static WebSocketClient[] clients = new  WebSocketClient[SIZE];
	static SimpleEchoSocket[] sockets = new  SimpleEchoSocket[SIZE];
	public static void startSocket(){
		for(int i=0;i<SIZE;i++){
			 clients[i] = new WebSocketClient();
				sockets[i] = new SimpleEchoSocket();
				try {
					clients[i].start();
					URI echoUri = new URI(WS_URL);
					
					clients[i].connect(sockets[i], echoUri);
					
					System.out.println("connect to "+WS_URL);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 startSocket();
		 socketClose=false;
			ScheduledExecutorService scheduledThreadPool1	= Executors.newScheduledThreadPool(1);
			
			scheduledThreadPool1.scheduleAtFixedRate(new HttpWorkka(), 0,600,TimeUnit.SECONDS);
			
			ScheduledExecutorService scheduledThreadPool2	= Executors.newScheduledThreadPool(1);
			
			scheduledThreadPool2.scheduleAtFixedRate(new HttpWorkfireeye(), 600, 10,TimeUnit.SECONDS);
			while(true){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(socketClose){
					 startSocket();
					 socketClose=false;
				}
			}
	}
	
	static class HttpWorkka implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String message;
			try {
				message = HttpSpider.getData1();
				Data data = new Data();
				data.data=message;
				Timestamp nowTime = new Timestamp(System.currentTimeMillis());
				data.time = nowTime;
				DBServer.save(kabTable, data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	static class HttpWorkfireeye implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String message;
			try {
				message = HttpSpider.getData2();
				Data data = new Data();
				data.data=message;
				Timestamp nowTime = new Timestamp(System.currentTimeMillis());
				data.time = nowTime;
				DBServer.save(firTable, data);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
