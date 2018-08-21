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

import warehouse.dao.SysUserMapper;
import warehouse.model.SysUser;

@Controller
@RequestMapping("/user")
public class SysUserController {
	
	@Autowired
	private SysUserMapper sysUserMapper;

	@RequestMapping("list.json")
	public void listJson(HttpServletRequest request,HttpServletResponse response,Integer limit,Integer offset,SysUser sysUser,
			String sort,String order) throws IOException {
		List<SysUser> out=new ArrayList<>();
		int total=sysUserMapper.count(sysUser);
		if(limit==null) limit=10000;
		if(offset==null) offset=0;
		int page=total%limit==0?total/limit:(total/limit+1);
		out=sysUserMapper.queryPage(limit, offset, sort, order, sysUser);
		Object o=JSONObject.toJSON(out);
		
		response.getWriter().print("{\"total\":" + total +",\"page\":"+page+ ",\"rows\":"+o.toString()+"}");
	}
	@RequestMapping("isenable")
	public void isenable(HttpServletRequest request,HttpServletResponse response,String uname) throws IOException {
		List<?> a=sysUserMapper.superSelect("select * from sys_user where username='"+uname+"'");
		if(a!=null&&a.size()>0)
			response.getWriter().print("1");
		else
			response.getWriter().print("0");
	}
	@RequestMapping("addUser")
	public void addUser(HttpServletRequest request,HttpServletResponse response,SysUser User) throws IOException {
		sysUserMapper.insert(User);
		response.getWriter().print("true");
	}
	@RequestMapping("updateUser")
	public void updateUser(HttpServletRequest request,HttpServletResponse response,SysUser User) throws IOException {
		sysUserMapper.updateByPrimaryKey(User);
		response.getWriter().print("true");
	}
	@RequestMapping("deleteUser")
	public void deleteOut(HttpServletRequest request,HttpServletResponse response,int id) throws IOException {
		sysUserMapper.deleteByPrimaryKey(id);
		response.getWriter().print("true");
	}
}
