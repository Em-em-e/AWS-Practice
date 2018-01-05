package echarts.controller;

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
import org.springframework.ui.Model;
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
import echarts.model.KeyValue;
import gsk.portal.quartz.dao.UserMapper;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/echarts")
public class ChartsController {
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 主页面
	 * @throws Exception 
	 */
	@RequestMapping("/main")
	public String homePage(HttpServletRequest request, Model model) throws Exception{
		return "index";
	
	}
	
	/**
	 * 获取主数据选择列表
	 * @throws Exception 
	 */
	@RequestMapping("/getSelectList")
	@ResponseBody
	public void provinceDetail(HttpServletRequest request,HttpServletResponse response,String type) throws Exception{
		if(type!=null) {
			String sql="";
			if("bu3".equals(type)||"brand".equals(type)) {
				sql="select "+type+" a,"+type+" b from echarts_main_data group by "+type;
			}else if("sku_code".equals(type)) {
				sql="select sku_code a,sku_name b from echarts_main_data group by sku_code,sku_name";
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
	
	
	
}
