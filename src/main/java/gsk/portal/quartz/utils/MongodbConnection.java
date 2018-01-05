package gsk.portal.quartz.utils;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongodbConnection {
	
	public static DB getCon() throws Exception{
		MongoClient mongo = new MongoClient("localhost", 27017);
		return mongo.getDB("gsk");
	}
}
