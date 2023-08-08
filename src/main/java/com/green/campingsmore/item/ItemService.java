package com.green.campingsmore.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.dataset.NaverApi;
import com.green.campingsmore.item.model.*;
import com.green.campingsmore.review.ReviewService;
import com.green.campingsmore.review.model.ReviewPageDto;
import com.green.campingsmore.review.model.ReviewRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class ItemService {
    private final ItemMapper MAPPER;
    private final NaverApi naverApi;
    private final ReviewService REVIEWSERVICE;

@Autowired
    public ItemService(ItemMapper MAPPER, NaverApi naverApi, ReviewService REVIEWSERVICE) {
        this.MAPPER = MAPPER;
        this.naverApi = naverApi;
        this.REVIEWSERVICE = REVIEWSERVICE;
    }

    // 카테고리
    public List<ItemSelCateVo> selCategory(){

        return MAPPER.selCategory();
    }

    //아이템 추가
    public int insItem(ItemInsDto dto, List<String> picUrl) {

        log.info(" List<String> picUrl: {}", picUrl);
        ItemEntity entity = new ItemEntity();
        entity.setIitemCategory(dto.getIitemCategory());
        entity.setName(dto.getName());
        entity.setPic(dto.getPic());
        entity.setPrice(dto.getPrice());

        int result = MAPPER.insItem(entity);
        if( result == 1 ) {

            for (int i = 0; i < picUrl.size(); i++) {
                ItemDetailInsDto picDto = new ItemDetailInsDto();
                picDto.setIitem(entity.getIitem());

                picDto.setPic(picUrl.get(i).toString());

                MAPPER.insDetailPic(picDto);
            }
        }

        return 1;
    }

    public ItemSelDetailRes searchItem(ItemSearchDto2 dto) {
        dto.setStartIdx((dto.getPage()-1) * dto.getRow());
        List<ItemVo> list = MAPPER.searchItem(dto);
        int count = MAPPER.selLastItem(dto);
        int maxPage = (int)Math.ceil((double) count /dto.getRow());
        int isMore = maxPage > dto.getPage() ? 1 : 0;

    return ItemSelDetailRes.builder()
            .iitemCategory(dto.getIitemCategory())
            .text(dto.getText())
            .sort(dto.getSort())
            .maxPage(maxPage)
            .startIdx(dto.getStartIdx())
            .isMore(isMore)
            .page(dto.getPage())
            .row(dto.getRow())
            .itemList(list)
            .build();
}

    //아이템 삭제
    public int delItem(Long iitem) {
        return MAPPER.delItem(iitem);
    }


    // 아이템 상세
    public ItemDetailReviewVo selDetail(ItemSelDetailDto dto) {
        ItemSelDetailVo vo = MAPPER.selDetail(dto.getIitem());
        vo.setPicList(MAPPER.selDetailPic(dto.getIitem()));

        ReviewPageDto reviewDto = new ReviewPageDto();
        reviewDto.setIitem(dto.getIitem());
        reviewDto.setPage(dto.getPage());
        reviewDto.setRow(dto.getRow());
        reviewDto.setStartIdx((dto.getPage() - 1) * dto.getRow());
        ReviewRes reviewList = REVIEWSERVICE.selReview(reviewDto);
    return ItemDetailReviewVo.builder()
            .item(vo)
            .review(reviewList)
            .build();
    }

    //상세이미지 추가
    public List<ItemDetailInsDto> insDetailPic(Long iitem, List<String> picUrl) {
        // 아이템 PK에 사진이 있으면 삭제
        // 아이템 PK로 아이템 추가
        MAPPER.delDetailPic(iitem);

        ItemDetailInsDto dto = new ItemDetailInsDto();
        dto.setIitem(iitem);
        for (int i = 0; i < picUrl.size(); i++) {
            dto.setPic(picUrl.get(i));
            MAPPER.insDetailPic(dto);
        }

        return null;
    }



    public int delDetailPic(Long iitem) {
        return MAPPER.delDetailPic(iitem);
    }


/*    public ItemSelDetailVo selDetail(Long iitem) {
        return MAPPER.selDetail(iitem);
    }*/

    public int insBestItem(ItemInsBest dto) {
        return MAPPER.insBestItem(dto);
    }

    public List<ItemVo> selBestItem() {
    return MAPPER.selBestItem();
    }

}
