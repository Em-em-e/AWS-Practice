package warehouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import warehouse.model.Practice;

public interface PracticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Practice record);

    int insertSelective(Practice record);

    Practice selectByPrimaryKey(Integer id);
    
    Practice selectByNo(Integer no);

    int updateByPrimaryKeySelective(Practice record);

    int updateByPrimaryKey(Practice record);
    
    int count(@Param("practice")Practice practice);
	
	List<Practice> queryPage(@Param("limit")int limit,@Param("offset")int offset,
    		@Param("sort")String sort, @Param("order")String order, @Param("practice")Practice practice);
}