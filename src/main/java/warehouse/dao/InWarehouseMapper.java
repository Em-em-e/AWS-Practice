package warehouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import warehouse.model.InWarehouse;

public interface InWarehouseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InWarehouse record);

    int insertSelective(InWarehouse record);

    InWarehouse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InWarehouse record);

    int updateByPrimaryKey(InWarehouse record);

    int count(@Param("inWarehouse")InWarehouse inWarehouse);
	
	List<InWarehouse> queryPage(@Param("limit")int limit,@Param("offset")int offset,
    		@Param("sort")String sort, @Param("order")String order, @Param("inWarehouse")InWarehouse inWarehouse);

}