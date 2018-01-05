package gsk.portal.quartz.model;

import java.util.Date;
/**
 * 
* @Description: 计划任务信息
* @author snailxr
* @date 2014年6月6日 下午10:49:43
 */
public class ScheduleJob implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";
	private String jobid;

	private Date createtime;

	private Date updatetime;
	/**
	 * 任务名称
	 */
	private String jobname;
	/**
	 * 任务分组
	 */
	private String jobgroup;
	/**
	 * 任务状态 是否启动任务
	 */
	private String jobstatus;
	/**
	 * cron表达式
	 */
	private String cronexpression;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 任务执行时调用哪个类的方法 包名+类名
	 */
	private String beanclass;
	/**
	 * 任务是否有状态
	 */
	private String isconcurrent;
	/**
	 * spring bean
	 */
	private String springid;
	/**
	 * 任务调用的方法名
	 */
	private String methodname;
	public String getJobid() {
		return jobid;
	}
	
	public void setJobid(String jobid) {
		this.jobid = jobid;
	}
	
	public Date getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public Date getUpdatetime() {
		return updatetime;
	}
	
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public String getJobname() {
		return jobname;
	}
	
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	
	public String getJobgroup() {
		return jobgroup;
	}
	
	public void setJobgroup(String jobgroup) {
		this.jobgroup = jobgroup;
	}
	
	public String getJobstatus() {
		return jobstatus;
	}
	
	public void setJobstatus(String jobstatus) {
		this.jobstatus = jobstatus;
	}
	
	public String getCronexpression() {
		return cronexpression;
	}
	
	public void setCronexpression(String cronexpression) {
		this.cronexpression = cronexpression;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getBeanclass() {
		return beanclass;
	}
	
	public void setBeanclass(String beanclass) {
		this.beanclass = beanclass;
	}
	
	public String getIsconcurrent() {
		return isconcurrent;
	}
	
	public void setIsconcurrent(String isconcurrent) {
		this.isconcurrent = isconcurrent;
	}
	
	public String getSpringid() {
		return springid;
	}
	
	public void setSpringid(String springid) {
		this.springid = springid;
	}
	
	public String getMethodname() {
		return methodname;
	}
	
	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

}