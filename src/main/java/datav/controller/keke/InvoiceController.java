package datav.controller.keke;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import datav.GaoDeMapUtil;
import datav.entity.IdValueInfo;
import datav.entity.Point;
import datav.entity.XY;
import datav.entity.XYS;
import datav.util.Util;
import gsk.portal.quartz.dao.UserMapper;

@Controller
@RequestMapping("/datav/keke")
public class InvoiceController {
	
	
//	@Autowired
//	private MainMapper mainMapper;
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 省份销售额热力图
	 */
	@RequestMapping("/provinceLspMap")
	public void provinceMap(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<IdValueInfo> li=new ArrayList<IdValueInfo>();
		//读取省份代码json数据
		Map<String,JSONObject> map=getProvinceJson("name");
		//商务服务商负责省份
		List<LinkedHashMap<String, Object>> businessProvince=new ArrayList<LinkedHashMap<String, Object>>();
		businessProvince=userMapper.superSelect("select * from d_lsp where isbusiness='1'");
		Double value=1.0;
		for(Map<?, ?> m:businessProvince){
			String businessProvinces=(String) m.get("b_province");
			for(String p:businessProvinces.split("/")){
				IdValueInfo id=new IdValueInfo();
				JSONObject jo=map.get(p);
				if(jo!=null){
					id.setId(jo.getString("adcode"));
					id.setValue(value==null?0:value);
					id.setInfo(p+"-"+m.get("lsp_name"));
					li.add(id);
				}else{
					System.out.println(p+" 找不到省份");
				}
			}
			value+=2;
		}
		JSONArray o=(JSONArray) JSONObject.toJSON(li);
        response.getWriter().print(o.toString());
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/serviceProviderDue")
	@ResponseBody
	public List<XY> serviceProviderDue(){
		List<LinkedHashMap<String, Object>> result=new ArrayList<LinkedHashMap<String, Object>>();
		result=userMapper.superSelect("select SUM(billing_amount_inc_vat-amount_paid) count,service_provider "
				+ "from b_invoice_detail where DATEDIFF(Day,getdate(),net_due_date)<=0  "
				+ "group by service_provider");
		List<XY> xys=new ArrayList<>();
		for(Map m:result){
			XY xy=new XY();
			xy.setX(lspName.get(m.get("service_provider")));
			Double dou=(Double) m.get("count");
			BigDecimal b=BigDecimal.valueOf(dou);
			xy.setY(b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue()/10000);
			xys.add(xy);
		}
		return xys;
	}
	
	/**
	 * CDC欠款
	 * @throws Exception 
	 */
	@RequestMapping("/customersDueCount")
	@ResponseBody
	public List<Point> getAllCustomers() throws Exception{
        List<Point> li=new ArrayList<Point>();
        List<LinkedHashMap<String, Object>> dueList=new ArrayList<LinkedHashMap<String, Object>>();
		dueList=userMapper.superSelect("select SUM(billing_amount_inc_vat-amount_paid) notpaid,customer_no,customer_name,d.address "
				+ "from b_invoice_detail i left join d_dcdc d on i.customer_no=d.dcdc_code "
				+ "where report_time>=(select substring(CONVERT(varchar(25),MAX(report_time),121),1,10)+' 00:00:00.000' "
				+ "from b_invoice_detail) and DATEDIFF(Day,getdate(),net_due_date)<=0 and (billing_amount_inc_vat-amount_paid)>0 "
				+ " and billing_date<='2017-10-31 00:00:00.000' "
				+ "group by customer_name,customer_no,d.address order by notpaid desc");
		
        li=setPoint(dueList, 1);
        return li;
	}
	public List<Point> setPoint(List<LinkedHashMap<String, Object>> resultList,int type) throws Exception{
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
	        		for(int k=0;k<locations.split("-").length;k++){
	        			String location=locations.split("-")[k];
	        			Double v=(Double) resultList.get(10*i+k).get("notpaid");
	        			String info=(String) resultList.get(10*i+k).get("customer_name");
	        			BigDecimal va = new BigDecimal(v);
	        			int value = va.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	        			Point p=new Point();
	        			lo=location.split(",");
	        			b = new BigDecimal(Float.parseFloat(lo[0])); 
	        			b1 = new BigDecimal(Float.parseFloat(lo[1])); 
	        			p.setLng(b.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
	        			p.setLat(b1.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());
	        			p.setType(type);
	        			p.setValue(value);
	        			p.setInfo(info+"-欠款"+value+"元");
	        			li.add(p);
	        		}
	        	}
	        }
		return li;
	}
	/**
	 * 省份欠款详情
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/provinceDetail")
	@ResponseBody
	public void provinceDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
			resultList=userMapper.superSelect("select top 10 SUM(not_due) notDue,SUM(overdue_0_30+overdue_31_60+overdue_61_90) over_0_90,"
					+ "sum(overdue_91_180+overdue_181_360+overdue_361_) over_91_,province,"
					+ "SUM(not_due+overdue_0_30+overdue_31_60+overdue_61_90+overdue_91_180+overdue_181_360+overdue_361_) amount "
					+ "from (select isnull(not_due,0) not_due,isnull(overdue_0_30,0) overdue_0_30,isnull(overdue_31_60,0)overdue_31_60,"
					+ "isnull(overdue_61_90,0)overdue_61_90,isnull(overdue_91_180,0)overdue_91_180,"
					+ "isnull(overdue_181_360,0)overdue_181_360,isnull(overdue_361_,0) overdue_361_,province "
					+ "from b_ar_report where  billing_date>='2017-01-01 00:00:00.000'  and amount_inc_vat>=0 "
					+ "and billing_date<='2017-10-31 00:00:00.000') so group by province order by amount desc");
        List<XYS> xyss=new ArrayList<>();
        for(Map m:resultList){
        	String pro=(String) m.get("province");
        	XYS notDue=new XYS();
        	notDue.setX(pro);
        	notDue.setS("未超期");
        	XYS over0_90=new XYS();
        	over0_90.setX(pro);
        	over0_90.setS("超期0-90天");
        	XYS over91_=new XYS();
        	over91_.setX(pro);
        	over91_.setS("超期91天以上");
			Double value=(Double) m.get("notDue");
			BigDecimal b = new BigDecimal(value);
			int f1 = b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			notDue.setY((double) (f1/10000));
			Double v1=(Double) m.get("over_0_90");
			BigDecimal b1 = new BigDecimal(v1);
			int f2 = b1.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			over0_90.setY((double) (f2/10000));
			Double v2=(Double) m.get("over_91_");
			BigDecimal b2 = new BigDecimal(v2);
			int f3 = b2.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			over91_.setY((double) (f3/10000));
        	xyss.add(notDue);
        	xyss.add(over0_90);
        	xyss.add(over91_);
        }
        JSONArray o=(JSONArray) JSONObject.toJSON(xyss);
        response.getWriter().print(o.toString());
	}
	private Map<String, JSONObject> getProvinceJson(String keyName) throws Exception{
		//读取省份代码json数据
		File file=new ClassPathResource("datav/controller/100000_province.text").getFile();
		String province=Util.ReadFile(file.getAbsolutePath());
		JSONArray arr=JSONObject.parseArray(province);
		Map<String,JSONObject> map=new HashMap<String, JSONObject>();
		for(int i=0;i<arr.size();i++){
			map.put(arr.getJSONObject(i).getString(keyName),arr.getJSONObject(i));
		}
		return map;
	}
	private Map<String,String> lspName;
	{
		lspName=new HashMap<String, String>();
		lspName.put("GSK Commercial", "GSK Commercial");
		lspName.put("科园信海（北京）医疗用品贸易有限公司", "北京科园");
		lspName.put("国药控股湖北有限公司", "国药湖北");
		lspName.put("广西柳州医药股份有限公司", "广西柳州医药");
		lspName.put("江西汇仁集团医药科研营销有限公司", "江西汇仁");
		lspName.put("国药控股广州有限公司", "国药广州");
		lspName.put("重庆医药集团药销医药有限公司", "重庆医药");
		lspName.put("安徽天星医药集团有限公司", "安徽天星");
		lspName.put("康德乐（上海）医药有限公司", "康德乐");
	}
	
}
