package echarts.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;
@Component
public class HolidayUtil {
	
	public static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 统计本月截止到date的工作日
	 * @param date 要统计的日期
	 * @param spdate 所有特殊日期
	 * @return
	 * @throws ParseException
	 */
    public static int getHolidays(Date date, Map<String, String> spdate) throws ParseException {  
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(date);
    	int day=calendar.get(Calendar.DAY_OF_MONTH);
    	int count=0;
    	for(int i=1;i<=day;i++) {
    		calendar.set(Calendar.DAY_OF_MONTH, i);
    		String dateStr=sdf.format(calendar.getTime());
    		if(spdate.get(dateStr)==null) {
    			if(calendar.get(Calendar.DAY_OF_WEEK)!=1&&calendar.get(Calendar.DAY_OF_WEEK)!=7)
    				count++;
    		}else {
    			if("0".equals(spdate.get(dateStr))) {
    				count++;
    			}
    		}
    	}
    	return count;
    } 
    /**
     * 判断某一天是否是工作日
     * @param dayOfMonth 本月第几天
     * @param spdate 所有节假日日期
     * @param calendar 日期，与第一个参数一起确定当前日期
     * @return
     */
    public static boolean isHoliday(int dayOfMonth,Map<String, String> spdate,Calendar calendar) {
//    	Calendar calendar=Calendar.getInstance();
//    	calendar.setTime(new Date());
    	calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    	int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);
    	String dateStr=sdf.format(calendar.getTime());
    	if(spdate.get(dateStr)==null) {
    		if(dayOfWeek==1||dayOfWeek==7)
    			return true;
    		else
    			return false;
    	}else {
    		if("0".equals(spdate.get(dateStr))) {
				return false;
			}else
				return true;
    	}
    }
}
