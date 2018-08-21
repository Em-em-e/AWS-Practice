package warehouse.controller;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import warehouse.dao.SysUserMapper;

@Component
public class BaseController {
	@Autowired
	private SysUserMapper sysUserMapper;
	
	public SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
}
