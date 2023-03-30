package hello.itemservice.repository.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.QItem;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static hello.itemservice.domain.QItem.item;

@Slf4j
@Repository
@Transactional
public class JpaItemRepositoryV3 implements ItemRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public JpaItemRepositoryV3(EntityManager em){
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }
    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) throws Exception {
        Item findItem = findById(itemId).orElseThrow();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    public List<Item> findAllOld(ItemSearchCond itemSearch) {
        Integer maxPrice   = itemSearch.getMaxPrice();
        String itemName    = itemSearch.getItemName();
        String conNameLike = "%"+itemName+"%";

        QItem item = QItem.item;
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(itemName)){
            builder.and(item.itemName.like(conNameLike));
        }

        if(maxPrice != null){
            builder.and(item.price.loe(maxPrice));
        }

        List<Item> result = query.select(item)
                                 .from(item)
                                 .where(builder)
                                 .fetch();
        return result;
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        Integer maxPrice = cond.getMaxPrice();
        String itemName = cond.getItemName();

        List<Item> result = query.select(item)
                .from(item)
                .where(likeItemName(itemName), maxPrice(maxPrice))
                .fetch();
        return result;
    }

    private Predicate likeItemName(String itemName) {
        String conNameLike = "%"+itemName+"%";
        if(StringUtils.hasText(itemName)){
            return item.itemName.like(conNameLike);
        }
        return null;
    }

    private Predicate maxPrice(Integer maxPrice) {
        if(maxPrice != null){
            return item.price.loe(maxPrice);
        }
        return null;
    }
}
