package gsk.portal.quartz.dao;

import java.util.List;

import gsk.portal.quartz.model.ScheduleJob;

public interface ScheduleJobMapper {
    int deleteByPrimaryKey(String jobid);

    int insert(ScheduleJob record);

    int insertSelective(ScheduleJob record);

    ScheduleJob selectByPrimaryKey(int jobId);

    int updateByPrimaryKeySelective(ScheduleJob record);

    int updateByPrimaryKey(ScheduleJob record);
    
    List<ScheduleJob> findAll();
    
}