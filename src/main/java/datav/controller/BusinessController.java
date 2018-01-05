package datav.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import datav.entity.IdValueInfo;
import datav.entity.XY;
import datav.entity.XYS;
import datav.entity.XYZ;
import datav.util.Util;
import gsk.portal.quartz.dao.UserMapper;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/datav")
public class BusinessController {
	
	
//	@Autowired
//	private MainMapper mainMapper;
	@Autowired
	private UserMapper userMapper;
	
	
	/**
	 * 省份销售详情
	 * @throws Exception 
	 */
	@RequestMapping("/provinceDetail")
	@ResponseBody
	public void provinceDetail(HttpServletRequest request,HttpServletResponse response,String id) throws Exception{
		List<LinkedHashMap<String, Object>> provinceList=new ArrayList<LinkedHashMap<String, Object>>();
		provinceList=userMapper.superSelect("select province from b_invoice_detail group by province");
		String province="";
		if(":id".equals(id)){
			province=provinceOrder().get(0).getX();
		}else{
			Map<String,JSONObject> map=getProvinceJson("adcode");
			JSONObject pJson=map.get(id);
			String p=pJson.getString("name");
			for(Map m:provinceList){
				if(p!=null&&p.indexOf((String)m.get("province"))>=0)
					province=(String)m.get("province");
			}
		}
		List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
        resultList=userMapper.superSelect("select top 10 SUM(billing_amount_inc_vat) monthCount,substring(bill_date,6,2) b_date "
        		+ "from (select billing_amount_inc_vat,substring(CONVERT(varchar(25),billing_date,121),1,7)+'-01 00:00:00.000' bill_date "
        		+ "from b_invoice_detail "
        		+ "where report_time>=(select substring(CONVERT(varchar(25),MAX(report_time),121),1,10)+' 00:00:00.000' from b_invoice_detail) "
        		+ "and province='"+province+"') so group by bill_date order by bill_date desc");
		List<LinkedHashMap<String, Object>> quantity=new ArrayList<LinkedHashMap<String, Object>>();
		quantity=userMapper.superSelect("select top 10 SUM(billed_quantity) monthCount,substring(bill_date,6,2) b_date "
        		+ "from (select billed_quantity,substring(CONVERT(varchar(25),billing_date,121),1,7)+'-01 00:00:00.000' bill_date "
        		+ "from b_invoice_detail "
        		+ "where report_time>=(select substring(CONVERT(varchar(25),MAX(report_time),121),1,10)+' 00:00:00.000' from b_invoice_detail) "
        		+ "and province='"+province+"') so group by bill_date order by bill_date desc");
        List<XYZ> xyzs=new ArrayList<>();
        String[] months={"12","01","02","03","04","05","06","07","08","09"};
        for(String mon:months){
        	XYZ xyz=new XYZ();
        	xyz.setX(mon);
        	xyz.setY("0");xyz.setZ("0");
        	for(int i=resultList.size()-1;i>=0;i--){
        		Map m=resultList.get(i);
        		String x=(String) m.get("b_date");
        		if(mon.equals(x)){
        			Double value=(Double) m.get("monthCount");
        			BigDecimal b = new BigDecimal(value);
        			int f1 = b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        			xyz.setY(f1/10000+"");
        		}
        	}
        	for(int i=quantity.size()-1;i>=0;i--){
        		Map m=quantity.get(i);
        		String x=(String) m.get("b_date");
        		if(mon.equals(x)){
        			Integer value=(Integer) m.get("monthCount");
        			xyz.setZ(value+"");
        		}
        	}
        	xyzs.add(xyz);
        }
        JSONArray o=(JSONArray) JSONObject.toJSON(xyzs);
        response.getWriter().print(o.toString());
	}
	/**
	 * 加载最近月份
	 */
	@RequestMapping("/loadMonth")
	public void loadMonth(HttpServletRequest request,HttpServletResponse response,@PathVariable String column) throws IOException{
        Date now=new Date();
        Calendar ca=Calendar.getInstance();
        ca.setTime(now);
        int nowMonth=ca.get(Calendar.MONTH);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime=sdf.format(now);
        for(int i=-2;i<3;i++){
        	ca.set(Calendar.MONTH,ca.get(Calendar.MONTH)+i);
        	Date d=ca.getTime();
        }
		JSONArray ja=new JSONArray();
        JSONObject jo=new JSONObject();
        jo.put("name", "");
//        jo.put("value", f1/1000);
        ja.add(jo);
        response.getWriter().print(ja.toString());
	}
	/**
	 * 欠款超期 金额汇总
	 */
	@RequestMapping("/overDue/{column}")
	public void overDue(HttpServletRequest request,HttpServletResponse response,@PathVariable String column) throws IOException{
		List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
        resultList=userMapper.superSelect("select sum(isnull(not_due,0)) not_due,"
        		+ "sum(isnull(overdue_0_30,0))+sum(isnull(overdue_31_60,0))+sum(isnull(overdue_61_90,0)) over_0_90,"
        		+ "sum(isnull(overdue_91_180,0))+sum(isnull(overdue_181_360,0))+sum(isnull(overdue_361_,0)) over_91_ "
        		+ "from b_ar_report");
        Double value=(Double) resultList.get(0).get(column);
        BigDecimal b = new BigDecimal(value);
		int f1 = b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        JSONArray ja=new JSONArray();
        JSONObject jo=new JSONObject();
        jo.put("name", "");
        jo.put("value", f1/10000);
        ja.add(jo);
        response.getWriter().print(ja.toString());
	}
	/**
	 * 产品数量汇总
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	@RequestMapping("/productQuantity")
	public void productQuantity(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<XYS> xyss=new ArrayList<XYS>();
		List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
        resultList=userMapper.superSelect("select SUM(y) y,x,s "
        		+ "from (select billed_quantity y,material_desc s,"
        		+ "(substring(CONVERT(varchar(25),billing_date,121),1,7)+'-01 00:00:00') x "
        		+ "from b_invoice_detail) gr group by x,s order by x");
        for(Map m:resultList){
        	XYS xys=new XYS();
        	Date da=sdf1.parse((String) m.get("x"));
        	xys.setX(sdf.format(da));
        	xys.setY(Double.valueOf((Integer) m.get("y")));
        	xys.setS(((String) m.get("s")).toUpperCase());
        	xyss.add(xys);
        }
        JSONArray o=(JSONArray) JSONObject.toJSON(xyss);
        response.getWriter().print(o.toString());
	}
	/**
	 * 总金额统计-根据url传过来的字段名获取
	 */
	@RequestMapping("/amount/{column}")
	public void amount(HttpServletRequest request,HttpServletResponse response,@PathVariable String column) throws IOException{
		List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
        resultList=userMapper.superSelect("select sum(item_net_value)/10000 itemNetValue,"
        		+ "sum(billing_amount_inc_vat)/10000 billingAmountIncVat,"
        		+ "sum(amount_paid)/10000 amountPaid "
        		+ "from b_invoice_detail "
        		+ "where report_time>="
        		+ "(select substring(CONVERT(varchar(25),MAX(report_time),121),1,10)+' 00:00:00.000' "
        		+ "from b_invoice_detail) ");
        Double value=(Double) resultList.get(0).get(column);
        BigDecimal b = new BigDecimal(value);
		int f1 = b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        JSONArray ja=new JSONArray();
        JSONObject jo=new JSONObject();
        jo.put("name", "");
        jo.put("value", f1);
        ja.add(jo);
        response.getWriter().print(ja.toString());
	}
	/**
	 * 商务KPI
	 */
	@RequestMapping("/businessKPI")
	public void businessKPI(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<XYS> xyss=new ArrayList<XYS>();
		List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
        resultList=userMapper.superSelect("select (case kpi_name when '应收账款跟进催收及时性' then '1' "
        		+ "when '应收账款回款及时性' then '2' end) s,lsp_name,rate,kpi_month "
        		+ "from kpi_b_report "
        		+ "where kpi_month>=("
        		+ "select substring(CONVERT(varchar(25),DATEADD(MONTH,-1,GETDATE()),121),1,7)+'-01 00:00:00.000')");
        for(Map m:resultList){
        	XYS xys=new XYS();
        	String name=(String) m.get("lsp_name");
        	xys.setX(lspName.get(name.trim()));
        	xys.setY(Double.valueOf((String) m.get("rate")));
        	xys.setS((String) m.get("s"));
        	xyss.add(xys);
        }
        JSONArray o=(JSONArray) JSONObject.toJSON(xyss);
        response.getWriter().print(o.toString());
	}
	/**
	 * 发票类型金额比例
	 */
	@RequestMapping("/billTypeRate")
	public void billTypeRate(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<XY> xys=new ArrayList<XY>();
		List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
        resultList=userMapper.superSelect("select SUM(billing_amount_inc_vat) amount,COUNT(*) count,bill_type from b_invoice_detail group by bill_type");
        for(Map m:resultList){
        	Integer value=(Integer) m.get("count");
//        	BigDecimal b = new BigDecimal(value);
//        	int f1 = b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
//        	int f2=Math.abs(f1);
        	XY xy=new XY();
        	xy.setX((String) m.get("bill_type"));
        	xy.setY(Double.valueOf(value));
        	xys.add(xy);
        }
        JSONArray o=(JSONArray) JSONObject.toJSON(xys);
        response.getWriter().print(o.toString());
	}
	/**
	 * 待回款总额
	 */
	@RequestMapping("/notPaid/{column}")
	public void notPaid(@PathVariable String column,HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
		String query="";
		if(column!=null&&"16".equals(column))
			query="select SUM(billing_amount_inc_vat-amount_paid)/10000 notpaid from b_invoice_detail "
        		+ "where report_time>=(select substring(CONVERT(varchar(25),MAX(report_time),121),1,10)+' 00:00:00.000' "
        		+ "from b_invoice_detail) and billing_date<'2017-01-01 00:00:00.000'";
		else if(column!=null&&"17".equals(column))
			query="select SUM(billing_amount_inc_vat-amount_paid)/10000 notpaid from b_invoice_detail "
	        		+ "where report_time>=(select substring(CONVERT(varchar(25),MAX(report_time),121),1,10)+' 00:00:00.000' "
	        		+ "from b_invoice_detail) and billing_date>='2017-01-01 00:00:00.000'";
		else
			query="select SUM(billing_amount_inc_vat-amount_paid) notpaid from b_invoice_detail "
	        		+ "where report_time>=(select substring(CONVERT(varchar(25),MAX(report_time),121),1,10)+' 00:00:00.000' "
	        		+ "from b_invoice_detail) ";
        resultList=userMapper.superSelect(query);
        Double value=(Double) resultList.get(0).get("notpaid");
        BigDecimal b = new BigDecimal(value);
		int f1 = b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        JSONArray ja=new JSONArray();
        JSONObject jo=new JSONObject();
        jo.put("name", "");
        jo.put("value", f1);
        ja.add(jo);
        response.getWriter().print(ja.toString());
	}
	/**
	 * 月份发票总额
	 * @throws IOException 
	 */
	@RequestMapping("/getCountMonth")
	public void getCount(HttpServletRequest request,HttpServletResponse response) throws IOException{
        List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
        resultList=userMapper.superSelect("select SUM(d.item_net_value) amount_count,d.month "+	
        		"from (select item_net_value,substring(CONVERT(varchar(12),billing_date,112),1,6) month  "
        		+ "from b_invoice_detail) d group by d.month order by d.month");
        List<XYS> li=new ArrayList<XYS>();
        for(Map<?, ?> m:resultList){
        	XYS xys=new XYS();
        	String month=(String) m.get("month");
			xys.setX(month.substring(4, month.length())+"月");
			xys.setY(((Double) m.get("amount_count"))/10000);
			xys.setS("1");
			li.add(xys);
        }
//		li.add(r);
        JSONArray o=(JSONArray) JSONObject.toJSON(li);
        response.getWriter().print(o.toString());
	}
	/**
	 * 商务服务公司欠款比例
	 * @throws IOException 
	 */
	@RequestMapping("/getBusinessOverdueRate")
	public void businessDue(HttpServletRequest request,HttpServletResponse response) throws IOException{
        List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
        resultList=userMapper.superSelect("select (sum(billing_amount_inc_vat-amount_paid)) rate,"
        		+ "service_provider from b_invoice_detail where status!='已清账' group by service_provider");
        List<XY> li=new ArrayList<XY>();
        
        for(Map<?, ?> m:resultList){
        	XY xy=new XY();
			xy.setX(lspName.get((m.get("service_provider"))));
			Double d=(Double) m.get("rate");
			BigDecimal b = new BigDecimal(d);
			double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
			xy.setY(f1);
			li.add(xy);
        }
//		li.add(r);
        JSONArray o=(JSONArray) JSONObject.toJSON(li);
        response.getWriter().print(o.toString());
	}
	@RequestMapping("/provinceSalesOrder")
	public void provinceOrderC(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<XY> xys=provinceOrder();
		JSONArray o=(JSONArray) JSONObject.toJSON(xys);
        response.getWriter().print(o.toString());
	}
	public List<XY> provinceOrder(){
		List<XY> xys=new ArrayList<XY>();
		//省份销售额
		List<LinkedHashMap<String, Object>> saleCount=new ArrayList<LinkedHashMap<String, Object>>();
		saleCount=userMapper.superSelect("select SUM(billing_amount_inc_vat) count,province "
				+ "from b_invoice_detail "
				+ "where report_time>=(select substring(CONVERT(varchar(25),MAX(report_time),121),1,10)+' 00:00:00.000' from b_invoice_detail) "
				+ "group by province order by count desc");
		int i=1;
		for(Map<?, ?> m:saleCount){
			XY xy=new XY();
			xy.setX((String) m.get("province"));
			xy.setY((Double) m.get("count"));
			xys.add(xy);
			if(i>10)
				break;
			i++;
		}
		return xys;
	}
	/**
	 * 省份销售额热力图
	 */
	@RequestMapping("/provinceMap")
	public void provinceMap(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<IdValueInfo> li=new ArrayList<IdValueInfo>();
		//读取省份代码json数据
		Map<String,JSONObject> map=getProvinceJson("name");
		//商务服务商负责省份
		List<LinkedHashMap<String, Object>> businessProvince=new ArrayList<LinkedHashMap<String, Object>>();
		businessProvince=userMapper.superSelect("select * from d_lsp where isbusiness='1'");
		//省份销售额
		List<LinkedHashMap<String, Object>> saleCount=new ArrayList<LinkedHashMap<String, Object>>();
		saleCount=userMapper.superSelect("select SUM(billing_amount_inc_vat) count,province "
				+ "from b_invoice_detail "
				+ "where report_time>=(select substring(CONVERT(varchar(25),MAX(report_time),121),1,10)+' 00:00:00.000' from b_invoice_detail) "
				+ "group by province order by count desc");
		Map<String,Double> count=new HashMap<String, Double>();
		for(Map m:saleCount){
			Double f = (Double) m.get("count");
			if(f!=null){
				BigDecimal b = new BigDecimal(f/10000);
				double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				count.put((String) m.get("province"),f1);
			}
		}
		for(Map<?, ?> m:businessProvince){
			String businessProvinces=(String) m.get("b_province");
			for(String p:businessProvinces.split("/")){
				IdValueInfo id=new IdValueInfo();
				JSONObject jo=map.get(p);
				Double value=count.get(p.replaceAll("省", "").replaceAll("市", ""));
				if(value==null){
					if(p.length()>3)
						value=count.get(p.substring(0, 3));
					if(value==null&&p.length()>2)
						value=count.get(p.substring(0, 2));
				}
				if(jo!=null){
					id.setId(jo.getString("adcode"));
					id.setValue(value==null?0:value);
					id.setInfo(p+"-"+m.get("lsp_name")+" "+(value==null?0:value)+"万");
					li.add(id);
				}else{
					System.out.println(p+" 找不到省份");
				}
			}
		}
		JSONArray o=(JSONArray) JSONObject.toJSON(li);
        response.getWriter().print(o.toString());
	}
	
	public static Map<String, JSONObject> getProvinceJson(String keyName) throws Exception{
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
