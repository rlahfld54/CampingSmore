package com.green.campingsmore.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.dataset.NaverApi;
import com.green.campingsmore.item.model.ItemInsDto;
import com.green.campingsmore.item.model.ItemInsParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemMapper mapper;
    private final NaverApi naverApi;

    public String insItem(String text) {

        String json = naverApi.search(text);

        System.out.println("json!!!!");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        ItemInsParam ipram = new ItemInsParam();
        ItemInsDto dto = new ItemInsDto();
        try {
            map = mapper.readValue(json, Map.class);
            List<LinkedHashMap<String, Object>> numList = (List<LinkedHashMap<String, Object>>) map.get("items");
            System.out.println(numList);
            for(LinkedHashMap<String, Object> item : numList) {


                dto.setCategory((String)item.get("category2"));
                dto.setName((String)item.get("title"));
                dto.setPrice((Integer)item.get("lprice"));
                dto.setPic((String)item.get("image"));


            }



        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return json;
    }

}
