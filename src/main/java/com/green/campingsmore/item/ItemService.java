package com.green.campingsmore.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.campingsmore.dataset.NaverApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemMapper mapper;
    private final NaverApi naverApi;

    public String insItem(String text) {

/*
        String json = naverApi.search(text);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, Map.class);
        // 여기서 캐스팅 안되는듯..
        List<LinkedHashMap<String, Object>> numList = (List<LinkedHashMap<String, Object>>) map.get("item");

*/


        return null;
    }

}
