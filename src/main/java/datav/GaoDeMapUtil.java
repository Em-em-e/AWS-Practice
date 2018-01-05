package datav;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class GaoDeMapUtil {
	/**
	 * 高德地图API key
	 */
	public static final String API_KEY="a9a5bf12bdd78c83a6455ef67fd6b04f";
	/**
	 * 获取经纬度API URL
	 */
	public static final String LOCATION_URL="http://restapi.amap.com/v3/geocode/geo";
	
	private static HttpClient client=HttpClients.custom().build();
	
	private static HttpGet get=new HttpGet();
	private static HttpPost post=new HttpPost();
	private static JsonParser parse =new JsonParser();
	
	/**
	 * 获取地址经纬度
	 * @param addr
	 * @return
	 * @throws Exception
	 */
	public static String getLocation(String addr) throws Exception{
		get.setURI(new URI(LOCATION_URL+"?output=JSON&key="+API_KEY+"&address="+addr));
		String respCont=EntityUtils.toString(client.execute(get).getEntity());
		JsonObject json=(JsonObject) parse.parse(respCont);  //创建jsonObject对象
		JsonObject geocodes;
        if(json.get("geocodes").getAsJsonArray()!=null&&json.get("geocodes").getAsJsonArray().size()>0){
        	geocodes=(JsonObject) json.get("geocodes").getAsJsonArray().get(0);
        	String location=geocodes.get("location").getAsString();
        	return location;
        }
        return null;
	}
	/**
	 * 批量获取地址经纬度，以“-”分割，每次最多十个
	 * @param addrs
	 * @return
	 * @throws Exception
	 */
	public static String getLocations(String[] addrs) throws Exception{
		String addr="";
		if(addrs.length>0){
			for(String a:addrs){
				if(a!=null && !"".equals(a))
					addr=addr+"|"+a;
			}
			if(addr.length()>0){
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("output", "JSON"));
				params.add(new BasicNameValuePair("batch", "true"));
				params.add(new BasicNameValuePair("key",API_KEY));
				params.add(new BasicNameValuePair("address",addr.length()>1?addr.substring(1):""));
				
				post.setURI(new URI(LOCATION_URL));
	        	post.setEntity(new UrlEncodedFormEntity(params,Consts.UTF_8));
				
	//			get.setURI(new URI(LOCATION_URL+"?output=JSON&batch=true&key="+API_KEY+"&address="+addr.substring(3)));
				String respCont=EntityUtils.toString(client.execute(post).getEntity());
				JsonObject json=(JsonObject) parse.parse(respCont);  //创建jsonObject对象
				StringBuilder sb=new StringBuilder();
				for(int i=0;i<json.get("geocodes").getAsJsonArray().size();i++){
					JsonObject geocodes=(JsonObject) json.get("geocodes").getAsJsonArray().get(i);
					try {
						String location=geocodes.get("location").getAsString();
						sb.append("-"+location);
					} catch (Exception e) {
					}
				}
				return sb.toString().substring(1);
			}else
				return "";
		}
		return null;
	}
	public static void main(String[] args) throws Exception {
//		System.out.println(getLocation("四川省成都市金牛区茶店子横街12号"));
		getLocations(new String[]{"四川省成都市金牛区茶店子横街12号","四川省成都市新都区新店镇文家二巷63号"});
	}
}
