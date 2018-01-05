package gsk.portal.quartz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import gsk.portal.quartz.QuartzJobFactory;
import gsk.portal.quartz.QuartzJobFactoryDisallowConcurrentExecution;
import gsk.portal.quartz.dao.ScheduleJobMapper;
import gsk.portal.quartz.model.ScheduleJob;

/**
 * 
 * @Description: 计划任务管理
 */
@Service
public class JobTaskService {
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

//	@Autowired
//	private ScheduleJobRepository scheduleJobRepository;
	
	@Autowired
	private ScheduleJobMapper scheduleJobMapper;

	/**
	 * 从数据库中取 区别于getAllJob
	 * 
	 * @return
	 */
	public List<ScheduleJob> getAllTask() {
		return scheduleJobMapper.findAll();
	}

	/**
	 * 添加到数据库中 区别于addJob
	 */
	public void addTask(ScheduleJob job) {
		job.setCreatetime(new Date());
		scheduleJobMapper.insert(job);
	}

	/**
	 * 从数据库中查询job
	 */
	public ScheduleJob getTaskById(int jobId) {
		return scheduleJobMapper.selectByPrimaryKey(jobId);
	}

	/**
	 * 更改任务状态
	 * 
	 * @throws SchedulerException
	 */
	public void changeStatus(int jobId, String cmd) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			deleteJob(job);
			job.setJobstatus(ScheduleJob.STATUS_NOT_RUNNING);
		} else if ("start".equals(cmd)) {
			job.setJobstatus(ScheduleJob.STATUS_RUNNING);
			addJob(job);
		}
		scheduleJobMapper.updateByPrimaryKey(job);
	}

	/**
	 * 更改任务 cron表达式
	 * 
	 * @throws SchedulerException
	 */
	public void updateCron(int jobId, String cron) throws SchedulerException {
		ScheduleJob job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		job.setCronexpression(cron);
		if (ScheduleJob.STATUS_RUNNING.equals(job.getJobstatus())) {
			updateJobCron(job);
		}
		scheduleJobMapper.updateByPrimaryKey(job);

	}

	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobstatus())) {
			return;
		}

		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobname(), job.getJobgroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsconcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;

			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobname(), job.getJobgroup()).build();

			jobDetail.getJobDataMap().put("scheduleJob", job);

			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronexpression());

			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobname(), job.getJobgroup()).withSchedule(scheduleBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronexpression());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	@PostConstruct
	public void init() throws Exception {

		// 这里获取任务信息数据
		List<ScheduleJob> jobList = scheduleJobMapper.findAll();
	
		for (ScheduleJob job : jobList) {
			addJob(job);
		}
	}

	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJobname(jobKey.getName());
				job.setJobgroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobstatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronexpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobname(jobKey.getName());
			job.setJobgroup(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobstatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronexpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobname(), scheduleJob.getJobgroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobname(), scheduleJob.getJobgroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobname(), scheduleJob.getJobgroup());
		scheduler.deleteJob(jobKey);

	}

	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobname(), scheduleJob.getJobgroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新job时间表达式
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobname(), scheduleJob.getJobgroup());

		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronexpression());

		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
	}

}
