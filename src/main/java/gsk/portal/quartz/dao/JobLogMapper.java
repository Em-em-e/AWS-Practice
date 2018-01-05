package gsk.portal.quartz.dao;

import java.util.List;

import gsk.portal.quartz.model.JobLog;

public interface JobLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JobLog record);

    int insertSelective(JobLog record);

    JobLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JobLog record);

    int updateByPrimaryKey(JobLog record);
    
    List<JobLog> findAll();
}