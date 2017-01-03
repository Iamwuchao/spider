import java.net.URI;

import org.eclipse.jetty.websocket.client.WebSocketClient;

public class Main {
	private static  String WS_URL="ws://map.norsecorp.com/socketcluster/"; 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//	WebSocketClient client = new WebSocketClient();
		//	SimpleEchoSocket socket = new SimpleEchoSocket();
			try {
		//		client.start();
			//	URI echoUri = new URI(WS_URL);
			//	ClientUpgradeRequest request = new ClientUpgradeRequest();
		//		request.setHeader("Cookie", "__cfduid=df1be9d35647d347d9757aa8ee99a2f3f1478744220; _gat=1; _ga=GA1.2.1728105108.1478744297");
	//			client.connect(socket, echoUri);
				
				System.out.println("connect to "+WS_URL);
				HttpSpider hs = new HttpSpider();
			//	hs.getData1();
				hs.getData2();
			/*	while(true){
					
				}*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
