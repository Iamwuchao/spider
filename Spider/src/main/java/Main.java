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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			WebSocketClient client = new WebSocketClient();
			SimpleEchoSocket socket = new SimpleEchoSocket();
			try {
				client.start();
				URI echoUri = new URI(WS_URL);
				//ClientUpgradeRequest request = new ClientUpgradeRequest();
				//request.setHeader("Cookie", "__cfduid=df1be9d35647d347d9757aa8ee99a2f3f1478744220; _gat=1; _ga=GA1.2.1728105108.1478744297");
				client.connect(socket, echoUri);
				
				System.out.println("connect to "+WS_URL);
				
				ScheduledExecutorService scheduledThreadPool1	= Executors.newScheduledThreadPool(1);
				
				scheduledThreadPool1.scheduleAtFixedRate(new HttpWorkka(), 0, 10,TimeUnit.SECONDS);
				
				ScheduledExecutorService scheduledThreadPool2	= Executors.newScheduledThreadPool(1);
				
				scheduledThreadPool2.scheduleAtFixedRate(new HttpWorkfireeye(), 0, 10,TimeUnit.SECONDS);
				//HttpSpider hs = new HttpSpider();
			//	hs.getData1();
			//	hs.getData2();
				while(true){
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
