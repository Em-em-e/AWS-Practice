package warehouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import warehouse.model.Report;

public interface ReportMapper {
    int insert(Report record);

    int insertSelective(Report record);
    
    int count(@Param("report")Report report);

	List<Report> queryPage(@Param("limit")int limit,@Param("offset")int offset,
    		@Param("sort")String sort, @Param("order")String order, @Param("report")Report report);
}