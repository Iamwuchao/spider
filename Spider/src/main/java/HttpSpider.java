import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import com.google.gson.Gson;


public class HttpSpider{
	private static String url1 = "https://cybermap.kaspersky.com/assets/data/events/8.json";
	private static String url2 = "https://www.fireeye.com/content/dam/legacy/cyber-map/weekly_sanitized.min.js";
	
	public static String getData1() throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url1);
		CloseableHttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
		System.out.println("get data1 "+result.length());
		return result;
	}
	
	public static String getData2() throws ClientProtocolException, IOException{
		//System.out.println("####### hahahahahah");
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY); 
		HttpGet get = new HttpGet(url2);
		CloseableHttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
		//System.out.println("get data2 "+result.length());
		return result;
	}
	
	
	public static void parse(String message){
		Gson gson = new Gson();
		KA ka = gson.fromJson(message, KA.class);
		long[] events = decodeBase64(ka.getEvents());
		long[] counts = decodeBase64(ka.getCounts8());
		long[] totals8 = decodeBase64(ka.getTotals8());
		System.out.println("events "+events.length);
		System.out.println("counts "+counts.length);
		System.out.println("totals8 "+totals8.length);
	}
	
	private static long[] decodeBase64(String value){
		byte[] bytearray = Base64.decodeBase64(value);
		long[] longarray = new long[bytearray.length/4+1];
		int i=0;
		int index = 0;
		while(i<bytearray.length){
			long tem=0;
			for(int j=0;j<4;j++)
			{
				tem = tem<<8 +bytearray[i++];
			}
			longarray[index++]=tem;
		}
		return longarray;
	}
}
