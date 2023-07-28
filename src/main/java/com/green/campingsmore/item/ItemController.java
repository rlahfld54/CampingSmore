package com.green.campingsmore.item;

import com.green.campingsmore.item.model.ItemDetailInsDto;
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
    private final ItemService SERVICE;

    @PostMapping("/item/post")
    @Operation(summary = "사용금지")
    public int insItem(@RequestParam String text) {
        return SERVICE.insItem(text);

    }

    @PostMapping("/item/dtailpic")
    @Operation(summary = "아이템 상세이미지 변경"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"picUrl\": [-] 사진 이미지 url<br>")
    public List<ItemDetailInsDto> insDetilpic(@RequestParam Long iitem, @RequestParam List<String> picUrl) {
        return SERVICE.insDetailPic(iitem, picUrl);
    }

    @GetMapping("/item/itemlist")
    @Operation(summary = "아이템 카테고리별 리스트"
            , description = "" +
            "\"cate\": [-] 카테고리 PK,<br>" +
            "\"page\": [-] 리스트 페이지<br>")
    public List<ItemVo> getICateList(@RequestParam int cate,
                                    @RequestParam(defaultValue = "1") int page) {

        return SERVICE.selCateItem(cate, page);
    }

    @GetMapping("/item/detail")

    @Operation(summary = "아이템 상세이미지 변경"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>")
    public Long getItemDetail(@RequestParam Long iitem){
        return null;
    }

    @GetMapping("/item/cate")







}
