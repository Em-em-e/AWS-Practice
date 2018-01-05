package gsk.portal.quartz.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class EncodingFilter implements Filter {
 
      @Override
	public void destroy() {
 
      }
 
      @Override
	public void doFilter(ServletRequest req, ServletResponse res,
                  FilterChain chain) throws IOException, ServletException {
            // 将请求和响应强制转换成Http形式
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;
 
            // 处理响应乱码
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Cache-Control","no-cache");
 
            String url=request.getRequestURL().toString();
            if(url.indexOf("datav")>0){
            	if(request.getRequestURL().indexOf("elb.amazonaws.com")>=0){
            		response.addHeader("Access-Control-Allow-Origin","https://datav.aliyun.com");
            	}else if(request.getRequestURL().indexOf("igskapp.com")>=0)
            		response.addHeader("Access-Control-Allow-Origin","https://datav.aliyun.com");
            	else
            		response.addHeader("Access-Control-Allow-Origin","http://datav.aliyun.com");
        		response.addHeader("Access-Control-Allow-Credentials","true");
                response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
                response.addHeader("Access-Control-Max-Age", "1800");//30 min
            }
            // 自定义一个request对象：MyRequest，对服务器原来的requset进行增强，使用装饰设计模式
            // 要增强原来的request对象，必须先获取到原来的request对象
            MyRequest myRequest = new MyRequest(request);
 
            // 注意：放行的时候应该传入增强后的request对象
            chain.doFilter(myRequest, response);
      }
 
      @Override
	public void init(FilterConfig arg0) throws ServletException {
 
      }
 
}