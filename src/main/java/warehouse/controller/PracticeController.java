package warehouse.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import warehouse.dao.PracticeEnMapper;
import warehouse.dao.PracticeMapper;
import warehouse.dao.SysUserMapper;
import warehouse.model.Practice;
import warehouse.model.PracticeEn;
import warehouse.model.SysUser;

@Controller
@RequestMapping("aws")
public class PracticeController {
	
	@Autowired
	private PracticeMapper practiceMapper;
	@Autowired
	private PracticeEnMapper practiceEnMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	/**
	 * 列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("page")
	public String page(HttpServletRequest request,HttpServletResponse response) {
		return "practiceList";
	}
	/**
	 * 答题
	 * @param request
	 * @param response
	 * @param selectAns 选择的答案
	 * @param no 题号
	 * @param cnOrEn 中文或英文
	 * @throws Exception
	 */
	@RequestMapping("ansDetail")
	public void ansDetail(HttpServletRequest request,HttpServletResponse response,String selectAns,String no,String cnOrEn) throws Exception{
		SysUser u=(SysUser) request.getSession().getAttribute("loginedUser");
		Integer noint=Integer.valueOf(no);
		selectAns=selectAns.replaceAll(",", "");
		int isAnsTrue=0;//0表示不对
		List<LinkedHashMap<String, Object>> l=null;
		if("en".equals(cnOrEn)) {
			PracticeEn p=practiceEnMapper.selectByNo(noint);
			if(selectAns.equals(p.getAns()))
				isAnsTrue=1;
			List<LinkedHashMap<String, Object>> ansDetail=sysUserMapper.superSelect("select * from practice_detail where user='"+u.getUsername()+"' and no="+noint+" and cn_or_en='en'");
			if(ansDetail!=null&&ansDetail.size()>0) {
				Integer ansTime=(Integer) ansDetail.get(0).get("ans_time");
				ansTime++;
				sysUserMapper.superSelect("update practice_detail set ans='"+selectAns+"',ans_time="+
						ansTime+",is_correct="+isAnsTrue+" where user='"+u.getUsername()+"' and no="+noint+" and cn_or_en='en'");
			}else {
				sysUserMapper.superSelect("insert into practice_detail(no,user,ans,is_correct,cn_or_en) values("+noint+",'"+u.getUsername()+"','"+selectAns+"',"+isAnsTrue+",'en')");
			}
			l=sysUserMapper.superSelect("select * from key_value where key1='practiceen_no' and user1='"+u.getUsername()+"'");
			if(l!=null&&l.size()>0) {
				sysUserMapper.superSelect("update key_value set value1='"+noint+"' where key1='practiceen_no' and user1='"+u.getUsername()+"'");
			}else
				sysUserMapper.superSelect("insert into key_value(key1,value1,user1) values('practiceen_no','"+(p==null?1:p.getNo())+"','"+u.getUsername()+"')");
		}else {
			Practice p=practiceMapper.selectByNo(noint);
			if(selectAns.equals(p.getAns()))
				isAnsTrue=1;
			List<LinkedHashMap<String, Object>> ansDetail=sysUserMapper.superSelect("select * from practice_detail where user='"+u.getUsername()+"' and no="+noint+" and cn_or_en='cn'");
			if(ansDetail!=null&&ansDetail.size()>0) {
				Integer ansTime=(Integer) ansDetail.get(0).get("ans_time");
				ansTime++;
				sysUserMapper.superSelect("update practice_detail set ans='"+selectAns+"',ans_time="+
						ansTime+",is_correct="+isAnsTrue+" where user='"+u.getUsername()+"' and no="+noint+" and cn_or_en='cn'");
			}else {
				sysUserMapper.superSelect("insert into practice_detail(no,user,ans,is_correct,cn_or_en) values("+noint+",'"+u.getUsername()+"','"+selectAns+"',"+isAnsTrue+",'cn')");
			}
			l=sysUserMapper.superSelect("select * from key_value where key1='practice_no' and user1='"+u.getUsername()+"'");
			if(l!=null&&l.size()>0) {
				sysUserMapper.superSelect("update key_value set value1='"+noint+"' where key1='practice_no' and user1='"+u.getUsername()+"'");
			}else
				sysUserMapper.superSelect("insert into key_value(key1,value1,user1) values('practice_no','"+(p==null?1:p.getNo())+"','"+u.getUsername()+"')");
		}
	}
	/**
	 * 题目页面，显示题目内容
	 * @param request
	 * @param response
	 * @param non 题号
	 * @param cnOrEn 中文或英文
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("practice")
	public String pageP(HttpServletRequest request,HttpServletResponse response,String non,String cnOrEn) throws Exception{
		SysUser u=(SysUser) request.getSession().getAttribute("loginedUser");
		Practice p=null;
		PracticeEn pen=null;
		if(non!=null&&!"".equals(non)) {
			if("en".equals(cnOrEn)) {
				pen=practiceEnMapper.selectByNo(Integer.valueOf(non));
				request.setAttribute("practice", pen);
			}else {
				p=practiceMapper.selectByNo(Integer.valueOf(non));
				request.setAttribute("practice", p);
			}
		}else {
			List<LinkedHashMap<String, Object>> l=null;
			if("en".equals(cnOrEn)) {
				l=sysUserMapper.superSelect("select * from key_value where key1='practiceen_no' and user1='"+u.getUsername()+"'");
			}else
				l=sysUserMapper.superSelect("select * from key_value where key1='practice_no' and user1='"+u.getUsername()+"'");
			if(l!=null&&l.size()>0) {
				Integer no=Integer.valueOf((String) l.get(0).get("value1"));
				if("en".equals(cnOrEn)) {
					pen=practiceEnMapper.selectByNo(no);
					request.setAttribute("practice", pen);
				}else {
					p=practiceMapper.selectByNo(no);
					request.setAttribute("practice", p);
				}
			}else {
				if("en".equals(cnOrEn)) {
					sysUserMapper.superSelect("insert into key_value(key1,value1,user1) values('practiceen_no','"+(p==null?1:p.getNo())+"','"+u.getUsername()+"')");
					pen=practiceEnMapper.selectByNo(1);
					request.setAttribute("practice", pen);
				}else {
					sysUserMapper.superSelect("insert into key_value(key1,value1,user1) values('practice_no','"+(p==null?1:p.getNo())+"','"+u.getUsername()+"')");
					p=practiceMapper.selectByNo(1);
					request.setAttribute("practice", p);
				}
			}
		}
		List<LinkedHashMap<String, Object>> l=null;
		if("en".equals(cnOrEn)) {
			request.setAttribute("cnOrEn", "en");
			l=sysUserMapper.superSelect("select * from practice_detail where user='"+u.getUsername()+"' and no="+pen.getNo()+" and cn_or_en='en'");
		}else {
			request.setAttribute("cnOrEn", "cn");
			l=sysUserMapper.superSelect("select * from practice_detail where user='"+u.getUsername()+"' and no="+p.getNo()+" and cn_or_en='cn'");
		}
		if(l!=null&&l.size()>0) {
			String ans=(String) l.get(0).get("ans");
			request.setAttribute("oldAns", ans);
		}
		return "practice";
	}
	
	/**
	 * 中文题目数据列表
	 * @param request
	 * @param response
	 * @param limit
	 * @param offset
	 * @param sort
	 * @param order
	 * @param practice
	 * @throws IOException
	 */
	@RequestMapping("list.json")
	public void listJson(HttpServletRequest request,HttpServletResponse response,Integer limit,Integer offset,String sort,String order,Practice practice) throws IOException {
		List<Practice> in=new ArrayList<>();
		int total=practiceMapper.count(practice);
		if(limit==null) limit=10000;
		if(offset==null) offset=0;
		int page=total%limit==0?total/limit:(total/limit+1);
		in=practiceMapper.queryPage(limit, offset, sort, order, practice);
		Object o=JSONObject.toJSON(in);
		
		response.getWriter().print("{\"total\":" + total +",\"page\":"+page+ ",\"rows\":"+o.toString()+"}");
	}
	
	
	@RequestMapping("listen.json")
	public void listEnJson(HttpServletRequest request,HttpServletResponse response,Integer limit,Integer offset,String sort,String order,PracticeEn practice) throws IOException {
		List<PracticeEn> in=new ArrayList<>();
		int total=practiceEnMapper.count(practice);
		if(limit==null) limit=10000;
		if(offset==null) offset=0;
		int page=total%limit==0?total/limit:(total/limit+1);
		in=practiceEnMapper.queryPage(limit, offset, sort, order, practice);
		Object o=JSONObject.toJSON(in);
		
		response.getWriter().print("{\"total\":" + total +",\"page\":"+page+ ",\"rows\":"+o.toString()+"}");
	}
	/**
	 * 记录最后练习的题号 中英文分开
	 * @param request
	 * @param response
	 * @param key
	 * @param value
	 */
	@RequestMapping("setkey")
	public void setKeyValue(HttpServletRequest request,HttpServletResponse response,String key,String value) {
		SysUser u=(SysUser) request.getSession().getAttribute("loginedUser");
		List<LinkedHashMap<String, Object>> l=sysUserMapper.superSelect("select * from key_value where key1='"+key+"' and user1='"+u.getUsername()+"'");
		if(l!=null&&l.size()>0) {
			sysUserMapper.superSelect("update key_value set value1='"+value+"' where key1='"+key+"' and user1='"+u.getUsername()+"'");
		}else
			sysUserMapper.superSelect("insert into key_value(key1,value1,user1) values('"+key+"','"+value+"','"+u.getUsername()+"')");
	}
	
	/**
	 * 翻译-网易直译
	 * @param request
	 * @param response
	 * @param q 要翻译的内容
	 * @param from 源语言
	 * @param to 目标语言
	 * @param appKey 网易应用key
	 * @param salt 随机数
	 * @param sign MD5签名
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("translate")
	public void translate(HttpServletRequest request,HttpServletResponse response,String q,String from,String to,String appKey,String salt,String sign) throws Exception {
		StringBuffer sb = new StringBuffer();
	      InputStreamReader isr = null;
	      BufferedReader br = null;
	      try
	      {
	    	  q=URLEncoder.encode(q);
	         URL url = new URL("http://openapi.youdao.com/api?q="+q+"&&from="+from+"&&to="+to+"&&appKey="+appKey+"&&salt="+salt+"&&sign="+sign);
	         URLConnection urlConnection = url.openConnection();
	         urlConnection.setAllowUserInteraction(false);
	         isr = new InputStreamReader(url.openStream());
	         br = new BufferedReader(isr);
	         String line;
	         while ((line = br.readLine()) != null){
	            sb.append(line);
	         }
	      }
	      catch (IOException e){
	         e.printStackTrace();
	      }
	      finally{
	    	  br.close();isr.close();
	      }
	      String translateStr= sb.toString();
	     response.getWriter().print(translateStr);
	}
}
