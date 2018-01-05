package datav.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import datav.GaoDeMapUtil;
import datav.entity.FromTo;
import datav.entity.Point;
import datav.entity.ProvinceAmount;
import gsk.portal.quartz.dao.UserMapper;


@Controller
@RequestMapping("/datav")
public class MainController {
	
	
//	@Autowired
//	private MainMapper mainMapper;
	@Autowired
	private UserMapper userMapper;
	
	String json=null;
	
	public List<Point> getAllCustomers() throws Exception{
        List<Point> li=new ArrayList<Point>();
        List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
        resultList=userMapper.superSelect("select address  from d_dcdc where address is not null and address!='';");
        li=setPoint(resultList, 1,1);
        resultList=userMapper.superSelect("select address  from d_lsp where address is not null and address!='';");
        List<Point> li1=setPoint(resultList, 2,5);
        for(Point p:li1){
        	li.add(p);
        }
        JSONArray o=(JSONArray) JSONObject.toJSON(li);
        json=o.toString();
        return li;
	}
	public List<Point> setPoint(List<LinkedHashMap<String, Object>> resultList,int type,int value) throws Exception{
		List<Point> li=new ArrayList<Point>();
		if(resultList.size()>0)
	        for(int i=0;i<(resultList.size()/10+1);i++){
	        	String[] addrs=new String[10];
	        	for(int j=0;j<10;j++){
	        		if((10*i+j)<resultList.size())
	        			addrs[j]=(String) resultList.get(10*i+j).get("address");
	        		else
	        			break;
	        	}
	        	String locations=GaoDeMapUtil.getLocations(addrs);
	        	if(!"".equals(locations)){
	        		String[] lo;
	        		BigDecimal b;
	        		BigDecimal b1;
	        		for(String location:locations.split("-")){
	        			Point p=new Point();
	        			lo=location.split(",");
	        			b = new BigDecimal(Float.parseFloat(lo[0])); 
	        			b1 = new BigDecimal(Float.parseFloat(lo[1])); 
	        			p.setLng(b.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
	        			p.setLat(b1.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
	        			p.setType(type);
	        			p.setValue(value);
	        			li.add(p);
	        		}
	        	}
	        }
		return li;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/fromTo")
	public void getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List<FromTo> li=new ArrayList<FromTo>();
        List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
        resultList=userMapper.superSelect("select w.address l_from,s.shipto_address l_to "+
			"from v_shipping_order s left join d_warehouse w on s.out_wh_code=w.wh_code "+
			"left join l_transport t on s.id=t.delivery_number "+
			"where w.address is not null and s.shipto_address is not null "+
			"and t.start_time>=('2017-08-01 00:00:00.000')"+ 
			//"and t.start_time>=(substring(CONVERT(varchar(25),GETDATE(),121),1,7)+'-01 00:00:00.000')"+ 
			"group by w.address,s.shipto_address");
        for(Map m:resultList){
        	String lto=(String) m.get("l_to");
        	if(lto.indexOf("-")>0)
        		continue;
        	if(lto!=null){
        		if(lto.indexOf(" ")>0){
        			continue;
//        			lto=lto.substring(0, lto.indexOf(" ")).trim();
        		}
        	}
        	String locat=GaoDeMapUtil.getLocations(new String[]{(String)m.get("l_from"),(String)m.get("l_to")});
        	if(locat.split("-").length>=2){
	        	FromTo ft=new FromTo();
	        	ft.setFrom(locat.split("-")[0]);
	        	ft.setTo(locat.split("-")[1]);
	        	li.add(ft);
        	}
        }
        System.out.println("--------FromTo:"+li.size());
        JSONArray o=(JSONArray) JSONObject.toJSON(li);
        response.getWriter().print(o.toString());
//      return li;
	}
	
	@RequestMapping("/getJson")
	public void fromTo(HttpServletRequest request,HttpServletResponse response) throws Exception{
        if(json==null || "".equals(json)){
        	getAllCustomers();
        }
        response.getWriter().print(json);
	}
	
	@RequestMapping("/accessCount")
	public void access(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
		List<result> arr=new ArrayList<result>();
			resultList=userMapper.superSelect("select COUNT(*) su,month "
					+ "from (select substring(CONVERT(varchar(12),op_time,112),1,6) month  from sys_log) d "
					+ "group by d.month order by d.month");
		for(int i=0;i<resultList.size();i++){
			result r=new result();
			String month=(String) resultList.get(i).get("month");
			r.x=month.substring(4, month.length())+"月";
			r.y=(Integer) resultList.get(i).get("su");
			arr.add(r);
		}
		JSONArray ob=(JSONArray) JSONObject.toJSON(arr);
		response.getWriter().print(ob.toString());
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("amounts")
	public void getAmount(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
		resultList=userMapper.superSelect("select sum(delivery_quantity) amount,province from l_shipping_order group by province");
		List<ProvinceAmount> amount=new ArrayList<ProvinceAmount>();
		for(Map m:resultList){
			ProvinceAmount pa=new ProvinceAmount();
			pa.setAmountCount(Double.valueOf(m.get("amount")+""));
			String po=GaoDeMapUtil.getLocation((String) m.get("province"));
			if(po!=null){
				pa.setLat(Float.parseFloat(po.split(",")[1]));
				pa.setLng(Float.parseFloat(po.split(",")[0]));
			}
			amount.add(pa);
		}
		JSONArray o=(JSONArray) JSONObject.toJSON(amount);
		response.getWriter().print(o.toString());
	}
}

class result{
	String x;
	int y;
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}