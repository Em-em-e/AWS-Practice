package warehouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import warehouse.model.OutWarehouse;

public interface OutWarehouseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OutWarehouse record);

    int insertSelective(OutWarehouse record);

    OutWarehouse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OutWarehouse record);

    int updateByPrimaryKey(OutWarehouse record);
    
    List<OutWarehouse> queryPage(@Param("limit")int limit,@Param("offset")int offset,
    		@Param("sort")String sort, @Param("order")String order, @Param("outWarehouse")OutWarehouse outWarehouse);

	int count(@Param("outWarehouse")OutWarehouse outWarehouse);
}