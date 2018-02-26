package echarts.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import gsk.portal.quartz.dao.UserMapper;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/portalCharts")
public class PortalController {
	
		@Autowired
		private UserMapper userMapper;
		
		/**
		 * 主页面
		 * @throws Exception 
		 */
		@RequestMapping("/main")
		public String homePage(HttpServletRequest request, Model model) throws Exception{
			return "portal";
		}
}
