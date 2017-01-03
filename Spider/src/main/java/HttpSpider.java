import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class HttpSpider {
	private static String url1 = "https://cybermap.kaspersky.com/assets/map/data/labels.json";
	private static String url2 = "https://www.fireeye.com/content/dam/legacy/cyber-map/weekly_sanitized.min.js";
	
	public String getData1() throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url1);
		CloseableHttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
		System.out.println(result);
		return result;
	}
	
	public String getData2() throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url2);
		CloseableHttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
		System.out.println(result);
		return result;
	}
}
