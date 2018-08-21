package warehouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import warehouse.model.PracticeEn;

public interface PracticeEnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PracticeEn record);

    int insertSelective(PracticeEn record);

    PracticeEn selectByPrimaryKey(Integer id);
    
    PracticeEn selectByNo(Integer no);

    int updateByPrimaryKeySelective(PracticeEn record);

    int updateByPrimaryKey(PracticeEn record);
    
    int count(@Param("practiceen")PracticeEn practiceen);
	
	List<PracticeEn> queryPage(@Param("limit")int limit,@Param("offset")int offset,
    		@Param("sort")String sort, @Param("order")String order, @Param("practiceen")PracticeEn practiceen);
}