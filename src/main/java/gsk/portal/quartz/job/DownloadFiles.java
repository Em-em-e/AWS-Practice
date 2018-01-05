package gsk.portal.quartz.job;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

import datav.entity.Rate;
import gsk.portal.quartz.dao.UserMapper;
import gsk.portal.quartz.utils.MongodbConnection;

@Controller
@RequestMapping("/down")
public class DownloadFiles {
	@Autowired
	private UserMapper userMapper;
	
	String toDir="/home/portal/down";
	
	@RequestMapping("/all")
	@ResponseBody
	public List<Rate> downAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
        resultList=userMapper.superSelect("select l.delivery_number,l.file_id,l.file_name,v.plant "
        		+ "from l_shipping_order_status l left join v_shipping_order v on l.delivery_number=v.id "
        		+ "where l.file_id is not null  and l.file_id!='' ");

        DB db = MongodbConnection.getCon();   //创建数据库连接  
        GridFS myFS = new GridFS(db); //创建GridFS   对mongoDBFile数据库中的user1进行操作，这样文件的读取和删除都是user1中的文件  
        
        //保存文件  
//        GridFSFile file = myFS.createFile(new File("D:/image1.jpg"));  
//        file.save();  
          
         //删除文件  
        /*GridFSDBFile file =myFS.findOne("image1.jpg"); 
        myFS.remove((ObjectId) file2.getId());*/  
        String dNo="";
        String ids="";
        
        if(resultList.size()>0){
        	for(Map<?, ?> m:resultList){
        		dNo=(String) m.get("delivery_number");
        		ids=(String) m.get("file_id");
        		String plant=(String) m.get("plant");
        		if(ids!=null){
        			if(ids.indexOf(",")>0){//多个文件
        				//按出库仓库分类
        				File dir=new File(toDir+File.separator+plant+File.separator+dNo);
        				dir.mkdirs();
        				String[] id=ids.split(",");
        				for(int i=0;i<id.length;i++){
        					DBObject query  = new BasicDBObject("filename",id[i]);  
        	        		GridFSDBFile dfile =myFS.findOne(query); 
        	        		if(dfile!=null){
	        	        		DBObject metaData=dfile.getMetaData();
	        	        		String fileName=(String) metaData.get("filename");
	        	        		String end=fileName.substring(fileName.lastIndexOf("."),fileName.length());
	        	        		dfile.writeTo(new File(dir.getPath()+File.separator+dNo+"-"+(i+1)+end));
        	        		}
        				}
        			}else{//单个文件
        				DBObject query  = new BasicDBObject("filename",ids);  
    	        		GridFSDBFile dfile =myFS.findOne(query); 
    	        		File dir=new File(toDir+File.separator+plant);
    	        		dir.mkdirs();
    	        		if(dfile!=null){
    	        			DBObject metaData=dfile.getMetaData();
        	        		String fileName=(String) metaData.get("filename");
        	        		String end=fileName.substring(fileName.lastIndexOf("."),fileName.length());
        	        		dfile.writeTo(new File(dir.getPath()+File.separator+dNo+end));
    	        		}
        			}
        		}
        	}
        }
        return null;
	}
	
}
