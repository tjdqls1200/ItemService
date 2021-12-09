package hello.itemservice.web.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId,
                       Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    //@GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String addItem1(@RequestParam String itemName,
                       @RequestParam Integer price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item(itemName, price, quantity);
        model.addAttribute("item", item);
        itemRepository.save(item);
        return "basic/item";
    }

    // @ModelAttribute를 사용하면 클래스 생성 자동으로 해줌
    // attributeName과 ("item")이 같아야함
    //@PostMapping("/add")
    public String addItem2(@ModelAttribute ("item") Item item,
                           Model model) {
        itemRepository.save(item);
        return "basic/item";
    }

    // ("item") 생략 가능
    // model.addAttribute도 자동으로 해주기 때문에 생략 가능
    //@PostMapping("/add")
    public String addItem3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    // 매개 타입이 클래스인 경우 @ModelAttribute가 자동 적용,
    // 기본 자료형, String 일 경우 @RequestParam이 자동 적용.
    //@PostMapping("/add")
    public String addItem4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId,
                           @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}
