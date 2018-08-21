package warehouse.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import warehouse.dao.CustomerMapper;
import warehouse.dao.OutWarehouseMapper;
import warehouse.dao.ProductMapper;
import warehouse.model.Customer;
import warehouse.model.OutWarehouse;
import warehouse.model.Product;

@Controller
@RequestMapping("/out")
public class OutWarehouseController extends BaseController{

	@Autowired
	private OutWarehouseMapper outWarehouseMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CustomerMapper customerMapper;
	
	
	
	@RequestMapping("list.json")
	public void listJson(HttpServletRequest request,HttpServletResponse response,Integer limit,Integer offset,OutWarehouse outWarehouse,
			String sort,String order,String outDateStr) throws IOException {
		if(StringUtils.isNotBlank(outDateStr)) {
			try {
				Date outD=sdf.parse(outDateStr);
				outWarehouse.setOutDate(outD);
			} catch (Exception e) {
			}
		}
		if(!StringUtils.isNotBlank(outWarehouse.getOutNo()))
			outWarehouse.setOutNo(null);
		List<OutWarehouse> out=new ArrayList<>();
		int total=outWarehouseMapper.count(outWarehouse);
		if(limit==null) limit=10000;
		if(offset==null) offset=0;
		int page=total%limit==0?total/limit:(total/limit+1);
		out=outWarehouseMapper.queryPage(limit, offset, sort, order, outWarehouse);
		Object o=JSONObject.toJSON(out);
		
		response.getWriter().print("{\"total\":" + total +",\"page\":"+page+ ",\"rows\":"+o.toString()+"}");
	}
	
	@RequestMapping("addOut")
	public void addOut(HttpServletRequest request,HttpServletResponse response,OutWarehouse outWarehouse,String outDateStr) throws IOException {
		outWarehouse.setOpTime(new Date());
		if(StringUtils.isNotBlank(outDateStr)) {
			try {
				Date outD=sdf.parse(outDateStr);
				outWarehouse.setOutDate(outD);
			} catch (Exception e) {
			}
		}
		if(StringUtils.isNotBlank(outWarehouse.getProductNo())) {
			Product p=productMapper.selectByNo(outWarehouse.getProductNo());
			if(p!=null)
				outWarehouse.setProductName(p.getProductName());
		}
		if(StringUtils.isNotBlank(outWarehouse.getCustomerNo())) {
			Customer c=customerMapper.selectByNo(outWarehouse.getCustomerNo());
			if(c!=null)
				outWarehouse.setCustomerName(c.getCustomerName());
		}
		Product p=productMapper.selectByNo(outWarehouse.getProductNo());
		if(outWarehouse.getQuantity()!=null) {
			p.setCubeOrQuantity(p.getCubeOrQuantity()-outWarehouse.getQuantity());
		}
		productMapper.updateByPrimaryKey(p);
		outWarehouseMapper.insert(outWarehouse);
		response.getWriter().print("true");
	}
	@RequestMapping("updateOut")
	public void updateOut(HttpServletRequest request,HttpServletResponse response,OutWarehouse outWarehouse,String outDateStr) throws IOException {
		outWarehouse.setOpTime(new Date());
		if(StringUtils.isNotBlank(outDateStr)) {
			try {
				Date outD=sdf.parse(outDateStr);
				outWarehouse.setOutDate(outD);
			} catch (Exception e) {
			}
		}
		if(StringUtils.isNotBlank(outWarehouse.getProductNo())) {
			Product p=productMapper.selectByNo(outWarehouse.getProductNo());
			if(p!=null)
				outWarehouse.setProductName(p.getProductName());
		}
		if(StringUtils.isNotBlank(outWarehouse.getCustomerNo())) {
			Customer c=customerMapper.selectByNo(outWarehouse.getCustomerNo());
			if(c!=null)
				outWarehouse.setCustomerName(c.getCustomerName());
		}
		outWarehouseMapper.updateByPrimaryKey(outWarehouse);
		response.getWriter().print("true");
	}
	@RequestMapping("deleteOut")
	public void deleteOut(HttpServletRequest request,HttpServletResponse response,int id) throws IOException {
		outWarehouseMapper.deleteByPrimaryKey(id);
		response.getWriter().print("true");
	}
}
