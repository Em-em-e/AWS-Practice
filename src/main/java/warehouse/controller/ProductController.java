package warehouse.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import warehouse.dao.ProductMapper;
import warehouse.dao.SysUserMapper;
import warehouse.model.Customer;
import warehouse.model.InWarehouse;
import warehouse.model.Product;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private SysUserMapper sysUserMapper;

	@RequestMapping("selectList.json")
	@ResponseBody
	public List<Product> selectList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		List<Product> li=new ArrayList<>();
		li=productMapper.selectAll();
		return li;
	}
	
	@RequestMapping("list.json")
	public void listJson(HttpServletRequest request,HttpServletResponse response,Integer limit,Integer offset,Product product,
			String sort,String order) throws IOException {
		List<Product> out=new ArrayList<>();
		int total=productMapper.count(product);
		if(limit==null) limit=10000;
		if(offset==null) offset=0;
		int page=total%limit==0?total/limit:(total/limit+1);
		out=productMapper.queryPage(limit, offset, sort, order, product);
		Object o=JSONObject.toJSON(out);
		
		response.getWriter().print("{\"total\":" + total +",\"page\":"+page+ ",\"rows\":"+o.toString()+"}");
	}
	
	@RequestMapping("getProductQuantity")
	public void getProductQuantity(HttpServletRequest request,HttpServletResponse response,String productNo) throws IOException {
		
		List<LinkedHashMap<String, Object>> li=sysUserMapper.superSelect("select cube_or_quantity from product where product_no='"+productNo+"'");
		if(li!=null&&li.size()>0) {
			response.getWriter().print(li.get(0).get("cube_or_quantity"));
		}else {
			response.getWriter().print("");
		}
	}
	
	@RequestMapping("addProduct")
	public void addProduct(HttpServletRequest request,HttpServletResponse response,Product product) throws IOException {
		productMapper.insert(product);
		response.getWriter().print("true");
	}
	@RequestMapping("updateProduct")
	public void updateProduct(HttpServletRequest request,HttpServletResponse response,Product product) throws IOException {
		productMapper.updateByPrimaryKey(product);
		response.getWriter().print("true");
	}
	@RequestMapping("deleteProduct")
	public void deleteOut(HttpServletRequest request,HttpServletResponse response,int id) throws IOException {
		productMapper.deleteByPrimaryKey(id);
		response.getWriter().print("true");
	}
}
