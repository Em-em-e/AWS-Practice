package gsk.portal.quartz.dao;

import java.util.LinkedHashMap;
import java.util.List;

import datav.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<LinkedHashMap<String, Object>> superSelect(String sql);
}