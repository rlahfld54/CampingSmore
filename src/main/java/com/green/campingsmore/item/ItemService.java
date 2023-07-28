package com.green.campingsmore.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.dataset.NaverApi;
import com.green.campingsmore.item.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class ItemService {
    private final ItemMapper MAPPER;
    private final NaverApi naverApi;

@Autowired
    public ItemService(ItemMapper MAPPER, NaverApi naverApi) {
        this.MAPPER = MAPPER;
        this.naverApi = naverApi;
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

    public List<ItemSelCateVo> selCategory(){

    return MAPPER.selCategory();
    }

    public List<ItemDetailInsDto> insDetailPic(Long iitem, List<String> picUrl) {
        // 아이템 PK에 사진이 있으면 삭제
        // 아이템 PK로 아이템 추가
        MAPPER.delDetail(Long.valueOf(iitem));

        ItemDetailInsDto dto = new ItemDetailInsDto();
        dto.setIitem(iitem);
        for (int i = 0; i < picUrl.size(); i++) {
            log.info("picUrl.get(i): {}",picUrl.get(i));
            dto.setPic(picUrl.get(i));
            MAPPER.insDetail(dto);
        }

     return null;
    }

    public List<ItemVo> selCateItem(int cate, int page) {

        ItemSelCateDto dto = new ItemSelCateDto();
        dto.setIitemCategory(Long.valueOf(cate));
        dto.setPage(page);
        dto.setRow(21);
        dto.setStartIdx((dto.getPage()-1)* dto.getRow());
        return MAPPER.selCateItem(dto);
    }

    public ItemSelDetailVo selDetail(Long iitem) {

    return MAPPER.selDetail(iitem);
    }
}
