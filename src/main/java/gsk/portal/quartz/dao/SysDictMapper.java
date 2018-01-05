package gsk.portal.quartz.dao;

import java.util.List;

import gsk.portal.quartz.model.SysDict;

public interface SysDictMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);
    
    List<SysDict> getAllByType(String type);
}