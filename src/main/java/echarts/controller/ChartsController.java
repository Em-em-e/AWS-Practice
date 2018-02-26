package echarts.controller;

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
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import echarts.model.KeyValue;
import echarts.util.HolidayUtil;
import gsk.portal.quartz.dao.UserMapper;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/echarts")
public class ChartsController {
	@Autowired
	private UserMapper userMapper;
	
	private Map<String,String> spdate=new HashMap<>();
	/**
	 * 主页面
	 * @throws Exception 
	 */
	@RequestMapping("/main")
	public String homePage(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
		if(!isHavePormittion(request, response)) {
			return "nopermit";
		}
		return "index";
	}
	
	@RequestMapping("/currentMonthAllData")
	public void refreshData(HttpServletRequest request,HttpServletResponse response,
			String bu1,String bu2,String bu3,String brand,String sku_code,String month)throws Exception {
		String timout=response.getHeader("sessionstatus");
		if(timout!=null&&"timeout".equals(timout)) {//ajax请求在filter中验证没有登录，转到这里返回，前端跳转
			response.getWriter().print("timeout");
			return;
		}
		if(!isHavePormittion(request, response)) {
			return;
		}
		JSONArray allData=new JSONArray();
		Calendar calendar=Calendar.getInstance();
		if("lastMonthAllData".equals(month)) {
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		}else {
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -1);
		}
		Calendar ca2=(Calendar) calendar.clone();//由于方法内部会改变calendar内容，需要分别传入不同的calendar对象
		Calendar ca3=(Calendar) calendar.clone();
		Calendar ca4=(Calendar) calendar.clone();
		Calendar ca5=(Calendar) calendar.clone();
		Calendar ca6=(Calendar) calendar.clone();
		Integer[] days=getDays(calendar);
		JSONArray pieData=pieData(bu1, bu2, bu3, brand, sku_code,ca2);
		JSONArray gridData=gridData(bu1, bu2, bu3, brand, sku_code,ca3);
		JSONArray barData=barData(bu1, bu2, bu3, brand, sku_code,ca4);
		JSONArray powerData=powerBrands(bu1, bu2,ca5);
		allData.add(JSONObject.toJSON(days));
		allData.add(pieData);
		allData.add(gridData);
		allData.add(barData);
		allData.add(powerData);
		SimpleDateFormat sdf=new SimpleDateFormat("MMM dd",Locale.ENGLISH);
		allData.add(sdf.format(ca6.getTime()));
		response.getWriter().print(allData.toString());
	}
	
//	@RequestMapping("/powerBrandsData")
	public JSONArray powerBrands(String bu1,String bu2, Calendar calendar) throws Exception{
		String column="";//查询条件列
		String query="";//查询条件
		if(isNull(bu2)) {
			query=bu1;
			column="bu1";
		}
		else {
			query=bu2;
			column="bu2";
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String nowDate=sdf.format(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String monthBegin=sdf.format(calendar.getTime());
		String monthString=(calendar.get(Calendar.MONTH)+1)+"";
		List<LinkedHashMap<String, Object>> act=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> plan=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> ly=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> drm=new ArrayList<LinkedHashMap<String, Object>>();
		if("all".equals(query)) {
			act=userMapper.superSelect("select sum(value) act,m.brand from echarts_daily_sales d "
					+"left join echarts_main_data m on d.sku_code=m.sku_code "
					+ "where d.sales_date>='"+monthBegin+"' and d.sales_date<='"+nowDate+"' and is_key_brand='Y' group by m.brand");
			plan=userMapper.superSelect("select (sum(value))*1000 pla,m.brand from echarts_plan p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and is_key_brand='Y' group by m.brand");
			ly=userMapper.superSelect("select (sum(value))*1000 ly,m.brand from echarts_last_year_act p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and is_key_brand='Y' group by m.brand");
			drm=userMapper.superSelect("select (sum(value))*1000 drm,m.brand from echarts_last_year_act p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and is_key_brand='Y' group by m.brand");
		}else {
			act=userMapper.superSelect("select sum(value) act,m.brand from echarts_daily_sales d "
					+"left join echarts_main_data m on d.sku_code=m.sku_code "
					+ "where d.sales_date>='"+monthBegin+"' and d.sales_date<='"+nowDate+"' and is_key_brand='Y' and m."+column+"='"+query+"' group by m.brand");
			plan=userMapper.superSelect("select (sum(value))*1000 pla,m.brand from echarts_plan p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and is_key_brand='Y' and m."+column+"='"+query+"' group by m.brand");
			ly=userMapper.superSelect("select (sum(value))*1000 ly,m.brand from echarts_last_year_act p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and is_key_brand='Y' and m."+column+"='"+query+"' group by m.brand");
			drm=userMapper.superSelect("select (sum(value))*1000 drm,m.brand from echarts_plan_drm p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and is_key_brand='Y' and m."+column+"='"+query+"' group by m.brand");
		}
		StringBuilder yAxis=new StringBuilder();
		String [] dataP=new String[plan.size()];
		String [] dataD=new String[plan.size()];
		String [] dataL=new String[plan.size()];;;
		String [] dataPb=new String[plan.size()];//柱状条背景
		String [] dataDb=new String[plan.size()];
		String [] dataLb=new String[plan.size()];
		if(plan.size()==0) {
			yAxis.append("             ");
		}
		for(int i=0;i<plan.size();i++) {
			String brand=(String) plan.get(i).get("brand");
			yAxis.append(brand+",");
			Double aDouble=0.0;
			for(int j=0;j<act.size();j++) {
				if(brand.equals(act.get(j).get("brand"))) {
					aDouble=(Double) act.get(j).get("act");
				}
			}
			Double p=plan==null?0:plan.get(i)==null?0:(Double)plan.get(i).get("pla");
			Double d=drm==null?0:drm.get(i)==null?0:(Double)drm.get(i).get("drm");
			Double l=ly==null?0:ly.get(i)==null?0:(Double)ly.get(i).get("ly");
			if(p!=0) {
				BigDecimal bigD=new BigDecimal((aDouble)/p*100);
				bigD=bigD.setScale(0, BigDecimal.ROUND_HALF_UP);
				dataP[i]=bigD.toString();
				if(bigD.intValue()<100)
					dataPb[i]=(100.0-Integer.valueOf(dataP[i]))+"";
				else
					dataPb[i]=0+"";
			}else {
				dataP[i]="N/A";
				dataPb[i]="N/A";
			}
			if(d!=0) {
				BigDecimal bigD=new BigDecimal((aDouble)/d*100);
				bigD=bigD.setScale(0, BigDecimal.ROUND_HALF_UP);
				dataD[i]=bigD.toString();
				if(bigD.intValue()<100)
					dataDb[i]=(100.0-Integer.valueOf(dataD[i]))+"";
				else
					dataDb[i]=0+"";
			}else {
				dataD[i]="N/A";
				dataDb[i]="N/A";
			}
			if(l!=0) {
				BigDecimal bigD=new BigDecimal((aDouble)/l*100);
				bigD=bigD.setScale(0, BigDecimal.ROUND_HALF_UP);
				dataL[i]=bigD.toString();
				if(bigD.intValue()<100)
					dataLb[i]=(100.0-Integer.valueOf(dataL[i]))+"";
				else
					dataLb[i]=0+"";
			}else {
				dataL[i]="N/A";
				dataLb[i]="N/A";
			}
		}
		JSONArray all=new JSONArray();
		JSONArray o=null;
		if(yAxis!=null&&!"".equals(yAxis.toString())) {
			o=(JSONArray) JSONObject.toJSON(yAxis.toString().substring(0, yAxis.toString().length()-1).split(","));
			all.add(o);
		}else
			o=(JSONArray) JSONObject.toJSON(new String[8]);
		o=(JSONArray)JSONObject.toJSON(dataL);all.add(o);
		o=(JSONArray)JSONObject.toJSON(dataP);all.add(o);
		o=(JSONArray)JSONObject.toJSON(dataD);all.add(o);
		o=(JSONArray)JSONObject.toJSON(dataLb);all.add(o);
		o=(JSONArray)JSONObject.toJSON(dataPb);all.add(o);
		o=(JSONArray)JSONObject.toJSON(dataDb);all.add(o);
		return all;
//		response.getWriter().print(all.toString());
	}
	
//	@RequestMapping("/gridData")
	public JSONArray gridData(String bu1,String bu2,String bu3,String brand,String sku_code, Calendar calendar) throws Exception {
    	//这个月过了多少天-actual
		Double [] days=new Double[calendar.get(Calendar.DAY_OF_MONTH)+1];
		//这个月共有多少天-plan
		Double [] planDays=new Double[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1];
		for(int i=0;i<planDays.length;i++)
			planDays[i]=0.0;
		for(int i=0;i<days.length;i++)
			days[i]=0.0;
		String column="";//查询条件列
		String query="";//查询条件
		//查询列优先级 sku_code>brand>bu3>bu2>bu1
		if(isNull(sku_code)) { 
			if(isNull(brand))
				if(isNull(bu3))
					if(isNull(bu2)) {
						query=bu1;
						column="bu1";
					}
					else {
						query=bu2;
						column="bu2";
					}
				else {
					query=bu3;
					column="bu3";
				}
			else {
				query=brand;
				column="brand";
			}
		}else {
			query=sku_code;
			column="sku_code";
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String nowDate=sdf.format(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String monthBegin=sdf.format(calendar.getTime());
		String monthString=(calendar.get(Calendar.MONTH)+1)+"";
		List<LinkedHashMap<String, Object>> act=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> plan=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> drm=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> u2=new ArrayList<LinkedHashMap<String, Object>>();
		if("all".equals(query)) {
			act=userMapper.superSelect("select * from echarts_daily_sales d "
					+"left join echarts_main_data m on d.sku_code=m.sku_code "
					+ "where d.sales_date>='"+monthBegin+"' and d.sales_date<='"+nowDate+"'");
			plan=userMapper.superSelect("select (sum(value)) pla from echarts_plan p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"'");
			drm=userMapper.superSelect("select (sum(value)) drm from echarts_plan_drm p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"'");
			u2=userMapper.superSelect("select (sum(value)) u2 from echarts_plan_u2 p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"'");
		}else {
			act=userMapper.superSelect("select * from echarts_daily_sales d "
					+"left join echarts_main_data m on d.sku_code=m.sku_code "
					+ "where d.sales_date>='"+monthBegin+"' and d.sales_date<='"+nowDate+"' and m."+column+"='"+query+"'");
			plan=userMapper.superSelect("select (sum(value)) pla from echarts_plan p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and m."+column+"='"+query+"'");
			drm=userMapper.superSelect("select (sum(value)) drm from echarts_plan_drm p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and m."+column+"='"+query+"'");
			u2=userMapper.superSelect("select (sum(value)) u2 from echarts_plan_u2 p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and m."+column+"='"+query+"'");
		}
		
		Double p=plan==null?0:plan.get(0)==null?0:(Double)plan.get(0).get("pla");
		Double d=drm==null?0:drm.get(0)==null?0:(Double)drm.get(0).get("drm");
		Double u=u2==null?0:u2.get(0)==null?0:(Double)u2.get(0).get("u2");
		for(Map m:act) {//按日期将数据放入数组
			Date date=(Date) m.get("sales_date");
			Calendar c=Calendar.getInstance();
			c.setTime(date);
			int day=c.get(Calendar.DAY_OF_MONTH);
			Double value=(Double) m.get("value");
			if(day<days.length)
				days[day]=days[day]+value/1000;
		}
		for(int i=0;i<days.length;i++) {//数据累加
			if(i!=0) {
				BigDecimal value=new BigDecimal(days[i-1]);
				days[i]+=days[i-1];
				Double doubleValue=value.setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
				days[i-1]=doubleValue;
			}
		}
		days[days.length-1]=new BigDecimal(days[days.length-1]).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		JSONArray o=(JSONArray) JSONObject.toJSON(days);
		JSONArray all=new JSONArray();
		all.add(o);
		int lastDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, lastDay);
		Date lastDateMonth=calendar.getTime();
		int workDaysMonthly=HolidayUtil.getHolidays(lastDateMonth, spdate);
		Double pDouble=p/workDaysMonthly;
		for(int i=0;i<planDays.length;i++) {
			if(i!=0) {
				BigDecimal value=new BigDecimal(planDays[i-1]);
				if(!HolidayUtil.isHoliday(i,spdate,calendar))
					planDays[i]=planDays[i-1]+pDouble;
				else
					planDays[i]=planDays[i-1];
				Double doubleValue=value.setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
				planDays[i-1]=doubleValue;
			}
		}
		planDays[planDays.length-1]=new BigDecimal(planDays[planDays.length-1]).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		o=(JSONArray) JSONObject.toJSON(planDays);
		all.add(o);
		return all;
//		response.getWriter().print(all.toString());
	}
	
//	@RequestMapping("/barData")
	public JSONArray barData(String bu1,String bu2,String bu3,String brand,String sku_code, Calendar calendar) throws IOException {
		Double [] datas=new Double[7];
		for(int i=0;i<datas.length;i++)
			datas[i]=0.0;
		String column="";
		String query="";
		if(isNull(sku_code)) { 
			if(isNull(brand))
				if(isNull(bu3))
					if(isNull(bu2)) {
						query=bu1;
						column="bu1";
					}
					else {
						query=bu2;
						column="bu2";
					}
				else {
					query=bu3;
					column="bu3";
				}
			else {
				query=brand;
				column="brand";
			}
		}else {
			query=sku_code;
			column="sku_code";
		}
		List<LinkedHashMap<String, Object>> act=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> mtdAct=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> plan=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> drm=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> u2=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> ly=new ArrayList<LinkedHashMap<String, Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String nowDate=sdf.format(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String monthBegin=sdf.format(calendar.getTime());
		String monthString=(calendar.get(Calendar.MONTH)+1)+"";
		if("all".equals(query)) {
			act=userMapper.superSelect("select sum(value)/1000 value,sum(volume)/1000 volume from echarts_daily_sales d "
					+"left join echarts_main_data m on d.sku_code=m.sku_code "
					+ "where d.sales_date='"+nowDate+"'");
			mtdAct=userMapper.superSelect("select sum(value)/1000 value,sum(volume)/1000 volume from echarts_daily_sales d "
					+"left join echarts_main_data m on d.sku_code=m.sku_code "
					+ "where d.sales_date>='"+monthBegin+"' and d.sales_date<='"+nowDate+"'");
			plan=userMapper.superSelect("select (sum(value)) planValue,(sum(volume)) planVolume from echarts_plan p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"'");
			drm=userMapper.superSelect("select (sum(value)) drmValue,(sum(volume)) drmVolume from echarts_plan_drm p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"'");
			u2=userMapper.superSelect("select (sum(value)) u2Value,(sum(volume)) u2Volume from echarts_plan_u2 p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"'");
			ly=userMapper.superSelect("select (sum(value)) lyValue,(sum(volume)) lyVolume from echarts_last_year_act p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"'");
		}else {
			act=userMapper.superSelect("select sum(value)/1000 value,sum(volume)/1000 volume from echarts_daily_sales d "
					+"left join echarts_main_data m on d.sku_code=m.sku_code "
					+ "where d.sales_date='"+nowDate+"' and m."+column+"='"+query+"'");
			mtdAct=userMapper.superSelect("select sum(value)/1000 value,sum(volume)/1000 volume from echarts_daily_sales d "
					+"left join echarts_main_data m on d.sku_code=m.sku_code "
					+ "where d.sales_date>='"+monthBegin+"' and d.sales_date<='"+nowDate+"' and m."+column+"='"+query+"'");
			plan=userMapper.superSelect("select (sum(value)) planValue,(sum(volume)) planVolume from echarts_plan p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and m."+column+"='"+query+"'");
			drm=userMapper.superSelect("select (sum(value)) drmValue,(sum(volume)) drmVolume from echarts_plan_drm p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and m."+column+"='"+query+"'");
			u2=userMapper.superSelect("select (sum(value)) u2Value,(sum(volume)) u2Volume from echarts_plan_u2 p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and m."+column+"='"+query+"'");
			ly=userMapper.superSelect("select (sum(value)) lyValue,(sum(volume)) lyVolume from echarts_last_year_act p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and m."+column+"='"+query+"'");
		}
		Double actVa=act==null?0:act.get(0)==null?0:(Double)act.get(0).get("value");
		Double actVo=act==null?0:act.get(0)==null?0:(Double)act.get(0).get("volume");
		Double mtdVa=mtdAct==null?0:mtdAct.get(0)==null?0:(Double)mtdAct.get(0).get("value");
		Double mtdVo=mtdAct==null?0:mtdAct.get(0)==null?0:(Double)mtdAct.get(0).get("volume");
		Double pva=plan==null?0:plan.get(0)==null?0:(Double)plan.get(0).get("planValue");
		Double pvo=plan==null?0:plan.get(0)==null?0:(Double)plan.get(0).get("planVolume");
		Double dva=drm==null?0:drm.get(0)==null?0:(Double)drm.get(0).get("drmValue");
		Double dvo=drm==null?0:drm.get(0)==null?0:(Double)drm.get(0).get("drmVolume");
		Double uva=drm==null?0:u2.get(0)==null?0:(Double)u2.get(0).get("u2Value");
		Double uvo=drm==null?0:u2.get(0)==null?0:(Double)u2.get(0).get("u2Volume");
		Double lva=drm==null?0:ly.get(0)==null?0:(Double)ly.get(0).get("lyValue");
		Double lvo=drm==null?0:ly.get(0)==null?0:(Double)ly.get(0).get("lyVolume");
		actVa=new BigDecimal(actVa).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		actVo=new BigDecimal(actVo).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		mtdVa=new BigDecimal(mtdVa).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		mtdVo=new BigDecimal(mtdVo).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		pva=new BigDecimal(pva).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		pvo=new BigDecimal(pvo).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		dva=new BigDecimal(dva).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		dvo=new BigDecimal(dvo).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		uva=new BigDecimal(uva).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		uvo=new BigDecimal(uvo).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		lva=new BigDecimal(lva).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		lvo=new BigDecimal(lvo).setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		datas[0]=actVa;datas[1]=mtdVa;datas[2]=pva;datas[3]=uva;datas[4]=0.0;datas[5]=lva;datas[6]=dva;
		JSONArray o=(JSONArray) JSONObject.toJSON(datas);
		JSONArray all=new JSONArray();
		all.add(o);
		datas[0]=actVo;datas[1]=mtdVo;datas[2]=pvo;datas[3]=uvo;datas[4]=0.0;datas[5]=lvo;datas[6]=dvo;
		o=(JSONArray) JSONObject.toJSON(datas);
		all.add(o);
		return all;
//		response.getWriter().print(all.toString());
	}
	
	@RequestMapping("/pieData")
	public JSONArray pieData(String bu1,String bu2,String bu3,String brand,String sku_code, Calendar calendar) throws IOException {
		String column="";
		String query="";
		if(isNull(sku_code)) { 
			if(isNull(brand))
				if(isNull(bu3))
					if(isNull(bu2)) {
						query=bu1;
						column="bu1";
					}
					else {
						query=bu2;
						column="bu2";
					}
				else {
					query=bu3;
					column="bu3";
				}
			else {
				query=brand;
				column="brand";
			}
		}else {
			query=sku_code;
			column="sku_code";
		}
		//获取当前日期
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	String nowDate=sdf.format(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String monthBegin=sdf.format(calendar.getTime());
		String monthString=(calendar.get(Calendar.MONTH)+1)+"";
		List<LinkedHashMap<String, Object>> act=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> plan=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> drm=new ArrayList<LinkedHashMap<String, Object>>();
		List<LinkedHashMap<String, Object>> u2=new ArrayList<LinkedHashMap<String, Object>>();
		if("all".equals(query)) {
			act=userMapper.superSelect("select sum(value) act from echarts_daily_sales d "
					+"left join echarts_main_data m on d.sku_code=m.sku_code "
					+ "where d.sales_date>='"+monthBegin+"' and d.sales_date<='"+nowDate+"'");
			plan=userMapper.superSelect("select (sum(value))*1000 pla from echarts_plan p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"'");
			drm=userMapper.superSelect("select (sum(value))*1000 drm from echarts_plan_drm p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"'");
			u2=userMapper.superSelect("select (sum(value))*1000 u2 from echarts_plan_u2 p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"'");
		}else {
			act=userMapper.superSelect("select sum(value) act from echarts_daily_sales d "
					+"left join echarts_main_data m on d.sku_code=m.sku_code "
					+ "where d.sales_date>='"+monthBegin+"' and d.sales_date<='"+nowDate+"' and m."+column+"='"+query+"'");
			plan=userMapper.superSelect("select (sum(value))*1000 pla from echarts_plan p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and m."+column+"='"+query+"'");
			drm=userMapper.superSelect("select (sum(value))*1000 drm from echarts_plan_drm p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and m."+column+"='"+query+"'");
			u2=userMapper.superSelect("select (sum(value))*1000 u2 from echarts_plan_u2 p "
					+ "left join echarts_main_data m on p.sku_code=m.sku_code "
					+ "where p.month='"+monthString+"' and m."+column+"='"+query+"'");
		}
		Double a=act==null?0:act.get(0)==null?0:(Double)act.get(0).get("act");
		Double p=plan==null?0:plan.get(0)==null?0:(Double)plan.get(0).get("pla");
		Double d=drm==null?0:drm.get(0)==null?0:(Double)drm.get(0).get("drm");
		Double u=drm==null?0:u2.get(0)==null?0:(Double)u2.get(0).get("u2");
		List<KeyValue> li=new ArrayList<>();
		KeyValue pKeyValue=new KeyValue();pKeyValue.setKey("plan");
		KeyValue dKeyValue=new KeyValue();dKeyValue.setKey("drm");
		KeyValue uKeyValue=new KeyValue();uKeyValue.setKey("u2");
		if(p==0) {
			pKeyValue.setValue("N/A");
		}else {
			Double rateP=a/p;
			BigDecimal   b   =   new   BigDecimal(rateP*100); 
			int   f1   =   b.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();  
			pKeyValue.setValue(f1+"");
		}
		if(d==0) {
			dKeyValue.setValue("N/A");
		}else {
			Double rateD=a/d;
			BigDecimal b   =   new   BigDecimal(rateD*100); 
			int   f2   =   b.setScale(0,BigDecimal.ROUND_HALF_UP).intValue(); 
			dKeyValue.setValue(f2+"");
		}
		if(u==0) {
			uKeyValue.setValue("N/A");
		}else {
			Double rateU=a/d;
			BigDecimal b   =   new   BigDecimal(rateU*100); 
			int   f2   =   b.setScale(0,BigDecimal.ROUND_HALF_UP).intValue(); 
			uKeyValue.setValue(f2+"");
		}
		li.add(dKeyValue);li.add(pKeyValue);li.add(uKeyValue);
		JSONArray o=(JSONArray) JSONObject.toJSON(li);
		return o;
//		response.getWriter().print(o.toString());
	}
	/**
	 * 查询所有特殊的日期（法定假日，法定工作日，自定义节假日，自定义工作日）
	 * @throws ParseException
	 */
	public void getSpecialDate() throws ParseException{
		List<LinkedHashMap<String, Object>> specialDate=null;
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		int year=calendar.get(Calendar.YEAR);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		specialDate=userMapper.superSelect("select * from echarts_holidays where special_date>='"+(year+"-01-01")+"'");
		for(Map<?, ?> m:specialDate) {
			spdate.put(sdf.format(m.get("special_date")), (String) m.get("is_holiday"));
		}
	}
	/**
	 * 计算当前日期共有多少个工作日，到本月底有多少个工作日
	 * @param calendar
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getWorkingDays")
	public Integer[] getDays(Calendar calendar) throws Exception{
		Integer[] d=new Integer[3];
		if(spdate.size()<=0)
			getSpecialDate();
		int day=HolidayUtil.getHolidays(calendar.getTime(),spdate);
    	int monthDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    	calendar.set(Calendar.DAY_OF_MONTH, monthDay);
    	int monthWorkDays=HolidayUtil.getHolidays(calendar.getTime(), spdate);
    	d[0]=day;d[1]=monthWorkDays;
		return d;
	}
	
	/**
	 * 获取主数据选择列表
	 * @throws Exception 
	 */
	@RequestMapping("/getSelectList")
	@ResponseBody
	public void getSelectList(HttpServletRequest request,HttpServletResponse response,String type,String query) throws Exception{
		if(type!=null) {
			String sql="";
			if("bu2".equals(type)) {
				if(query==null||"".equals(query)||"all".equals(query))
					sql="select "+type+" a,"+type+" b from echarts_main_data group by "+type;
				else
					sql="select "+type+" a,"+type+" b from echarts_main_data where bu1='"+query+"' group by "+type;
			}else if("bu3".equals(type)) {
				sql="select "+type+" a,"+type+" b from echarts_main_data where bu2='"+query+"' group by "+type;
			}else if("brand".equals(type)) {
				sql="select brand a,brand b from echarts_main_data where bu3='"+query+"' group by "+type;
			}else if("sku_code".equals(type)) {
				sql="select sku_code a,sku_name b from echarts_main_data where brand='"+query+"' group by sku_code,sku_name";
			}
			List<LinkedHashMap<String, Object>> resultList=new ArrayList<LinkedHashMap<String, Object>>();
			resultList=userMapper.superSelect(sql);
	        List<KeyValue> xys=new ArrayList<>();
	        for(Map m:resultList) {
	        	KeyValue k=new KeyValue();
	        	k.setKey((String) m.get("a"));
	        	k.setValue((String) m.get("b"));
	        	xys.add(k);
	        }
	        JSONArray o=(JSONArray) JSONObject.toJSON(xys);
	        response.getWriter().print(o.toString());
		}
	}
	
	public boolean isNull(String str) {
		return str==null||"".equals(str);
	}
	/**
	 * 验证用户是否有查看报告的权限
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean isHavePormittion(HttpServletRequest request,HttpServletResponse response) {
		List<LinkedHashMap<String, Object>> user=new ArrayList<LinkedHashMap<String, Object>>();
		String userId=(String) request.getAttribute("userId");
		user=userMapper.superSelect("select * from sys_user where id='"+userId+"' and is_enable='1'");
		if(user.size()>0) {//用户存在
			user=userMapper.superSelect("select u.username,r.role_code,r.role_name,p.code,p.name from sys_user u " + 
					"left join sys_user_role ur on u.id=ur.user_id " + 
					"left join sys_role r on ur.role_id=r.id " + 
					"left join sys_privilege p on p.role_id=r.id where u.id='"
					+userId+"' and role_code='DailySalesReport'");
			if(user.size()>0) {
				return true;
			}
		}//用户不存在或已禁用
		return false;
	}
	
}
