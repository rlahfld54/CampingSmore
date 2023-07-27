package com.green.campingsmore.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.dataset.NaverApi;
import com.green.campingsmore.item.model.SelCategory;
import com.green.campingsmore.item.model.ItemInsParam;
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

}
