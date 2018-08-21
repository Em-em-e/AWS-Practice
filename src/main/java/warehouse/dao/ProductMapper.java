package warehouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import warehouse.model.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    
    List<Product> selectAll();
    
    Product selectByNo(String no);

	int count(@Param("product")Product product);

	List<Product> queryPage(@Param("limit")int limit,@Param("offset")int offset,
    		@Param("sort")String sort, @Param("order")String order, @Param("product")Product product);
}