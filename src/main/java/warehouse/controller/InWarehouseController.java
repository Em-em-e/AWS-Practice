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
import warehouse.dao.InWarehouseMapper;
import warehouse.dao.ProductMapper;
import warehouse.model.Customer;
import warehouse.model.InWarehouse;
import warehouse.model.OutWarehouse;
import warehouse.model.Product;

@Controller
@RequestMapping("/in")
public class InWarehouseController extends BaseController{
	
	@Autowired
	private InWarehouseMapper inWarehouseMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CustomerMapper customerMapper;
	
	@RequestMapping("list.json")
	public void listJson(HttpServletRequest request,HttpServletResponse response,Integer limit,Integer offset,InWarehouse inWarehouse,
			String sort,String order,String inDateStr) throws IOException {
		if(StringUtils.isNotBlank(inDateStr)) {
			try {
				Date outD=sdf.parse(inDateStr);
				inWarehouse.setInDate(outD);
			} catch (Exception e) {
			}
		}
		if(!StringUtils.isNotBlank(inWarehouse.getInNumber()))
			inWarehouse.setInNumber(null);
		List<InWarehouse> in=new ArrayList<>();
		int total=inWarehouseMapper.count(inWarehouse);
		if(limit==null) limit=10000;
		if(offset==null) offset=0;
		int page=total%limit==0?total/limit:(total/limit+1);
		in=inWarehouseMapper.queryPage(limit, offset, sort, order, inWarehouse);
		Object o=JSONObject.toJSON(in);
		
		response.getWriter().print("{\"total\":" + total +",\"page\":"+page+ ",\"rows\":"+o.toString()+"}");
	}
	
	@RequestMapping("addIn")
	public void addOut(HttpServletRequest request,HttpServletResponse response,InWarehouse inWarehouse,String inDateStr) throws IOException {
		inWarehouse.setOpTime(new Date());
		if(StringUtils.isNotBlank(inDateStr)) {
			try {
				Date outD=sdf.parse(inDateStr);
				inWarehouse.setInDate(outD);
			} catch (Exception e) {
			}
		}
		if(StringUtils.isNotBlank(inWarehouse.getProductNo())) {
			Product p=productMapper.selectByNo(inWarehouse.getProductNo());
			if(p!=null)
				inWarehouse.setProductName(p.getProductName());
		}
		if(StringUtils.isNotBlank(inWarehouse.getSupplierNo())) {
			Customer c=customerMapper.selectByNo(inWarehouse.getSupplierNo());
			if(c!=null)
				inWarehouse.setSupplierName(c.getCustomerName());
		}
		if(StringUtils.isNotBlank(inWarehouse.getTransporterNo())) {
			Customer c=customerMapper.selectByNo(inWarehouse.getTransporterNo());
			if(c!=null)
				inWarehouse.setTransporterName(c.getCustomerName());
		}
		Product p=productMapper.selectByNo(inWarehouse.getProductNo());
		if(inWarehouse.getQuantity()!=null&&p!=null) {
			p.setCubeOrQuantity(p.getCubeOrQuantity()+inWarehouse.getQuantity());
		}
		productMapper.updateByPrimaryKey(p);
		inWarehouseMapper.insert(inWarehouse);
		response.getWriter().print("true");
	}
	
	@RequestMapping("updateIn")
	public void updateOut(HttpServletRequest request,HttpServletResponse response,InWarehouse inWarehouse,String inDateStr) throws IOException {
		inWarehouse.setOpTime(new Date());
		if(StringUtils.isNotBlank(inDateStr)) {
			try {
				Date outD=sdf.parse(inDateStr);
				inWarehouse.setInDate(outD);
			} catch (Exception e) {
			}
		}
		if(StringUtils.isNotBlank(inWarehouse.getProductNo())) {
			Product p=productMapper.selectByNo(inWarehouse.getProductNo());
			if(p!=null)
				inWarehouse.setProductName(p.getProductName());
		}
		if(StringUtils.isNotBlank(inWarehouse.getSupplierNo())) {
			Customer c=customerMapper.selectByNo(inWarehouse.getSupplierNo());
			if(c!=null)
				inWarehouse.setSupplierName(c.getCustomerName());
		}
		if(StringUtils.isNotBlank(inWarehouse.getTransporterNo())) {
			Customer c=customerMapper.selectByNo(inWarehouse.getTransporterNo());
			if(c!=null)
				inWarehouse.setTransporterName(c.getCustomerName());
		}
		inWarehouseMapper.updateByPrimaryKey(inWarehouse);
		response.getWriter().print("true");
	}
	@RequestMapping("deleteIn")
	public void deleteOut(HttpServletRequest request,HttpServletResponse response,int id) throws IOException {
		inWarehouseMapper.deleteByPrimaryKey(id);
		response.getWriter().print("true");
	}

}
