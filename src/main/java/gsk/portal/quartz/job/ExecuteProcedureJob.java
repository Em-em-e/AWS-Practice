package gsk.portal.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gsk.portal.quartz.dao.SysDictMapper;
import gsk.portal.quartz.dao.UserMapper;
import gsk.portal.quartz.model.SysDict;

@Component
public class ExecuteProcedureJob {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private SysDictMapper sysDictMapper;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void runDetail() throws InterruptedException{
		List<SysDict> dicts=sysDictMapper.getAllByType("jobProcedure");
		for(SysDict dict:dicts){
			if(dict.getCodeId().indexOf("detail")>=0){
				System.out.println(sdf.format(new Date())+" 开始执行任务------>："+dict.getCodeName());
				long start=System.currentTimeMillis();
				List<LinkedHashMap<String, Object>> result=userMapper.superSelect("DECLARE @returnStatus int "+
	            "exec "+dict.getCodeId()+" @returnStatus output "+
	            " select @returnStatus returnStatus");
				while(true){
					if(result.get(0)!=null&&result.get(0).get("returnStatus").equals(0)){
						break;
					}
					if((System.currentTimeMillis()-start)/1000>30){
						break;
					}
				}
				System.out.println("任务执行结束 ,耗时："+(System.currentTimeMillis()-start));
			}
		}
	}
	public void runReport() throws InterruptedException{
		List<SysDict> dicts=sysDictMapper.getAllByType("jobProcedure");
		for(SysDict dict:dicts){
			if(dict.getCodeId().indexOf("report")>=0){
				System.out.println(sdf.format(new Date())+"开始执行任务------>："+dict.getCodeName());
				long start=System.currentTimeMillis();
				List<LinkedHashMap<String, Object>> result=userMapper.superSelect("DECLARE @returnStatus int "+
	            "exec "+dict.getCodeId()+" @returnStatus output "+
	            " select @returnStatus returnStatus");
				while(true){
					if(result.get(0)!=null&&result.get(0).get("returnStatus").equals(0)){
						break;
					}
					if((System.currentTimeMillis()-start)/1000>30){
						break;
					}
				}
				System.out.println("任务执行结束 ,耗时："+(System.currentTimeMillis()-start));
			}
		}
	}
	public void runWhTemperatureReport() throws InterruptedException{
		System.out.println(sdf.format(new Date())+"开始执行任务------>：仓库温度上传状态报告job");
		long start=System.currentTimeMillis();
		userMapper.superSelect("DECLARE @returnStatus int "+
        "exec exe_wh_temperature_report @returnStatus output "+
        " select @returnStatus returnStatus");
		
		System.out.println("任务执行结束 ,耗时："+(System.currentTimeMillis()-start));
	}
	//测试服务器时区
	public void testTimeTC(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<LinkedHashMap<String, Object>> result=userMapper.superSelect("select getdate() sqldate");
		System.out.println("java系统时间："+new Date());
		System.out.println("数据库服务器时间："+result.get(0).get("sqldate"));
		System.out.println("java系统时间-format："+sdf.format(new Date()));
		System.out.println("数据库服务器时间-format："+sdf.format(result.get(0).get("sqldate")));
	}
}
