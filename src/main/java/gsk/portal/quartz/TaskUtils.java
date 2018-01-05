package gsk.portal.quartz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import gsk.portal.quartz.model.ScheduleJob;
import gsk.portal.quartz.utils.SpringUtils;

public class TaskUtils {
	public final static Logger log = Logger.getLogger(TaskUtils.class);
	
	

	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 * @param scheduleJob
	 */
	public static void invokMethod(ScheduleJob scheduleJob) {
		Object object = null;
		Class clazz = null;
		if (StringUtils.isNotBlank(scheduleJob.getSpringid())) {
			object = SpringUtils.getBean(scheduleJob.getSpringid());
		} else if (StringUtils.isNotBlank(scheduleJob.getBeanclass())) {
			try {
				clazz = Class.forName(scheduleJob.getBeanclass());
				object=SpringUtils.getBean(clazz);
				if(object==null)
					object = clazz.newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (object == null) {
			log.error("任务名称 = [" + scheduleJob.getJobname() + "]---------------未启动成功，请检查是否配置正确！！！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(scheduleJob.getMethodname());
		} catch (NoSuchMethodException e) {
			log.error("任务名称 = [" + scheduleJob.getJobname() + "]---------------未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (method != null) {
			try {
				method.invoke(object);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("任务名称 = [" + scheduleJob.getJobname() + "]----------启动成功");
	}
}
