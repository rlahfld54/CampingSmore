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

    public int insItem(String text) {

        String json = naverApi.search(text);

        System.out.println("json!!!!");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        ItemInsParam ipram = new ItemInsParam();


        try {
            map = mapper.readValue(json, Map.class);
            List<LinkedHashMap<String, Object>> numList = (List<LinkedHashMap<String, Object>>) map.get("items");
            System.out.println(numList);
            for(LinkedHashMap<String, Object> item : numList) {

                log.info("ipram.category2: {}",item.get("category2"));
                String result = MAPPER.selCate((String)item.get("category2"));
                log.info("cate : {}", MAPPER.selCate((String) item.get("category2")));


                if (result == null) {
                    MAPPER.insCate((String) item.get("category2"));
                }
                ipram.setIitemCategory(MAPPER.selIcate((String) item.get("category2")));
                ipram.setName((String)item.get("title"));
                ipram.setPrice(Integer.valueOf((String)item.get("lprice")));
                ipram.setPic((String)item.get("image"));
                log.info("ipram : {}",ipram);
                MAPPER.insItem(ipram);

                /*if (result.equals(item.get("category2"))) {
                    ipram.setIitemCategory(cate.getIitemCategory());
                    ipram.setName((String)item.get("title"));
                    ipram.setPrice(Integer.valueOf((String)item.get("lprice")));
                    ipram.setPic((String)item.get("image"));
                    log.info("ipram : {}",ipram);
                    MAPPER.insItem(ipram);
                }*/

            }
            return 1;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

/*    public List<ItemVo> searchItem(ItemSearchDto dto) {
        dto.setStartIdx((dto.getPage()-1) * dto.getRow());
        log.info("res : {}", dto);


     return MAPPER.searchItem(dto);
    }*/
    public ItemSelDetailRes searchItem(ItemSearchDto2 dto) {
        dto.setStartIdx((dto.getPage()-1) * dto.getRow());
        log.info("res : {}", dto);

        List<ItemVo> list = MAPPER.searchItem(dto);


    return ItemSelDetailRes.builder()
            .iitemCategory(dto.getIitemCategory())
            .text(dto.getText())
            .sort(dto.getSort())
            .startIdx(dto.getStartIdx())
            .page(dto.getPage())
            .row(dto.getRow())
            .itemList(list)
            .build();
}


    // 카테고리
    public List<ItemSelCateVo> selCategory(){

    return MAPPER.selCategory();
    }

    //상세이미지 추가
    public List<ItemDetailInsDto> insDetailPic(Long iitem, List<String> picUrl) {
        // 아이템 PK에 사진이 있으면 삭제
        // 아이템 PK로 아이템 추가
        MAPPER.delDetailPic(Long.valueOf(iitem));

        ItemDetailInsDto dto = new ItemDetailInsDto();
        dto.setIitem(iitem);
        for (int i = 0; i < picUrl.size(); i++) {
            log.info("picUrl.get(i): {}",picUrl.get(i));
            dto.setPic(picUrl.get(i));
            MAPPER.insDetailPic(dto);
        }

     return null;
    }

    public int insBestItem(ItemInsBest dto) {

    return MAPPER.insBestItem(dto);
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
/*    public ItemSelDetailVo selDetail(Long iitem) {
        return MAPPER.selDetail(iitem);
    }*/

    public List<ItemVo> selBestItem() {
    return MAPPER.selBestItem();
    }

}
