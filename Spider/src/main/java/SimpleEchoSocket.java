import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.SuspendToken;
import org.eclipse.jetty.websocket.api.WebSocketListener;

public class SimpleEchoSocket implements WebSocketListener{
	Session session;
	private volatile int times=0;
	public void onWebSocketBinary(byte[] arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	public void onWebSocketClose(int arg0, String arg1) {
		// TODO Auto-generated method stub
		System.out.println("#####   closed  #######");
		Main.socketClose=true;
		
	}

	public void onWebSocketConnect(Session session1) {
		// TODO Auto-generated method stub
		System.out.println("%%%%%%%%% connection");
		this.session = session1;
		
	//	String message1="{\"rid\":1,\"data\":{\"id\":\"A06zXLxhlpfu9uRVAGx7\",\"isAuthenticated\":false,\"pingTimeout\":20000}}";
		String message1="{\"event\":\"#handshake\",\"data\":{\"authToken\":null},\"cid\":1}";
		try {
			session.getRemote().sendString(message1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onWebSocketError(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onWebSocketText(String arg0) {
		// TODO Auto-generated method stub
	System.out.println("### message  : "+arg0);
		if(arg0.equals("#1")){
				try {
					times++;
					this.session.getRemote().sendString("#2");
					if(times>20){
						this.session.close();
					}
				//	System.out.println("send #2");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		else if(arg0.startsWith("{\"rid\":1,\"")){
		//	System.out.println(arg0);
			String message2 = "{\"event\":\"#subscribe\",\"data\":{\"channel\":\"global\"},\"cid\":2}";
			try {
				session.getRemote().sendString(message2);
		//	System.out.println(message2);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(!arg0.startsWith("{\"rid\"")){
			Data data = new Data();
			data.data = arg0;
			data.time =  new Timestamp(System.currentTimeMillis());
			DBServer.save1("data_basic_nor", data);
		}
		
	}
	
}
