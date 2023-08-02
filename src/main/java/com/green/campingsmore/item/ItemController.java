package com.green.campingsmore.item;

import com.green.campingsmore.item.model.*;
import com.green.campingsmore.review.model.ItemSelDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="아이템")
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService SERVICE;

    /*@PostMapping("/item/post")
    @Operation(summary = "사용금지")
    public int insItem(@RequestParam String text) {
        return SERVICE.insItem(text);

    }*/

    @PostMapping("/dtailpic")
    @Operation(summary = "아이템 상세이미지 업로드 - 관리자페이지"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"picUrl\": [-] 사진 이미지 url<br>")
    public List<ItemDetailInsDto> insDetailPic(@RequestParam Long iitem, @RequestParam List<String> picUrl) {
        return SERVICE.insDetailPic(iitem, picUrl);
    }

    @GetMapping("/category")
    @Operation(summary = "아이템 카테고리"
            , description = "" )
    public List<ItemSelCateVo> getCategory(){
        return SERVICE.selCategory();
    }

    @GetMapping("/itemlist")
    @Operation(summary = "아이템 카테고리별 리스트"
            , description = "" +
            "\"cate\": [-] 카테고리 PK,<br>" +
            "\"page\": [-] 리스트 페이지<br>")
    public List<ItemVo> getICateList(@RequestParam int cate,
                                    @RequestParam(defaultValue = "1") int page) {

        return SERVICE.selCateItem(cate, page);
    }

    @GetMapping("/detail")
    @Operation(summary = "아이템 상세페이지"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>")
    public ItemDetailReviewVo getItemDetail(@RequestParam Long iitem){
        ItemSelDto dto = new ItemSelDto();
        dto.setPage(1);
        dto.setIitem(iitem);
        return SERVICE.selDetail(dto);
    }

    @PostMapping("/bestitem")
    @Operation(summary = "추천 아이템 추가"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"monthLike\": [yyyy-MM-dd] 추천 아이템 노출 할 년월")
    public int insBestItem(@RequestBody ItemInsBest dto) {
        return SERVICE.insBestItem(dto);
    }

    @GetMapping("/bestitem")
    @Operation(summary = "추천 아이템 리스트"
            , description = "" )
    public List<ItemVo> getBestItem() {
        return SERVICE.selBestItem();
    }









}
