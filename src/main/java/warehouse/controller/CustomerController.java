package warehouse.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import warehouse.dao.CustomerMapper;
import warehouse.model.Customer;
import warehouse.model.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerMapper customerMapper;

	@RequestMapping("selectList.json")
	@ResponseBody
	public List<Customer> selectList(HttpServletRequest request,HttpServletResponse response,String customerType) throws IOException {
		List<Customer> li=new ArrayList<>();
		if(customerType!=null&&"".equals(customerType))
			customerType=null;
		li=customerMapper.selectAll(customerType);
		return li;
	}
	
	
	@RequestMapping("list.json")
	public void listJson(HttpServletRequest request,HttpServletResponse response,Integer limit,Integer offset,Customer customer,
			String sort,String order) throws IOException {
		List<Customer> out=new ArrayList<>();
		int total=customerMapper.count(customer);
		if(limit==null) limit=10000;
		if(offset==null) offset=0;
		int page=total%limit==0?total/limit:(total/limit+1);
		out=customerMapper.queryPage(limit, offset, sort, order, customer);
		Object o=JSONObject.toJSON(out);
		
		response.getWriter().print("{\"total\":" + total +",\"page\":"+page+ ",\"rows\":"+o.toString()+"}");
	}
	
	@RequestMapping("addCustomer")
	public void addCustomer(HttpServletRequest request,HttpServletResponse response,Customer Customer) throws IOException {
		System.out.println(Customer.getCustomerName()+"===========================");
		customerMapper.insert(Customer);
		response.getWriter().print("true");
	}
	@RequestMapping("updateCustomer")
	public void updateCustomer(HttpServletRequest request,HttpServletResponse response,Customer Customer) throws IOException {
		customerMapper.updateByPrimaryKey(Customer);
		response.getWriter().print("true");
	}
	@RequestMapping("deleteCustomer")
	public void deleteOut(HttpServletRequest request,HttpServletResponse response,int id) throws IOException {
		customerMapper.deleteByPrimaryKey(id);
		response.getWriter().print("true");
	}
}
