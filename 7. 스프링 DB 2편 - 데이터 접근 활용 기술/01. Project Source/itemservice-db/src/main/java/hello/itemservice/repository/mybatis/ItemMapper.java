package hello.itemservice.repository.mybatis;

import java.util.Optional;

import java.util.List;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import org.springframework.data.repository.query.Param;

// @Mapper
public interface ItemMapper {
/*
	void save(Item item);
	
	void update(@Param("id") Long id, @Param("udpateParam") ItemUpdateDto UpdateParam);
	
	@Select("select id, item_name, price, quantity from item where id = #{id}")
	Optional<Item> findById(Long id);
	
	List<Item> findAll(ItemSearchCond itemSearch);
	*/
}
