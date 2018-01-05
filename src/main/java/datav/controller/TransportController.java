package datav.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import datav.entity.XY;
import datav.entity.XYS2;
import datav.entity.XYZ;
import gsk.portal.quartz.dao.UserMapper;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/datav")
public class TransportController {

	@Autowired
	private UserMapper userMapper;
	
	
	@RequestMapping("/transDays")
	public void transDays(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<LinkedHashMap<String, Object>> result1=new ArrayList<LinkedHashMap<String, Object>>();
        result1=userMapper.superSelect("select sum(DATEDIFF ( DAY ,start_time ,reach_time ) ) amount,substring(d_date,6,2) b_date "
        		+ "from(select substring(CONVERT(varchar(25),document_date,121),1,7)+'-1 00:00:00.000' d_date,start_time,reach_time "
        		+ "from v_shipping_order v left join l_transport l on  v.id=l.delivery_number) "
        		+ "s group by d_date order by d_date");
        List<XYS2> xyss=new ArrayList<>();
        for(Map m:result1){
        	XYS2 xys=new XYS2();
        	xys.setX((String) m.get("b_date"));
        	xys.setY(m.get("amount")+"");
        	xys.setS("1");
        	xyss.add(xys);
        }
        JSONArray o=(JSONArray) JSONObject.toJSON(xyss);
        response.getWriter().print(o.toString());
	}
	@RequestMapping("/productRate")
	public void productRate(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<LinkedHashMap<String, Object>> result1=new ArrayList<LinkedHashMap<String, Object>>();
        result1=userMapper.superSelect("select SUM(delivery_quantity) sum,material_description "
        		+ "from l_shipping_order group by material_description");
        List<XY> xys=new ArrayList<>();
        for(Map m:result1){
        	XY xy=new XY();
        	String x=(String) m.get("material_description");
        	if(x.indexOf(" ")>0)
        		x=x.substring(x.lastIndexOf(" "));
        	xy.setX(x);
        	xy.setY(Double.valueOf(m.get("sum")+""));
        	xys.add(xy);
        }
        JSONArray o=(JSONArray) JSONObject.toJSON(xys);
        response.getWriter().print(o.toString());
	}
	@RequestMapping("/lkpi")
	public void getLKPI(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<LinkedHashMap<String, Object>> result1=new ArrayList<LinkedHashMap<String, Object>>();
        result1=userMapper.superSelect("select * from kpi_l_report "
        		+ "where kpi_month=(select substring(CONVERT(varchar(25),DATEADD(MONTH,-1,GETDATE()),121),1,7)+'-1 00:00:00.000')"
        		+ "and kpi_name ='运输签收单返回及时率'");
        List<LinkedHashMap<String, Object>> result2=new ArrayList<LinkedHashMap<String, Object>>();
        result2=userMapper.superSelect("select * from kpi_l_report "
        		+ "where kpi_month=(select substring(CONVERT(varchar(25),DATEADD(MONTH,-1,GETDATE()),121),1,7)+'-1 00:00:00.000')"
        		+ "and kpi_name ='运输签收单返回完整率'");
        List<XYZ> xyzs=new ArrayList<>();
        for(String k:lspNameT.keySet()){
        	XYZ xyz=new XYZ();
        	xyz.setX(lspNameT.get(k));
        	xyz.setY("0");xyz.setZ("0");
        	for(Map m:result1){
        		if(k.equals(m.get("lsp_name")))
        			xyz.setY((String) m.get("rate"));
        	}
        	for(Map m:result2){
        		if(k.equals(m.get("lsp_name")))
        			xyz.setZ((String) m.get("rate"));
        	}
        	xyzs.add(xyz);
        }
        JSONArray o=(JSONArray) JSONObject.toJSON(xyzs);
        response.getWriter().print(o.toString());
	}
	
	private Map<String,String> lspNameT;
	{
		lspNameT=new HashMap<String, String>();
		lspNameT.put("安徽天星医药集团有限公司", "安徽天星");
		lspNameT.put("北京科园信海医药经营有限公司", "北京科园");
		lspNameT.put("广西泛北部湾物流有限公司", "广西泛北部湾");
		lspNameT.put("国药集团西南医药有限公司", "国药西南");
		lspNameT.put("国药集团医药物流有限公司", "国药物流");
		lspNameT.put("国药控股广东物流有限公司", "国药广州");
		lspNameT.put("国药控股海南有限公司", "国药海南");
		lspNameT.put("国药控股湖北有限公司", "国药湖北");
		lspNameT.put("江西汇仁集团医药科研营销有限公司", "江西汇仁");
		lspNameT.put("康德乐（上海）医药有限公司", "康德乐");
		lspNameT.put("南京医药股份有限公司", "南京医药");
		lspNameT.put("顺丰医药供应链有限公司", "顺丰");
		lspNameT.put("重庆医药（集团）股份有限公司", "重庆医药");
	}
}
