package warehouse.controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import warehouse.dao.SysUserMapper;
import warehouse.model.SysUser;

@Controller
public class LoginController {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@RequestMapping("/doLogin")
	public void doLogin(HttpServletRequest request,HttpServletResponse response,SysUser sysUser,ModelMap map) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		if(sysUser==null || sysUser.getUsername()==null || "".equals(sysUser.getUsername())){
			request.setAttribute("errMsg", "用户名不存在");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
		SysUser user=sysUserMapper.getByUsername(sysUser.getUsername());
		if(user==null){
			request.setAttribute("errMsg", "用户名不存在");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else{
			if(user.getPassword().equals(sysUser.getPassword())){
				SysUser u=new SysUser();
				u.setLastLoginTime(user.getLastLoginTime());
				u.setName(user.getName());
				u.setUsername(user.getUsername());
				request.getSession().setAttribute("loginedUser", u);
				user.setLastLoginTime(new Date());
				sysUserMapper.updateByPrimaryKeySelective(user);
				response.sendRedirect("aws/page");
			}else{
				request.setAttribute("errMsg", "密码错误");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
	}
	
	@RequestMapping("/loginout")
	public void loginOut(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.getSession().removeAttribute("loginedUser");
		response.sendRedirect("login.jsp");
	}
	
	@RequestMapping("/main")
	public String homePage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return "index";
	}
	@RequestMapping("/isLogin")
	public void isLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.getWriter().print("yes");
	}
}
