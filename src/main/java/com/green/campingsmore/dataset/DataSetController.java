package com.green.campingsmore.dataset;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "오픈API 데이터셋 요청할때 썼던 것")
@RequestMapping("/api/dataset")
public class DataSetController {
    private final KaKaoApi kaKaoApi;
    private final NaverApi naverApi;

    @Autowired
    public DataSetController(KaKaoApi kaKaoApi, NaverApi naverApi) {
        this.kaKaoApi = kaKaoApi;
        this.naverApi = naverApi;
    }

    @GetMapping("/kakao")
    public String makeDataset(){
        return kaKaoApi.search("캠핑장");
    }

    @GetMapping("/naver")
    public String searchNaver(@RequestParam String text){
        return naverApi.search(text);
    }
}
