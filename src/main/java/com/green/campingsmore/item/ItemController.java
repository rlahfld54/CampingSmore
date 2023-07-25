package com.green.campingsmore.item;

import com.green.campingsmore.dataset.NaverApi;
import com.green.campingsmore.item.model.ItemInsParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campingsmore")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    @PostMapping("/itme/post")
    public String insItem(@RequestParam String text) {
        return service.insItem(text);
    }


}
