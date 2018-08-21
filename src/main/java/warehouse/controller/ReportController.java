package warehouse.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import warehouse.dao.ReportMapper;
import warehouse.model.Report;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	private ReportMapper reportMapper;

	
	@RequestMapping("list.json")
	public void listJson(HttpServletRequest request,HttpServletResponse response,Integer limit,Integer offset,Report report,
			String sort,String order) throws IOException {
		List<Report> out=new ArrayList<>();
		int total=reportMapper.count(report);
		if(limit==null) limit=10000;
		if(offset==null) offset=0;
		int page=total%limit==0?total/limit:(total/limit+1);
		out=reportMapper.queryPage(limit, offset, sort, order, report);
		Object o=JSONObject.toJSON(out);
		
		response.getWriter().print("{\"total\":" + total +",\"page\":"+page+ ",\"rows\":"+o.toString()+"}");
	}
}
