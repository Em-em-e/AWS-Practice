
package gsk.portal.quartz.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ResponseHeadersIntercepter extends HandlerInterceptorAdapter {

	private Map<String, String> headers;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			response.setHeader(entry.getKey(), entry.getValue());
			if ("Access-Control-Allow-Origin".equals(entry.getKey()) && "*".equals(entry.getValue())) {
				request.setAttribute("isJsonp", true);
			}
		}
		return super.preHandle(request, response, handler);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
