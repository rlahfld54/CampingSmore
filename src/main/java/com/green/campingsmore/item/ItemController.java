package com.green.campingsmore.item;

import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.item.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

@RestController
@Tag(name="아이템")
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService SERVICE;


    @GetMapping("/category")
    @Operation(summary = "아이템 카테고리 리스트"
            , description = "" )
    public List<ItemSelCateVo> getCategory(){
        return SERVICE.selCategory();
    }

    @PostMapping
    @Operation(summary = "아이템 추가 - 관리자페이지"
            , description = "" +
            "\"iitemCategory\": [-] 아이템 카테고리 PK,<br>" +
            "\"name\": [-] 아이템 제목,<br>" +
            "\"pic\": [-]  아이템 썸네일 pic url,<br>" +
            "\"price\": [-] 아이템 가격,<br>" +
            "\"picUrl\": [-] 사진 이미지 url<br>")
    public Long postItem(@RequestBody ItemInsDto dto) {
        return SERVICE.insItem(dto);
    }

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
                                          @RequestParam(defaultValue = "15")int row,
                                          @RequestParam(defaultValue = "0")int sort) {

        // 로그인을 안한 비회원인 경우 아이템 리스트 뿌려줌
        ItemSearchDto dto = new ItemSearchDto();
        dto.setText(text);
        dto.setPage(page);
        dto.setRow(row);
        dto.setIitemCategory(cate);
        dto.setSort(sort);

        return SERVICE.searchItem(dto);
    }

    @PutMapping
    @Operation(summary = "아이템 수정 - 관리자 페이지"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"iitemCategory\": [-] 아이템 카테고리 PK,<br>" +
            "\"name\": [-] 아이템 제목,<br>" +
            "\"pic\": [-]  아이템 썸네일 pic url,<br>" +
            "\"price\": [-] 아이템 가격,<br>" +
            "\"picUrl\": [-] 사진 이미지 url<br>")
    public int putItem(@RequestBody ItemUpdDto dto){
        return SERVICE.updItem(dto);
    }

    
    @DeleteMapping("/{iitem}")
    @Operation(summary = "아이템 삭제 - 관리자 페이지"
            , description = "" +
            "\"iitem\": [-] 아이템 PK<br>")
    public int delItem(@PathVariable Long iitem) {
        return SERVICE.delItem(iitem);
    }


    @GetMapping("/detail/{iitem}")
    @Operation(summary = "아이템 상세페이지"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>"+
            "\"page\": [-] 리스트 페이지,<br>" +
            "\"row\": [고정] 아이템 개수<br>" )
    public ItemDetailReviewVo getItemDetail(@PathVariable Long iitem,
                                            @RequestParam(defaultValue = "1")int page,
                                            @RequestParam(defaultValue = "5")int row){
        ItemSelDetailDto dto = new ItemSelDetailDto();
        dto.setIitem(iitem);
        dto.setPage(page);
        dto.setRow(row);
        return SERVICE.selDetail(dto);
    }

    @PostMapping("/detail-pic")
    @Operation(summary = "아이템 상세이미지 업로드 - 관리자페이지"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"picUrl\": [-] 사진 이미지 url<br>")
    public int insDetailPic(@RequestBody ItemInsDetailPicDto dto) {
        return SERVICE.insDetailPic(dto);
    }

    @DeleteMapping("/detail")
    @Operation(summary = "아이템 상세이미지 삭제 - 관리자페이지"
            , description = "" +
            "\"iitem\": [-] 아이템 PK<br>")
    public int delDetailPic(@RequestParam Long iitem) {
        return SERVICE.delDetailPic(iitem);
    }

    @PostMapping("/bestitem")
    @Operation(summary = "추천 아이템 추가 - 관리자페이지"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"monthLike\": [yyyy-MM-dd] 추천 아이템 노출 할 년월")
    public int insBestItem(@RequestBody ItemInsBestDto dto) {
        return SERVICE.insBestItem(dto);
    }

    @GetMapping("/bestitem")
    @Operation(summary = "추천 아이템 리스트"
            , description = "" )
    public List<ItemVo> getBestItem() {
        return SERVICE.selBestItem();
    }









}
