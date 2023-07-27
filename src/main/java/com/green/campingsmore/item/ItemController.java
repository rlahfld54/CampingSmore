package com.green.campingsmore.item;

import com.green.campingsmore.dataset.NaverApi;
import com.green.campingsmore.item.model.ItemInsParam;
import com.green.campingsmore.item.model.ItemRes;
import com.green.campingsmore.item.model.ItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="아이템")
@RequestMapping("/api/campingsmore")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    @PostMapping("/item/post")
    public int insItem(@RequestParam String text) {
        return service.insItem(text);
    }

/*    @GetMapping("/item/get")
    @Operation(summary = "아이템 리스트"
            , description = "" )
    public List<ItemVo> selICatetem(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "33")) {
        return null;
    }*/





}
