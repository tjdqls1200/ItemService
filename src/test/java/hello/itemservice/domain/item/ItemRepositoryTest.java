package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when
        Item saveItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(saveItem.getId());
        Assertions.assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll() {
        //given
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 10000, 10);

        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //when
        List<Item> itemList = itemRepository.findAll();

        //then
        Assertions.assertThat(itemList.size()).isEqualTo(2);
        Assertions.assertThat(itemList).contains(itemA, itemB);
    }

    @Test
    void update() {

        //given
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 20000, 5);
        itemRepository.save(itemA);

        //when
        itemRepository.update(itemA.getId(), itemB);

        //then
        Item updatedItemA = itemRepository.findById(itemA.getId());
        Assertions.assertThat(updatedItemA.getItemName()).isEqualTo(itemB.getItemName());
        Assertions.assertThat(updatedItemA.getPrice()).isEqualTo(itemB.getPrice());
        Assertions.assertThat(updatedItemA.getQuantity()).isEqualTo(itemB.getQuantity());
    }
}