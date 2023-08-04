package com.green.campingsmore.item;

import com.green.campingsmore.item.model.*;
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

    /* api 아이템 불러오기
    @PostMapping("/post")
    @Operation(summary = "사용금지")
    public int insItem(@RequestParam String text) {
        return SERVICE.insItem(text);

    }*/

//    @PostMapping("/itempost")
//    @Operation(summary = "아이템 상세이미지 업로드 - 관리자페이지"
//            , description = "" +
//            "\"iitem\": [-] 아이템 PK,<br>" +
//            "\"picUrl\": [-] 사진 이미지 url<br>")
//    public int insItem(@RequestParam )

    @PostMapping("/dtailpic")
    @Operation(summary = "아이템 상세이미지 업로드 - 관리자페이지"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"picUrl\": [-] 사진 이미지 url<br>")
    public List<ItemDetailInsDto> insDetailPic(@RequestParam Long iitem,
                                               @RequestParam List<String> picUrl) {
        return SERVICE.insDetailPic(iitem, picUrl);
    }

/*    @GetMapping("/search")
    @Operation(summary = "아이템 검색 및 검색리스트"
            , description = "" +
            "\"text\": [-] 검색어,<br>" +
            "\"page\": [-] 리스트 페이지<br>" +
            "\"row\": [고정] 아이템 개수<br>")
    public List<ItemVo> getSearchItem(@RequestParam String text,
                                       @RequestParam(defaultValue = "1")int page,
                                       @RequestParam(defaultValue = "21")int row) {
        ItemSearchDto dto = new ItemSearchDto();
        dto.setText(text);
        dto.setPage(page);
        dto.setRow(row);
        return SERVICE.searchItem(dto);
    }*/

    @GetMapping("/search")
    @Operation(summary = "아이템 검색 및 검색리스트"
            , description = "" +
            "\"text\": [-] 검색어,<br>" +
            "\"page\": [-] 리스트 페이지,<br>" +
            "\"row\": [고정] 아이템 개수,<br>" +
            "\"cate\": [-] 카테고리(11: 축산물, 16: 수산물, 13: 소스/드레싱, 18: 밀키트, 17: 농산물),<br>" +
            "\"sort\": [1] 판매순 랭킹(0 : 최신순, 1: 오래된순, 2: 높은가격순, 3: 낮은가격순)  <br>"
    )
    public ItemSelDetailRes getSearchItem(@RequestParam(value = "cate",required=false)Long cate,
                                      @RequestParam(value = "text",required=false)String text,
                                      @RequestParam(defaultValue = "1")int page,
                                      @RequestParam(defaultValue = "21")int row,
                                      @RequestParam(defaultValue = "0")int sort) {
        ItemSearchDto2 dto = new ItemSearchDto2();
        dto.setText(text);
        dto.setPage(page);
        dto.setRow(row);
        dto.setIitemCategory(cate);
        dto.setSort(sort);
        return SERVICE.searchItem(dto);
    }

    @GetMapping("/category")
    @Operation(summary = "아이템 카테고리"
            , description = "" )
    public List<ItemSelCateVo> getCategory(){
        return SERVICE.selCategory();
    }


    @GetMapping("/detail")
    @Operation(summary = "아이템 상세페이지"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>"+
            "\"page\": [-] 리스트 페이지,<br>" +
            "\"row\": [고정] 아이템 개수<br>" )
    public ItemDetailReviewVo getItemDetail(@RequestParam Long iitem,
                                            @RequestParam(defaultValue = "1")int page,
                                            @RequestParam(defaultValue = "5")int row){
        ItemSelDetailDto dto = new ItemSelDetailDto();
        dto.setPage(page);
        dto.setIitem(iitem);
        dto.setRow(row);
        return SERVICE.selDetail(dto);
    }

    @PostMapping("/bestitem")
    @Operation(summary = "추천 아이템 추가 - 관리자페이지"
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
